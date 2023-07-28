package openwrestling.model.interfaces;

import openwrestling.model.gameObjects.Promotion;
import openwrestling.model.gameObjects.StaffMember;
import openwrestling.model.gameObjects.Worker;

import java.time.LocalDate;

/**
 *
 * @author jared
 */
public interface iContract {

    iPerson getPerson();

    LocalDate getEndDate();

    LocalDate getStartDate();

    Promotion getPromotion();

    boolean isExclusive();

    int getMonthlyCost();

    int getAppearanceCost();

    void setActive(boolean active);

    Worker getWorker();

    StaffMember getStaff();
    
    LocalDate getLastShowDate();
    
    void setEndDate(LocalDate date);

}
