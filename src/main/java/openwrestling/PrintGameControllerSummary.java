package openwrestling;

import openwrestling.model.controller.GameController;
import openwrestling.model.controller.nextDay.PromotionSettingsManager;
import openwrestling.model.gameObjects.Contract;
import openwrestling.model.gameObjects.Event;
import openwrestling.model.gameObjects.MoraleRelationship;
import openwrestling.model.gameObjects.Promotion;
import openwrestling.model.gameObjects.Segment;
import openwrestling.model.gameObjects.Worker;
import openwrestling.model.utility.ModelUtils;
import org.apache.logging.log4j.Level;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static openwrestling.model.utility.ModelUtils.currencyString;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class PrintGameControllerSummary extends Logging {

    public void printSummary(GameController gameController, GameController baseline) {
        logger.log(Level.FATAL, "**** DB SUMMARY");
        logger.log(Level.FATAL, "Total workers: " + gameController.getWorkerManager().getWorkers().size());
        logger.log(Level.FATAL, "Total contracts signed: " + gameController.getContractManager().getContracts().size());
        logger.log(Level.FATAL, "Had contracts: " +
                gameController.getContractManager().getContracts().stream()
                        .map(Contract::getWorker)
                        .collect(Collectors.groupingBy(Worker::getWorkerID))
                        .size());
        logger.log(Level.FATAL, "Had segments: " +
                (int) gameController.getWorkerManager().getWorkers().stream()
                        .filter(worker -> isNotEmpty(gameController.getSegmentManager().getSegments(worker))).count());

        logger.log(Level.FATAL, "Total promotions: " + gameController.getPromotionManager().getPromotions().size());

        gameController.getPromotionManager().getPromotions().forEach(promotion -> {
            logger.log(Level.FATAL, "**** PROMOTION SUMMARY: " + promotion.getName());

            logger.log(Level.FATAL, "Funds: " + currencyString(gameController.getBankAccountManager().getBankAccount(promotion).getFunds()));
            logger.log(Level.FATAL, "Initial Funds: " + currencyString(baseline.getBankAccountManager().getBankAccount(promotion).getFunds()));

            List<Worker> roster = gameController.getWorkerManager().getRoster(promotion);
            logger.log(Level.FATAL, "Total contracts signed: " + gameController.getContractManager().getContracts().stream()
                    .filter(contract -> contract.getPromotion().equals(promotion))
                    .count());
            logger.log(Level.FATAL, "Total workers on current roster: " + roster.size());
            logger.log(Level.FATAL, "Minimum roster size: " + (new PromotionSettingsManager()).getMinimumRosterSize(promotion));

            //printContractInfo(gameController, promotion);

            List<MoraleRelationship> moraleRelationships = gameController.getRelationshipManager().getMoraleRelationships(promotion);
            logger.log(Level.FATAL, "Positive morale: " +
                    moraleRelationships.stream()
                            .filter(moraleRelationship -> moraleRelationship.getLevel() > 0)
                            .count());
            logger.log(Level.FATAL, "Negative morale: " +
                    moraleRelationships.stream()
                            .filter(moraleRelationship -> moraleRelationship.getLevel() < 0)
                            .count());

            logger.log(Level.FATAL, "Had segments: " +
                    (int) roster.stream()
                            .filter(worker -> isNotEmpty(gameController.getSegmentManager().getSegments(worker))).count());

       //     logger.log(Level.FATAL, "Total feuds (active): " + gameController.getFeudManager().getActiveFeuds(promotion).size());
      //      logger.log(Level.FATAL, "Total feuds (inactive): " + gameController.getFeudManager().getInActiveFeuds(promotion).size());
         //   printFeuds(gameController, promotion);

            logger.log(Level.FATAL, "Total titles: " + gameController.getTitleManager().getTitles(promotion).size());
            gameController.getTitleManager().getTitles(promotion).forEach(title -> {
                logger.printf(Level.FATAL, "%s Champion: %s", title.getName(), ModelUtils.slashNames(title.getChampions()));
                title.getTitleReigns().forEach(titleReign ->
                        logger.log(Level.FATAL, gameController.getTitleManager().titleReignString(titleReign)));
                List<Segment> defenses = gameController.getSegmentManager().getSegments(title.getChampions()).stream()
                        .filter(segment -> isNotEmpty(segment.getTitles()))
                        .filter(segment -> segment.getTitles().contains(title))
                        .collect(Collectors.toList());
                if (isNotEmpty(defenses)) {
                    defenses.forEach(segment ->
                            logger.log(Level.FATAL, gameController.getSegmentStringService().getSegmentString(segment))
                    );

                }
            });


            List<Event> pastEvents = gameController.getEventManager().getPastEvents(promotion, gameController.getDateManager().today());
            logger.log(Level.FATAL, "Total events held: " + pastEvents.size());
            logger.log(Level.FATAL, "Total segments: " + pastEvents.stream().mapToLong(event -> gameController.getSegmentManager().getSegments(event).size()).sum());
            logger.log(Level.FATAL, "Total events without full segments: " +
                    pastEvents.stream()
                            .filter(event -> event.getDefaultDuration() == 60 && gameController.getSegmentManager().getSegments(event).size() < 7 ||
                                    event.getDefaultDuration() == 120 && gameController.getSegmentManager().getSegments(event).size() < 12)
                            .count());
            //  printEvents(gameController, promotion);
        });

//        gameController.getEventManager().getEventsOnDate(gameController.getDateManager().today().minusDays(1))
//                .forEach(event ->
//                        logger.log(Level.INFO, gameController.getSegmentStringService().generateSummaryString(event)));
    }

    private void printContractInfo(GameController gameController, Promotion promotion) {
        List<Contract> currentContracts = gameController.getContractManager().getActiveContracts(promotion).stream()
                .filter(contract -> contract.getEndDate().isAfter(gameController.getDateManager().today().minusDays(1)))
                .sorted(Comparator.comparing(Contract::getStartDate))
                .collect(Collectors.toList());

        logger.log(Level.FATAL, "Current contracts (should match or exceed minimum roster size): " + currentContracts.size());

        currentContracts.forEach(contract -> {
            logger.printf(Level.FATAL, "%d %s  Start: %s End:%s Morale: %d",
                    contract.getWorker().getWorkerID(),
                    contract.getWorker().getName(),
                    contract.getStartDate(),
                    contract.getEndDate(),
                    gameController.getRelationshipManager().getMoraleRelationship(contract.getWorker(), promotion).getLevel()
            );
        });

        List<Contract> pastContracts = gameController.getContractManager().getContracts(promotion).stream()
                .filter(contract -> contract.getEndDate().isBefore(gameController.getDateManager().today()))
                .sorted(Comparator.comparing(Contract::getStartDate))
                .collect(Collectors.toList());

        logger.log(Level.FATAL, "Past contracts: " + pastContracts.size());
        pastContracts.forEach(contract -> {
            logger.printf(Level.FATAL, "%d %s  Start: %s End:%s Morale: %d",
                    contract.getWorker().getWorkerID(),
                    contract.getWorker().getName(),
                    contract.getStartDate(),
                    contract.getEndDate(),
                    gameController.getRelationshipManager().getMoraleRelationship(contract.getWorker(), promotion).getLevel()
            );
        });

        List<Contract> pastContractsForWorkersNotOnRoster = gameController.getContractManager().getContracts(promotion).stream()
                .filter(contract -> contract.getEndDate().isBefore(gameController.getDateManager().today()))
                .filter(contract -> currentContracts.stream().noneMatch(currentContract -> currentContract.getWorker().equals(contract.getWorker())))
                .sorted(Comparator.comparing(Contract::getStartDate))
                .collect(Collectors.toList());

        logger.log(Level.FATAL, "Past contracts (workers not on roster): " + pastContractsForWorkersNotOnRoster.size());
        pastContractsForWorkersNotOnRoster.forEach(contract -> {
            logger.printf(Level.FATAL, "%d %s  Start: %s End:%s Morale: %d",
                    contract.getWorker().getWorkerID(),
                    contract.getWorker().getName(),
                    contract.getStartDate(),
                    contract.getEndDate(),
                    gameController.getRelationshipManager().getMoraleRelationship(contract.getWorker(), promotion).getLevel()
            );
        });
    }

    private void printEvents(GameController gameController, Promotion promotion) {
        gameController.getEventManager().getPastEvents(promotion, gameController.getDateManager().today()).stream()
                .sorted(Comparator.comparing(Event::getDate))
                .forEach(event -> {
                    logger.log(Level.FATAL, "Date: " + event.getDate().toString());
                    logger.log(Level.FATAL, "Name: " + event.getName());
                    logger.log(Level.FATAL, "Id: " + event.getEventID());
                    List<Segment> segments = gameController.getSegmentManager().getSegments(event);
                    logger.log(Level.FATAL, "Segments: " + segments.size());
                    segments.forEach(segment ->
                            logger.log(Level.FATAL, gameController.getSegmentStringService().getSegmentString(segment))
                    );
                });
    }

//    private void printFeuds(GameController gameController, Promotion promotion) {
//        gameController.getFeudManager().getActiveFeuds(promotion).forEach(feud ->
//                logger.printf(Level.FATAL, "%d %s vs %s StartDate: %s EndDate: %s Title: %s Event: %s", feud.getFeudID(), feud.getWinnerName(), feud.getLoserName(), feud.getStartDate(), feud.getEndDate(), feud.getTitle() == null ? "none" : feud.getTitle().getName(), feud.getEventTemplate() == null ? "none" : feud.getEventTemplate().getName())
//        );
//
//        gameController.getFeudManager().getInActiveFeuds(promotion).forEach(feud ->
//                logger.printf(Level.FATAL, "%d %s vs %s StartDate: %s EndDate: %s Title: %s Event: %s", feud.getFeudID(), feud.getWinnerName(), feud.getLoserName(), feud.getStartDate(), feud.getEndDate(), feud.getTitle() == null ? "none" : feud.getTitle().getName(), feud.getEventTemplate() == null ? "none" : feud.getEventTemplate().getName())
//        );
//    }

    private void printYesterdayEvents(GameController gameController) {

        gameController.getEventManager().getEventsOnDate(gameController.getDateManager().today().minusDays(1))
                .forEach(event ->
                        logger.log(Level.INFO, gameController.getSegmentStringService().generateSummaryString(event)));
    }
}
