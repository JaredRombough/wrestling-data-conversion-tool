package openwrestling.mapper;

import openwrestling.entities.PromotionEntity;
import openwrestling.model.gameObjects.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PromotionMapper {

    PromotionMapper INSTANCE = Mappers.getMapper( PromotionMapper.class );

    PromotionEntity toEntity(Promotion promotion);

    Promotion toClass(PromotionEntity promotionEntity);
}
