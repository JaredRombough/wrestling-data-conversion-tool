package openwrestling.view.utility.comparators;

import openwrestling.model.SegmentItem;

import java.util.Comparator;

public class SegmentItemBehaviourComparator implements Comparator<SegmentItem> {

    @Override
    public int compare(SegmentItem segmentItem1, SegmentItem segmentItem2) {
        if (segmentItem1 != null && segmentItem2 != null) {

            return -Integer.compare(segmentItem1.getBehaviour(), segmentItem2.getBehaviour());
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Behaviour";
    }

}
