package openwrestling.mapper;

import openwrestling.entities.MonthlyReviewEntity;
import openwrestling.model.gameObjects.MonthlyReview;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MonthlyReviewMapper {

    MonthlyReviewMapper INSTANCE = Mappers.getMapper( MonthlyReviewMapper.class );

    MonthlyReviewEntity toEntity(MonthlyReview  monthlyReview);

    MonthlyReview toClass(MonthlyReviewEntity monthlyReviewEntity);
}
