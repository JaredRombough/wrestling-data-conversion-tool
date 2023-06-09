package openwrestling.mapper;

import openwrestling.entities.EntourageMemberEntity;
import openwrestling.model.gameObjects.EntourageMember;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntourageMemberMapper {

    EntourageMemberMapper INSTANCE = Mappers.getMapper( EntourageMemberMapper.class );

    EntourageMemberEntity toEntity(EntourageMember entourageMember);

    EntourageMember toClass(EntourageMemberEntity entourageMemberEntity);
}
