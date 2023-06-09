package openwrestling.mapper;

import openwrestling.entities.StableMemberEntity;
import openwrestling.model.gameObjects.StableMember;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StableMemberMapper {

    StableMemberMapper INSTANCE = Mappers.getMapper( StableMemberMapper.class );

    StableMemberEntity toEntity(StableMember stableMember);

    StableMember toClass(StableMemberEntity stableMemberEntity);
}
