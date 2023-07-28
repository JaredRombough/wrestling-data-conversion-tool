package openwrestling.model.interfaces;

import openwrestling.model.gameObjects.Promotion;

import java.time.LocalDate;
import java.util.List;

public interface iNewsItem {

    String getSummary();

    LocalDate getDate();

    List<Promotion> getPromotions();

}
