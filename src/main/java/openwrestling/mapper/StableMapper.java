package openwrestling.mapper;

import openwrestling.entities.StableEntity;
import openwrestling.model.gameObjects.Stable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StableMapper {

    StableMapper INSTANCE = Mappers.getMapper( StableMapper.class );

    StableEntity toEntity(Stable stable);

    Stable toClass(StableEntity stableEntity);
}
