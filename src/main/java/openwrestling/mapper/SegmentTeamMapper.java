package openwrestling.mapper;

import openwrestling.entities.SegmentTeamEntity;
import openwrestling.model.gameObjects.SegmentTeam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SegmentTeamMapper {

    SegmentTeamMapper INSTANCE = Mappers.getMapper( SegmentTeamMapper.class );

    SegmentTeamEntity toEntity(SegmentTeam segmentTeam);

    SegmentTeam toClass(SegmentTeamEntity segmentTeamEntity);
}
