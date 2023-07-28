package openwrestling.model.interfaces;

import openwrestling.model.gameObjects.Promotion;
import openwrestling.model.segment.constants.Gender;

import java.util.List;

public interface iPerson {

    iContract getContract();

    iContract getContract(Promotion promotion);

    List<? extends iContract> getContracts();

    Gender getGender();

    String getName();

    void setName(String name);

    void setShortName(String name);

}
