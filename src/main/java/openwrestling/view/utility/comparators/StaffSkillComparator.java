package openwrestling.view.utility.comparators;

import openwrestling.model.gameObjects.StaffMember;

import java.util.Comparator;

public class StaffSkillComparator implements Comparator<StaffMember> {

    @Override
    public int compare(StaffMember staffMember1, StaffMember staffMember2) {
        if (staffMember1 != null && staffMember2 != null) {

            return -Integer.compare(staffMember1.getSkill(), staffMember2.getSkill());
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Skill";
    }

}
