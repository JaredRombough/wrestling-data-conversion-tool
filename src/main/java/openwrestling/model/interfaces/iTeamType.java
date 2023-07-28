package openwrestling.model.interfaces;

import openwrestling.model.SegmentItem;
import openwrestling.model.segment.constants.TeamType;

import java.util.List;

public interface iTeamType {
    boolean droppable(SegmentItem segmentItem);

    List<TeamType> getShared();
}
