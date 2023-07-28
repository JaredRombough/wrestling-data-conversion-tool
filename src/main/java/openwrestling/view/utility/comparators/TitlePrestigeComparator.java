package openwrestling.view.utility.comparators;

import openwrestling.model.gameObjects.Title;

import java.util.Comparator;

public class TitlePrestigeComparator implements Comparator<Title> {

    @Override
    public int compare(Title title1, Title title2) {
        if (title1 != null && title2 != null) {

            return -Integer.compare(title1.getPrestige(), title2.getPrestige());
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Prestige";
    }

}
