package openwrestling.view.utility.comparators;

import java.util.Comparator;
import openwrestling.model.gameObjects.Worker;

public class WorkerPopularityComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker w1, Worker w2) {
        if (w1 != null && w2 != null) {

            return -Integer.valueOf(w1.getPopularity()).compareTo(w2.getPopularity());
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Popularity";
    }

}
