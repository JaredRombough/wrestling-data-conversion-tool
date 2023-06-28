package openwrestling.model.gameObjects;

import lombok.Getter;
import lombok.Setter;
import openwrestling.model.SegmentItem;
import openwrestling.model.interfaces.iContract;
import openwrestling.model.interfaces.iPerson;
import openwrestling.model.segment.constants.Gender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Worker extends GameObject implements Serializable, SegmentItem, iPerson {

    private final List<Contract> contracts = new ArrayList<>();
    private long workerID;
    private String name;
    private String shortName;
    private String imageFileName;
    private int importKey;
    private int striking;
    private int flying;
    private int wrestling;
    private int charisma;
    private int behaviour;
    private int popularity;
    private int age;
    private Gender gender;
    private boolean fullTime;
    private boolean mainRoster;
    private int minimumPopularity = 0;
    private Worker manager;

    private boolean highSpots;
    private boolean shooting;
    private boolean fonzFactor;
    private boolean superstarLook;
    private boolean diva;
    private boolean menacing;
    private boolean announcer;
    private boolean booker;
    private boolean trainer;

    private boolean speaks;

    private String nationality;

    private String primaryFinisherName;
    private String primaryFinisherType;
    private String secondaryFinisherName;
    private String secondaryFinisherType;

    private String weight;
    private int birthMonth;

    private Long wage;

    private int stiffness;
    private int selling;
    private int attitude;


    public void addContract(Contract contract) {
        contracts.add(contract);
    }

    public void removeContract(Contract contract) {
        contracts.remove(contract);
    }

    @Override
    public iContract getContract(Promotion promotion) {
        for (Contract contract : contracts) {
            if (contract.getPromotion().equals(promotion)) {
                return contract;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getName();
    }


    /**
     * @param popularity the popularity to set
     */
    public void setPopularity(int popularity) {
        this.popularity = popularity;
        //once workers reach a level of popularity, they can never  drop below 50% of that
        if ((popularity / 2) > getMinimumPopularity()) {
            setMinimumPopularity(popularity / 2);
        }
    }

    @Override
    public iContract getContract() {
        return contracts.isEmpty() ? null : contracts.get(0);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Worker &&
                Objects.equals(((Worker) object).getWorkerID(), workerID);
    }

}
