package openwrestling.mapper;

import openwrestling.entities.StaffMemberEntity;
import openwrestling.model.gameObjects.StaffMember;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StaffMemberMapper {

    StaffMemberMapper INSTANCE = Mappers.getMapper( StaffMemberMapper.class );

    StaffMemberEntity toEntity(StaffMember staffMember);

    StaffMember toClass(StaffMemberEntity staffMemberEntity);
}
