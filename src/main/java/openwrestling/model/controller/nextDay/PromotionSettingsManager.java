package openwrestling.model.controller.nextDay;

import openwrestling.model.gameObjects.Event;
import openwrestling.model.gameObjects.Promotion;
import openwrestling.model.gameObjects.Segment;
import openwrestling.model.segment.constants.SegmentType;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PromotionSettingsManager {

    public int getMinimumRosterSize(Promotion promotion) {
        return promotion.getLevel() * 15;
    }

    public boolean winnerWinsFeudSegment() {
        return RandomUtils.nextInt(1, 4) == 1;
    }

    public boolean isTitleChange() {
        return RandomUtils.nextInt(1, 5) == 1;
    }

    public int getTeamSize() {
        if (RandomUtils.nextInt(1, 3) == 1) {
            return 2;
        }
        return 1;
    }

    public List<Segment> getSegments(Event event) {
        List<Segment> segments = new ArrayList<>();
        int duration = event.getDefaultDuration();
        List<Integer> lengths = List.of(15, 10, 10, 10, 5, 5, 5);
        List<SegmentType> types = List.of(SegmentType.MATCH, SegmentType.MATCH, SegmentType.MATCH, SegmentType.MATCH,
                SegmentType.ANGLE, SegmentType.ANGLE, SegmentType.ANGLE);

        if (duration == 180) {
            List<Integer> extraLongEventLengths = List.of(30, 15, 10, 5);
            List<SegmentType> extraLongEventSegments = List.of(SegmentType.MATCH, SegmentType.MATCH, SegmentType.MATCH,
                    SegmentType.ANGLE);
            for (int i = 0; i < extraLongEventLengths.size(); i++) {
                Segment segment = new Segment();
                segment.setSegmentLength(extraLongEventLengths.get(i));
                segment.setSegmentType(extraLongEventSegments.get(i));

                segments.add(segment);
            }
        }

        if (duration == 120 || duration == 180) {
            List<Integer> longEventLengths = List.of(20, 20, 10, 5, 5);
            List<SegmentType> longEventSegments = List.of(SegmentType.MATCH, SegmentType.MATCH, SegmentType.MATCH,
                    SegmentType.ANGLE, SegmentType.ANGLE);
            for (int i = 0; i < longEventLengths.size(); i++) {
                Segment segment = new Segment();
                segment.setSegmentLength(longEventLengths.get(i));
                segment.setSegmentType(longEventSegments.get(i));

                segments.add(segment);
            }
        }

        for (int i = 0; i < lengths.size(); i++) {
            Segment segment = new Segment();
            segment.setSegmentLength(lengths.get(i));
            segment.setSegmentType(types.get(i));

            segments.add(segment);
        }
        Segment mainEvent = segments.remove(0);
        Collections.shuffle(segments);
        segments.add(0, mainEvent);
        return segments;
    }

    public int getMaxFeuds() {
        return 10;
    }


}
