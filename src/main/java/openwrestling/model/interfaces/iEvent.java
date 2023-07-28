package openwrestling.model.interfaces;

import openwrestling.model.gameObjects.Promotion;

import java.time.LocalDate;

public interface iEvent {

    Promotion getPromotion();

    LocalDate getDate();
}
