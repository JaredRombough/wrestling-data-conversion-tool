package openwrestling.view.utility.comparators;

import openwrestling.model.gameObjects.Worker;

import java.util.Comparator;

public class WorkerWrestlingComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker w1, Worker w2) {
        if (w1 != null && w2 != null) {

            return -Integer.compare(w1.getWrestling(), w2.getWrestling());
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Wrestling";
    }

}
