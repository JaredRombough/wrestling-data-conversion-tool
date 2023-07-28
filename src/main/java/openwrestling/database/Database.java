package openwrestling.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import openwrestling.Logging;
import openwrestling.database.queries.GameObjectQuery;
import openwrestling.entities.BankAccountEntity;
import openwrestling.entities.BroadcastTeamMemberEntity;
import openwrestling.entities.ContractEntity;
import openwrestling.entities.Entity;
import openwrestling.entities.EntourageMemberEntity;
import openwrestling.entities.EventEntity;
import openwrestling.entities.EventTemplateEntity;
import openwrestling.entities.GameSettingEntity;
import openwrestling.entities.InjuryEntity;
import openwrestling.entities.MatchRulesEntity;
import openwrestling.entities.MatchTitleEntity;
import openwrestling.entities.MonthlyReviewEntity;
import openwrestling.entities.MoraleRelationshipEntity;
import openwrestling.entities.NewsItemEntity;
import openwrestling.entities.NewsItemPromotionEntity;
import openwrestling.entities.NewsItemWorkerEntity;
import openwrestling.entities.PromotionEntity;
import openwrestling.entities.RosterSplitEntity;
import openwrestling.entities.RosterSplitWorkerEntity;
import openwrestling.entities.SegmentEntity;
import openwrestling.entities.SegmentTeamEntity;
import openwrestling.entities.SegmentTeamEntourageEntity;
import openwrestling.entities.SegmentTeamWorkerEntity;
import openwrestling.entities.SegmentTemplateEntity;
import openwrestling.entities.StableEntity;
import openwrestling.entities.StableMemberEntity;
import openwrestling.entities.StaffContractEntity;
import openwrestling.entities.StaffMemberEntity;
import openwrestling.entities.TagTeamEntity;
import openwrestling.entities.TagTeamWorkerEntity;
import openwrestling.entities.TitleEntity;
import openwrestling.entities.TitleReignEntity;
import openwrestling.entities.TitleReignWorkerEntity;
import openwrestling.entities.TransactionEntity;
import openwrestling.entities.WorkerEntity;
import openwrestling.entities.WorkerRelationshipEntity;
import openwrestling.mapper.BankAccountMapper;
import openwrestling.mapper.BroadcastTeamMemberMapper;
import openwrestling.mapper.ContractMapper;
import openwrestling.mapper.CycleAvoidingMappingContext;
import openwrestling.mapper.EntourageMemberMapper;
import openwrestling.mapper.EventMapper;
import openwrestling.mapper.EventTemplateMapper;
import openwrestling.mapper.GameSettingMapper;
import openwrestling.mapper.InjuryMapper;
import openwrestling.mapper.MatchRulesMapper;
import openwrestling.mapper.MonthlyReviewMapper;
import openwrestling.mapper.MoraleRelationshipMapper;
import openwrestling.mapper.PromotionMapper;
import openwrestling.mapper.RosterSplitMapper;
import openwrestling.mapper.RosterSplitWorkerMapper;
import openwrestling.mapper.SegmentMapper;
import openwrestling.mapper.SegmentTeamMapper;
import openwrestling.mapper.SegmentTemplateMapper;
import openwrestling.mapper.StableMapper;
import openwrestling.mapper.StableMemberMapper;
import openwrestling.mapper.StaffContractMapper;
import openwrestling.mapper.StaffMemberMapper;
import openwrestling.mapper.TagTeamMapper;
import openwrestling.mapper.TitleMapper;
import openwrestling.mapper.TitleReignMapper;
import openwrestling.mapper.TransactionMapper;
import openwrestling.mapper.WorkerMapper;
import openwrestling.mapper.WorkerRelationshipMapper;
import openwrestling.model.NewsItem;
import openwrestling.model.gameObjects.BroadcastTeamMember;
import openwrestling.model.gameObjects.Contract;
import openwrestling.model.gameObjects.EntourageMember;
import openwrestling.model.gameObjects.Event;
import openwrestling.model.gameObjects.EventTemplate;
import openwrestling.model.gameObjects.GameObject;
import openwrestling.model.gameObjects.Injury;
import openwrestling.model.gameObjects.MonthlyReview;
import openwrestling.model.gameObjects.MoraleRelationship;
import openwrestling.model.gameObjects.Promotion;
import openwrestling.model.gameObjects.RosterSplit;
import openwrestling.model.gameObjects.RosterSplitWorker;
import openwrestling.model.gameObjects.Segment;
import openwrestling.model.gameObjects.SegmentTeam;
import openwrestling.model.gameObjects.SegmentTemplate;
import openwrestling.model.gameObjects.Stable;
import openwrestling.model.gameObjects.StableMember;
import openwrestling.model.gameObjects.StaffContract;
import openwrestling.model.gameObjects.StaffMember;
import openwrestling.model.gameObjects.TagTeam;
import openwrestling.model.gameObjects.Title;
import openwrestling.model.gameObjects.TitleReign;
import openwrestling.model.gameObjects.Worker;
import openwrestling.model.gameObjects.WorkerRelationship;
import openwrestling.model.gameObjects.financial.BankAccount;
import openwrestling.model.gameObjects.financial.Transaction;
import openwrestling.model.gameObjects.gamesettings.GameSetting;
import openwrestling.model.segment.opitons.MatchRules;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Database extends Logging {

    private final String dbUrl;

    private final Map<Class<? extends GameObject>, Class<? extends Entity>> daoClassMap = new HashMap<>() {{
        put(Promotion.class, PromotionEntity.class);
        put(Worker.class, WorkerEntity.class);
        put(Stable.class, StableEntity.class);
        put(StableMember.class, StableMemberEntity.class);
        put(RosterSplit.class, RosterSplitEntity.class);
        put(RosterSplitWorker.class, RosterSplitWorkerEntity.class);
        put(Contract.class, ContractEntity.class);
        put(TagTeam.class, TagTeamEntity.class);
        put(Title.class, TitleEntity.class);
        put(TitleReign.class, TitleReignEntity.class);
        put(EventTemplate.class, EventTemplateEntity.class);
        put(StaffMember.class, StaffMemberEntity.class);
        put(StaffContract.class, StaffContractEntity.class);
        put(WorkerRelationship.class, WorkerRelationshipEntity.class);
        put(MoraleRelationship.class, MoraleRelationshipEntity.class);
        put(BankAccount.class, BankAccountEntity.class);
        put(Transaction.class, TransactionEntity.class);
        put(EntourageMember.class, EntourageMemberEntity.class);
        put(Event.class, EventEntity.class);
        put(Segment.class, SegmentEntity.class);
        put(SegmentTeam.class, SegmentTeamEntity.class);
        put(BroadcastTeamMember.class, BroadcastTeamMemberEntity.class);
        put(Injury.class, InjuryEntity.class);
        put(SegmentTemplate.class, SegmentTemplateEntity.class);
        put(NewsItem.class, NewsItemEntity.class);
        put(GameSetting.class, GameSettingEntity.class);
        put(MonthlyReview.class, MonthlyReviewEntity.class);
        put(MatchRules.class, MatchRulesEntity.class);
    }};


    //Test constructor
    public Database(String dbPath) {
        dbUrl = "jdbc:sqlite:" + dbPath;
        createNewDatabase();
    }

    public Database(File dbFile) {
        dbUrl = "jdbc:sqlite:" + dbFile.getPath().replace("\\", "/");
    }

    public void createNewDatabase() {
        createTables(dbUrl);
    }

    public List selectList(GameObjectQuery gameObjectQuery) {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(this.dbUrl);
            List<WorkerEntity> results = gameObjectQuery.getQueryBuilder(connectionSource).query();
            List<Worker> roster = new ArrayList<>();
            results.forEach(entity -> roster.add((Worker) mapItToGameObject(entity)));
            return roster;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public <T> List selectAll(Class sourceClass) {
        long start = System.currentTimeMillis();
        List list;
        try {
            Class<? extends Entity> targetClass = daoClassMap.get(sourceClass);
            ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);
            Dao dao = DaoManager.createDao(connectionSource, targetClass);

            List<? extends Entity> entities = dao.queryForAll();
            entities.forEach(Entity::selectChildren);
            list = entitiesToGameObjects(entities, sourceClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        logger.log(Level.DEBUG,
                String.format("selectAll class %s size %s took %d",
                        sourceClass.getName(),
                        list.size(),
                        (System.currentTimeMillis() - start)));
        return list;
    }

    public <T> List querySelect(GameObjectQuery query) {
        long start = System.currentTimeMillis();
        List list;
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);

            List<? extends Entity> entities = query.getQueryBuilder(connectionSource).query();
            entities.forEach(Entity::selectChildren);
            list = entitiesToGameObjects(entities, query.sourceClass());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        logger.log(Level.DEBUG,
                String.format("querySelect sourceClass %s size %s took %d",
                        query.sourceClass().getName(),
                        list.size(),
                        (System.currentTimeMillis() - start)));
        return list;
    }

    public void deleteByID(Class sourceClass, long id) {
        long start = System.currentTimeMillis();
        try {
            Class<? extends Entity> targetClass = daoClassMap.get(sourceClass);
            ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);
            Dao dao = DaoManager.createDao(connectionSource, targetClass);

            dao.deleteById(id);
            logger.log(Level.DEBUG,
                    String.format("deleteByID class %s id %s took %d",
                            sourceClass.getName(),
                            id,
                            (System.currentTimeMillis() - start)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public <T extends GameObject> T insertGameObject(GameObject gameObject) {
        return (T) insertList(List.of(gameObject)).get(0);
    }

    public <T extends GameObject> List<T> insertList(List<T> gameObjects) {
        long start = System.currentTimeMillis();
        try {
            if (gameObjects.isEmpty()) {
                return gameObjects;
            }
            List<? extends Entity> entities = gameObjectsToEntities(gameObjects);
            List<? extends Entity> saved = insertOrUpdateEntityList(entities);
            List updatedGameObjects = entitiesToGameObjects(saved, gameObjects.get(0).getClass()).stream().map(o -> (T) o).collect(Collectors.toList());

            logger.log(Level.DEBUG,
                    String.format("insertList class %s size %s took %d",
                            gameObjects.get(0).getClass().getName(),
                            updatedGameObjects.size(),
                            (System.currentTimeMillis() - start)));

            return updatedGameObjects;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends GameObject> void updateList(List<T> gameObjects) {
        long start = System.currentTimeMillis();
        if (gameObjects.isEmpty()) {
            return;
        }
        List<? extends Entity> entities = gameObjectsToEntities(gameObjects);
        insertOrUpdateEntityList(entities);
        logger.log(Level.DEBUG,
                String.format("updateList sourceClass %s size %s took %d",
                        gameObjects.get(0).getClass().getName(),
                        gameObjects.size(),
                        (System.currentTimeMillis() - start)));
    }

    private <T extends Entity> List<T> insertOrUpdateEntityList(List<T> entities) {
        if (entities.isEmpty()) {
            return entities;
        }

        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);

            Class<? extends Entity> targetClass = entities.get(0).getClass();

            Dao dao = DaoManager.createDao(connectionSource, targetClass);

            dao.callBatchTasks((Callable<Void>) () -> {
                for (Entity entity : entities) {
                    if (isCreate(entity)) {
                        dao.create(entity);
                    } else {
                        dao.update(entity);
                    }
                    insertOrUpdateChildList(entity.childrenToInsert(), connectionSource);
                    insertOrUpdateChildList(entity.childrenToInsert2(), connectionSource);
                }
                return null;
            });
            return entities;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void insertOrUpdateChildList(List<? extends Entity> toInsert, ConnectionSource connectionSource) {
        if (toInsert.isEmpty()) {
            return;
        }

        try {
            Dao dao = DaoManager.createDao(connectionSource, toInsert.get(0).getClass());

            dao.callBatchTasks((Callable<Void>) () -> {
                for (Entity entity : toInsert) {
                    if (isCreate(entity)) {
                        dao.create(entity);
                    } else {
                        dao.update(entity);
                    }
                    insertOrUpdateChildList(entity.childrenToInsert(), connectionSource);
                }
                return null;
            });

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private List<? extends Entity> gameObjectsToEntities(List<? extends GameObject> gameObjects) {
        if (gameObjects.isEmpty()) {
            return new ArrayList<>();
        }
        Class<? extends Entity> targetClass = daoClassMap.get(gameObjects.get(0).getClass());


        return gameObjects.stream()
                .map(gameObject -> (Object) mapIt(gameObject))
                .map(targetClass::cast)
                .collect(Collectors.toList());
    }

    private Entity mapIt(GameObject object) {
        if (object instanceof BankAccount) {
            return BankAccountMapper.INSTANCE.toEntity((BankAccount) object);
        } else if (object instanceof BroadcastTeamMember) {
            return BroadcastTeamMemberMapper.INSTANCE.toEntity((BroadcastTeamMember) object);
        } else if (object instanceof Contract) {
            return ContractMapper.INSTANCE.toEntity((Contract) object);
        } else if (object instanceof EntourageMember) {
            return EntourageMemberMapper.INSTANCE.toEntity((EntourageMember) object);
        } else if (object instanceof Event) {
            return EventMapper.INSTANCE.toEntity((Event) object);
        } else if (object instanceof EventTemplate) {
            return EventTemplateMapper.INSTANCE.toEntity((EventTemplate) object);
        } else if (object instanceof GameSetting) {
            return GameSettingMapper.INSTANCE.toEntity((GameSetting) object);
        } else if (object instanceof Injury) {
            return InjuryMapper.INSTANCE.toEntity((Injury) object);
        } else if (object instanceof MatchRules) {
            return MatchRulesMapper.INSTANCE.toEntity((MatchRules) object);
        } else if (object instanceof MonthlyReview) {
            return MonthlyReviewMapper.INSTANCE.toEntity((MonthlyReview) object);
        } else if (object instanceof MoraleRelationship) {
            return MoraleRelationshipMapper.INSTANCE.toEntity((MoraleRelationship) object);
        } else if (object instanceof Promotion) {
            return PromotionMapper.INSTANCE.toEntity((Promotion) object);
        } else if (object instanceof RosterSplit) {
            return RosterSplitMapper.INSTANCE.toEntity((RosterSplit) object);
        } else if (object instanceof RosterSplitWorker) {
            return RosterSplitWorkerMapper.INSTANCE.toEntity((RosterSplitWorker) object);
        } else if (object instanceof Segment) {
            return SegmentMapper.INSTANCE.toEntity((Segment) object);
        } else if (object instanceof SegmentTeam) {
            return SegmentTeamMapper.INSTANCE.toEntity((SegmentTeam) object);
        } else if (object instanceof SegmentTemplate) {
            return SegmentTemplateMapper.INSTANCE.toEntity((SegmentTemplate) object);
        } else if (object instanceof Stable) {
            return StableMapper.INSTANCE.toEntity((Stable) object);
        } else if (object instanceof StableMember) {
            return StableMemberMapper.INSTANCE.toEntity((StableMember) object);
        } else if (object instanceof StaffContract) {
            return StaffContractMapper.INSTANCE.toEntity((StaffContract) object);
        } else if (object instanceof StaffMember) {
            return StaffMemberMapper.INSTANCE.toEntity((StaffMember) object);
        } else if (object instanceof TagTeam) {
            return TagTeamMapper.INSTANCE.toEntity((TagTeam) object);
        } else if (object instanceof Title) {
            return TitleMapper.INSTANCE.toEntity((Title) object, new CycleAvoidingMappingContext());
        } else if (object instanceof TitleReign) {
            return TitleReignMapper.INSTANCE.toEntity((TitleReign) object);
        } else if (object instanceof Transaction) {
            return TransactionMapper.INSTANCE.toEntity((Transaction) object);
        } else if (object instanceof Worker) {
            return WorkerMapper.INSTANCE.toEntity((Worker) object);
        } else if (object instanceof WorkerRelationship) {
            return WorkerRelationshipMapper.INSTANCE.toEntity((WorkerRelationship) object);
        }
        throw new NotImplementedException("not yet");
    }

    private GameObject mapItToGameObject(Entity entity) {
        if (entity instanceof BankAccountEntity) {
            return BankAccountMapper.INSTANCE.toClass((BankAccountEntity) entity);
        } else if (entity instanceof BroadcastTeamMemberEntity) {
            return BroadcastTeamMemberMapper.INSTANCE.toClass((BroadcastTeamMemberEntity) entity);
        } else if (entity instanceof ContractEntity) {
            return ContractMapper.INSTANCE.toClass((ContractEntity) entity);
        } else if (entity instanceof EntourageMemberEntity) {
            return EntourageMemberMapper.INSTANCE.toClass((EntourageMemberEntity) entity);
        } else if (entity instanceof EventEntity) {
            return EventMapper.INSTANCE.toClass((EventEntity) entity);
        } else if (entity instanceof EventTemplateEntity) {
            return EventTemplateMapper.INSTANCE.toClass((EventTemplateEntity) entity);
        } else if (entity instanceof GameSettingEntity) {
            return GameSettingMapper.INSTANCE.toClass((GameSettingEntity) entity);
        } else if (entity instanceof InjuryEntity) {
            return InjuryMapper.INSTANCE.toClass((InjuryEntity) entity);
        } else if (entity instanceof MatchRulesEntity) {
            return MatchRulesMapper.INSTANCE.toClass((MatchRulesEntity) entity);
        } else if (entity instanceof MonthlyReviewEntity) {
            return MonthlyReviewMapper.INSTANCE.toClass((MonthlyReviewEntity) entity);
        } else if (entity instanceof MoraleRelationshipEntity) {
            return MoraleRelationshipMapper.INSTANCE.toClass((MoraleRelationshipEntity) entity);
        } else if (entity instanceof PromotionEntity) {
            return PromotionMapper.INSTANCE.toClass((PromotionEntity) entity);
        } else if (entity instanceof RosterSplitEntity) {
            return RosterSplitMapper.INSTANCE.toClass((RosterSplitEntity) entity);
        } else if (entity instanceof RosterSplitWorkerEntity) {
            return RosterSplitWorkerMapper.INSTANCE.toClass((RosterSplitWorkerEntity) entity);
        } else if (entity instanceof SegmentEntity) {
            return SegmentMapper.INSTANCE.toClass((SegmentEntity) entity);
        } else if (entity instanceof SegmentTeamEntity) {
            return SegmentTeamMapper.INSTANCE.toClass((SegmentTeamEntity) entity);
        } else if (entity instanceof SegmentTemplateEntity) {
            return SegmentTemplateMapper.INSTANCE.toClass((SegmentTemplateEntity) entity);
        } else if (entity instanceof StableEntity) {
            return StableMapper.INSTANCE.toClass((StableEntity) entity);
        } else if (entity instanceof StableMemberEntity) {
            return StableMemberMapper.INSTANCE.toClass((StableMemberEntity) entity);
        } else if (entity instanceof StaffContractEntity) {
            return StaffContractMapper.INSTANCE.toClass((StaffContractEntity) entity);
        } else if (entity instanceof StaffMemberEntity) {
            return StaffMemberMapper.INSTANCE.toClass((StaffMemberEntity) entity);
        } else if (entity instanceof TagTeamEntity) {
            return TagTeamMapper.INSTANCE.toClass((TagTeamEntity) entity);
        } else if (entity instanceof TitleEntity) {
            return TitleMapper.INSTANCE.toClass((TitleEntity) entity, new CycleAvoidingMappingContext());
        } else if (entity instanceof TitleReignEntity) {
            return TitleReignMapper.INSTANCE.toClass((TitleReignEntity) entity);
        } else if (entity instanceof TransactionEntity) {
            return TransactionMapper.INSTANCE.toClass((TransactionEntity) entity);
        } else if (entity instanceof WorkerEntity) {
            return WorkerMapper.INSTANCE.toClass((WorkerEntity) entity);
        } else if (entity instanceof WorkerRelationshipEntity) {
            return WorkerRelationshipMapper.INSTANCE.toClass((WorkerRelationshipEntity) entity);
        }
        throw new NotImplementedException("not yet");
    }

    private List<? extends GameObject> entitiesToGameObjects(List<? extends Entity> entities, Class<? extends GameObject> targetClass) {
        if (entities.isEmpty()) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(this::mapItToGameObject)
                .map(targetClass::cast)
                .collect(Collectors.toList());
    }


    private boolean isCreate(Entity entity) {
        return List.of(entity.getClass().getDeclaredFields()).stream().anyMatch(field -> {
                    try {
                        boolean isCreate = false;
                        if (field.isAnnotationPresent(DatabaseField.class) &&
                                field.getAnnotation(DatabaseField.class).generatedId()) {
                            field.setAccessible(true);
                            isCreate = field.getLong(entity) == 0;
                            field.setAccessible(false);
                        }
                        return isCreate;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
        );
    }

    private void createTables(String url) {
        try {

            ConnectionSource connectionSource = new JdbcConnectionSource(url);

            List<Class> classes = List.of(
                    WorkerEntity.class,
                    PromotionEntity.class,
                    StableEntity.class,
                    StableMemberEntity.class,
                    RosterSplitEntity.class,
                    RosterSplitWorkerEntity.class,
                    ContractEntity.class,
                    TagTeamEntity.class,
                    TagTeamWorkerEntity.class,
                    TitleEntity.class,
                    TitleReignEntity.class,
                    TitleReignWorkerEntity.class,
                    EventTemplateEntity.class,
                    StaffMemberEntity.class,
                    StaffContractEntity.class,
                    WorkerRelationshipEntity.class,
                    MoraleRelationshipEntity.class,
                    BankAccountEntity.class,
                    TransactionEntity.class,
                    EntourageMemberEntity.class,
                    EventEntity.class,
                    SegmentEntity.class,
                    SegmentTeamEntity.class,
                    SegmentTeamEntourageEntity.class,
                    SegmentTeamWorkerEntity.class,
                    MatchTitleEntity.class,
                    BroadcastTeamMemberEntity.class,
                    InjuryEntity.class,
                    SegmentTemplateEntity.class,
                    NewsItemEntity.class,
                    NewsItemWorkerEntity.class,
                    NewsItemPromotionEntity.class,
                    GameSettingEntity.class,
                    MonthlyReviewEntity.class,
                    MatchRulesEntity.class);

            for (Class entityClass : classes) {
                Dao dao = DaoManager.createDao(connectionSource, entityClass);
                TableUtils.dropTable(dao, true);
                TableUtils.createTable(connectionSource, entityClass);
            }

            StaticDataHelper staticDataHelper = new StaticDataHelper();
            staticDataHelper.insertStaticData(connectionSource);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
