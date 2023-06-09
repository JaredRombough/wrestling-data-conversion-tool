package openwrestling.mapper;

import openwrestling.entities.TagTeamEntity;
import openwrestling.model.gameObjects.TagTeam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagTeamMapper {

    TagTeamMapper INSTANCE = Mappers.getMapper( TagTeamMapper.class );

    TagTeamEntity toEntity(TagTeam tagTeam);

    TagTeam toClass(TagTeamEntity tagTeamEntity);
}
