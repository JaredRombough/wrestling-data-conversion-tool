package openwrestling.mapper;

import openwrestling.entities.MoraleRelationshipEntity;
import openwrestling.model.gameObjects.MoraleRelationship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MoraleRelationshipMapper {

    MoraleRelationshipMapper INSTANCE = Mappers.getMapper( MoraleRelationshipMapper.class );

    MoraleRelationshipEntity toEntity(MoraleRelationship moraleRelationship);

    MoraleRelationship toClass(MoraleRelationshipEntity moraleRelationshipEntity);
}
