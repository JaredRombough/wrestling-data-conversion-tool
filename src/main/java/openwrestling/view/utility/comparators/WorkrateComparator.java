package openwrestling.view.utility.comparators;

import openwrestling.model.gameObjects.Worker;
import openwrestling.model.utility.ModelUtils;

import java.util.Comparator;

public class WorkrateComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker w1, Worker w2) {
        if (w1 != null && w2 != null) {

            return -Integer.compare(ModelUtils.getMatchWorkRating(w1), ModelUtils.getMatchWorkRating(w2));
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Workrate";
    }

}
