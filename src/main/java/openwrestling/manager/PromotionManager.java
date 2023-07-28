package openwrestling.manager;

import lombok.Getter;
import openwrestling.database.Database;
import openwrestling.model.gameObjects.Promotion;
import openwrestling.model.gameObjects.financial.BankAccount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class PromotionManager extends GameObjectManager implements Serializable {

    private final Map<Long, Promotion> promotionMap = new HashMap<>();

    private final BankAccountManager bankAccountManager;
    private final GameSettingManager gameSettingManager;

    public PromotionManager(Database database, BankAccountManager bankAccountManager, GameSettingManager gameSettingManager) {
        super(database);
        this.bankAccountManager = bankAccountManager;
        this.gameSettingManager = gameSettingManager;
    }

    @Override
    public void selectData() {
        List<Promotion> promotions = getDatabase().selectAll(Promotion.class);
        promotions.forEach(promotion -> promotionMap.put(promotion.getPromotionID(), promotion));
    }


    public Promotion getPromotion(Long promotionID) {
        return promotionMap.get(promotionID);
    }

    public List<Promotion> getPromotions() {
        return new ArrayList<>(promotionMap.values());
    }

    public Promotion refreshPromotion(Promotion promotion) {
        return promotionMap.get(promotion.getPromotionID());
    }

    public List<Promotion> createPromotions(List<Promotion> promotions) {
        List<Promotion> saved = getDatabase().insertList(promotions);
        bankAccountManager.createBankAccounts(
                saved.stream()
                        .map(promotion -> BankAccount.builder().promotion(promotion).build())
                        .collect(Collectors.toList())
        );
        saved.forEach(promotion -> promotionMap.put(promotion.getPromotionID(), promotion));
        return saved;
    }

    public List<Promotion> updatePromotions(List<Promotion> promotions) {
        List<Promotion> saved = getDatabase().insertList(promotions);
        saved.forEach(promotion -> promotionMap.put(promotion.getPromotionID(), promotion));
        return saved;
    }

}
