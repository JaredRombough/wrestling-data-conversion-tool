package openwrestling.model.interfaces;

import openwrestling.model.segment.constants.TeamType;

public interface iAngleType {

    int minWorkers();

    int defaultWorkers();

    TeamType addTeamType();

    TeamType mainTeamType();

    String resultString();

}
