package openwrestling.model.controller;

import lombok.Getter;
import openwrestling.Logging;
import openwrestling.database.Database;
import openwrestling.manager.BankAccountManager;
import openwrestling.manager.BroadcastTeamManager;
import openwrestling.manager.ContractManager;
import openwrestling.manager.DateManager;
import openwrestling.manager.EntourageManager;
import openwrestling.manager.EventManager;
import openwrestling.manager.GameObjectManager;
import openwrestling.manager.GameSettingManager;
import openwrestling.manager.InjuryManager;
import openwrestling.manager.MatchRulesManager;
import openwrestling.manager.MonthlyReviewManager;
import openwrestling.manager.NewsManager;
import openwrestling.manager.PromotionManager;
import openwrestling.manager.RelationshipManager;
import openwrestling.manager.RosterSplitManager;
import openwrestling.manager.SegmentManager;
import openwrestling.manager.SegmentStringService;
import openwrestling.manager.StableManager;
import openwrestling.manager.StaffManager;
import openwrestling.manager.TagTeamManager;
import openwrestling.manager.TitleManager;
import openwrestling.manager.WorkerManager;
import openwrestling.model.factory.ContractFactory;
import openwrestling.model.factory.EventFactory;
import openwrestling.model.factory.MatchFactory;

import java.io.Serializable;
import java.util.List;

@Getter
public final class GameController extends Logging implements Serializable {

    private final ContractFactory contractFactory;
    private final EventFactory eventFactory;
    private final MatchFactory matchFactory;

    private final DateManager dateManager;
    private final ContractManager contractManager;
    private final EventManager eventManager;
    private final TitleManager titleManager;
    private final WorkerManager workerManager;
    private final PromotionManager promotionManager;
    private final TagTeamManager tagTeamManager;
    private final SegmentManager segmentManager;
    private final InjuryManager injuryManager;
    private final NewsManager newsManager;
    private final StaffManager staffManager;
    private final StableManager stableManager;
    private final RelationshipManager relationshipManager;
    private final BankAccountManager bankAccountManager;
    private final RosterSplitManager rosterSplitManager;
    private final EntourageManager entourageManager;
    private final BroadcastTeamManager broadcastTeamManager;
    private final GameSettingManager gameSettingManager;
    private final MonthlyReviewManager monthlyReviewManager;
    private final MatchRulesManager matchRulesManager;
    private final SegmentStringService segmentStringService;
    private final int EVENT_MONTHS = 6;
    private final List<GameObjectManager> managers;

    public GameController(Database database) {

        dateManager = new DateManager(database);

        gameSettingManager = new GameSettingManager(database);

        bankAccountManager = new BankAccountManager(database);
        promotionManager = new PromotionManager(database, bankAccountManager, gameSettingManager);

        newsManager = new NewsManager(database);

        relationshipManager = new RelationshipManager(database);

        broadcastTeamManager = new BroadcastTeamManager(database);

        contractManager = new ContractManager(database, bankAccountManager);

        workerManager = new WorkerManager(database, contractManager);
        rosterSplitManager = new RosterSplitManager(database, workerManager);
        staffManager = new StaffManager(database, contractManager);
        titleManager = new TitleManager(database, dateManager, workerManager);
        stableManager = new StableManager(database, workerManager);
        entourageManager = new EntourageManager(database, workerManager, contractManager);
        tagTeamManager = new TagTeamManager(database, workerManager);
        injuryManager = new InjuryManager(database, newsManager, workerManager, dateManager);
        monthlyReviewManager = new MonthlyReviewManager(database);

        segmentManager = new SegmentManager(database, dateManager);


        matchRulesManager = new MatchRulesManager(database);
        matchRulesManager.selectData();

        eventManager = new EventManager(database,
                contractManager,
                dateManager,
                segmentManager);

        contractFactory = new ContractFactory(contractManager);

        matchFactory = new MatchFactory(dateManager, staffManager);

        eventFactory = new EventFactory(
                eventManager,
                matchFactory,
                titleManager,
                workerManager,
                tagTeamManager,
                stableManager,
                relationshipManager,
                newsManager);


        segmentStringService = new SegmentStringService(
                segmentManager,
                tagTeamManager,
                stableManager,
                eventManager,
                contractManager
        );


        managers = List.of(
                bankAccountManager,
                broadcastTeamManager,
                contractManager,
                dateManager,
                entourageManager,
                eventManager,
                injuryManager,
                newsManager,
                promotionManager,
                relationshipManager,
                segmentManager,
                staffManager,
                workerManager,
                stableManager,
                rosterSplitManager,
                tagTeamManager,
                titleManager,
                monthlyReviewManager,
                matchRulesManager
        );

    }


    public void loadGameDataFromDatabase() {
        managers.forEach(GameObjectManager::selectData);
        eventManager.refreshSegmentEvents();
    }



}
