package openwrestling.model.utility;

import openwrestling.model.SegmentItem;
import openwrestling.model.gameObjects.BroadcastTeamMember;
import openwrestling.model.gameObjects.Promotion;
import openwrestling.model.gameObjects.Segment;
import openwrestling.model.gameObjects.SegmentTeam;
import openwrestling.model.gameObjects.SegmentTemplate;
import openwrestling.model.gameObjects.Title;
import openwrestling.model.gameObjects.Worker;
import openwrestling.model.segment.constants.SegmentType;
import openwrestling.model.segment.constants.TeamType;
import openwrestling.model.segment.opitons.MatchRules;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ModelUtils {

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static String currencyString(long amount) {
        Locale locale = new Locale("en", "US");
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(locale);
        moneyFormat.setMaximumFractionDigits(0);
        return moneyFormat.format(amount);
    }

    public static String dateString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy (cccc)"));
    }

    public static String timeString(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return String.format("%d:%02d", hours, minutes);
    }

    public static String slashNames(List<? extends SegmentItem> segmentItems) {
        return slashNames(segmentItems, "?");
    }

    public static String slashNamesBroadcastTeam(List<BroadcastTeamMember> broadcastTeamMembers) {
        List<? extends SegmentItem> segmentItems = broadcastTeamMembers.stream()
                .map(broadcastTeamMember ->
                        broadcastTeamMember.getWorker() != null ?
                                broadcastTeamMember.getWorker() :
                                broadcastTeamMember.getStaffMember())
                .collect(Collectors.toList());
        return slashNames(segmentItems, "?");
    }

    public static String slashNames(List<? extends SegmentItem> segmentItems, String placeholder) {
        if (segmentItems.isEmpty()) {
            return placeholder;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < segmentItems.size(); i++) {
            sb.append(segmentItems.get(i).toString());
            if (segmentItems.size() - i > 1) {
                sb.append("/");
            }
        }

        return sb.toString();
    }

    public static String andItemsLongName(List<? extends SegmentItem> items) {
        List<String> slashed = new ArrayList<>();
        for (SegmentItem item : items) {
            slashed.add(item.getLongName());
        }
        return slashed.isEmpty() ? "?" : joinGrammatically(slashed);
    }

    public static String andTeams(List<SegmentTeam> teams) {
        List<String> slashed = new ArrayList<>();
        for (SegmentTeam team : teams) {
            slashed.add(slashNames(team.getWorkers()));
        }
        return slashed.isEmpty() ? "?" : joinGrammatically(slashed);
    }

    public static String joinGrammatically(final List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return list.size() > 1
                ? String.join(", ", list.subList(0, list.size() - 1))
                .concat(String.format("%s and ", list.size() > 2 ? "," : ""))
                .concat(list.get(list.size() - 1))
                : list.get(0);
    }

    public static String slashShortNames(List<? extends SegmentItem> workers) {
        if (workers.isEmpty()) {
            return "?";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < workers.size(); i++) {
            sb.append(workers.get(i).getShortName());
            if (workers.size() - i > 1) {
                sb.append("/");
            }
        }

        return sb.toString();
    }

    public static int weekOfMonth(LocalDate date) {
        Calendar ca1 = Calendar.getInstance();
        ca1.set(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
        return ca1.get(Calendar.WEEK_OF_MONTH);
    }

    //the maximum popularity worker the promotion can hire
    public static int maxPopularity(Promotion promotion) {
        return promotion.getLevel() * 20;
    }

    public static int getMatchWorkRating(Worker worker) {
        return getWeightedScore(new Integer[]{
                worker.getFlying(),
                worker.getWrestling(),
                worker.getStriking(),
                worker.getCharisma()
        });
    }

    public static int getMatchWorkRating(Worker worker, MatchRules matchRules) {
        int flying = worker.getFlying() + (matchRules.getFlyingModifier() * worker.getFlying() / 100);
        int wrestling = worker.getWrestling() + (matchRules.getWrestingModifier() * worker.getWrestling() / 100);
        int striking = worker.getStriking() + (matchRules.getStrikingModifier() * worker.getStriking() / 100);
        return getWeightedScore(new Integer[]{
                flying,
                wrestling,
                striking,
                worker.getCharisma()
        });
    }

    public static int getWeightedScore(Integer[] attributes) {
        Arrays.sort(attributes, Collections.reverseOrder());

        return getPrioritizedScore(attributes);
    }

    public static int getPrioritizedScore(Integer[] attributes) {
        int totalScore = 0;

        for (int i = 0; i < attributes.length; i++) {
            totalScore += (attributes[i] * (attributes.length - i));
        }

        int denominator = (attributes.length * (attributes.length + 1)) / 2;
        return totalScore / denominator;
    }

    public static List<Worker> getWorkersFromSegmentItems(List<SegmentItem> segmentItems) {
        List<Worker> workers = new ArrayList<>();

        segmentItems.forEach((item) -> {
            if (item instanceof Worker) {
                workers.add((Worker) item);
            }
        });
        return workers;
    }

    public static List<Title> getTitleViewsFromSegmentItems(List<SegmentItem> segmentItems) {
        List<Title> titles = new ArrayList<>();

        segmentItems.forEach((item) -> {
            if (item instanceof Title) {
                titles.add((Title) item);
            }
        });
        return titles;
    }

    public static Segment getSegmentFromTemplate(SegmentTemplate template) {
        return getSegmentFromTeams(template.getSegmentTeams());
    }

    public static Segment getSegmentFromTeams(List<SegmentTeam> segmentTeams) {
        Segment challengeMatch = new Segment(SegmentType.MATCH);
        segmentTeams.forEach(team -> {
            if (TeamType.CHALLENGER.equals(team.getType()) || TeamType.CHALLENGED.equals(team.getType())) {
                SegmentTeam segmentTeam = new SegmentTeam();
                segmentTeam.setType(TeamType.CHALLENGER.equals(team.getType()) ? TeamType.WINNER : TeamType.LOSER);
                segmentTeam.setWorkers(team.getWorkers());
                challengeMatch.getSegmentTeams().add(segmentTeam);
            }
        });
        return challengeMatch;
    }

}
