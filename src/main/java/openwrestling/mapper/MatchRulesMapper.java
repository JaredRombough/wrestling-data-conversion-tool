package openwrestling.mapper;

import openwrestling.entities.MatchRulesEntity;
import openwrestling.entities.MonthlyReviewEntity;
import openwrestling.model.gameObjects.MonthlyReview;
import openwrestling.model.segment.opitons.MatchRules;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MatchRulesMapper {

    MatchRulesMapper INSTANCE = Mappers.getMapper( MatchRulesMapper.class );

    MatchRulesEntity toEntity(MatchRules matchRules);

    MatchRules toClass(MatchRulesEntity matchRulesEntity);
}
