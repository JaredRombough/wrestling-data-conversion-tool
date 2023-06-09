package openwrestling.mapper;

import openwrestling.entities.BroadcastTeamMemberEntity;
import openwrestling.model.gameObjects.BroadcastTeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BroadcastTeamMemberMapper {

    BroadcastTeamMemberMapper INSTANCE = Mappers.getMapper( BroadcastTeamMemberMapper.class );

    BroadcastTeamMemberEntity toEntity(BroadcastTeamMember broadcastTeamMember);

    BroadcastTeamMember toClass(BroadcastTeamMemberEntity broadcastTeamMemberEntity);
}
