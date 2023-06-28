package openwrestling.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import openwrestling.model.segment.constants.Gender;

import java.util.Collection;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DatabaseTable(tableName = "workers")
public class WorkerEntity extends Entity {

    @ForeignCollectionField(eager = true)
    public ForeignCollection<ContractEntity> contractEntities;

    @ForeignCollectionField(eager = true)
    public Collection<StableMemberEntity> workerGroups;

    @DatabaseField(generatedId = true)
    private long workerID;

    @DatabaseField
    private long importKey;

    @DatabaseField
    private String name;

    @DatabaseField
    private String shortName;

    @DatabaseField
    private String imageFileName;

    @DatabaseField
    private int striking;

    @DatabaseField
    private int flying;

    @DatabaseField
    private int wrestling;

    @DatabaseField
    private int charisma;

    @DatabaseField
    private int behaviour;

    @DatabaseField
    private int popularity;

    @DatabaseField
    private int age;

    @DatabaseField
    private Gender gender;

    @DatabaseField
    private boolean fullTime;

    @DatabaseField
    private boolean mainRoster;

    @DatabaseField
    private boolean highSpots;

    @DatabaseField
    private boolean shooting;

    @DatabaseField
    private boolean fonzFactor;

    @DatabaseField
    private boolean superstarLook;

    @DatabaseField
    private boolean diva;

    @DatabaseField
    private boolean menacing;

    @DatabaseField
    private boolean announcer;

    @DatabaseField
    private boolean booker;

    @DatabaseField
    private boolean trainer;

    @DatabaseField
    private int birthMonth;

    @DatabaseField
    private String weight;

    @DatabaseField
    private boolean speaks;

    @DatabaseField
    private String nationality;

    @DatabaseField
    private Long wage;

    @DatabaseField
    private String primaryFinisherName;

    @DatabaseField
    private String primaryFinisherType;

    @DatabaseField
    private String secondaryFinisherName;

    @DatabaseField
    private String secondaryFinisherType;

    @DatabaseField
    private int stiffness;

    @DatabaseField
    private int selling;

    @DatabaseField
    private int attitude;

    @DatabaseField
    private int minimumPopularity;

    @DatabaseField(foreign = true)
    private WorkerEntity manager;

    @ForeignCollectionField
    private Collection<SegmentTeamWorkerEntity> segmentTeams;

    @ForeignCollectionField
    private Collection<SegmentTeamEntourageEntity> entourageTeams;

}
