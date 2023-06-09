package openwrestling.mapper;

import openwrestling.entities.RosterSplitEntity;
import openwrestling.model.gameObjects.RosterSplit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RosterSplitMapper {

    RosterSplitMapper INSTANCE = Mappers.getMapper( RosterSplitMapper.class );

    RosterSplitEntity toEntity(RosterSplit rosterSplit);

    RosterSplit toClass(RosterSplitEntity rosterSplitEntity);
}
