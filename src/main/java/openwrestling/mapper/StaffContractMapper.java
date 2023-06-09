package openwrestling.mapper;

import openwrestling.entities.StaffContractEntity;
import openwrestling.model.gameObjects.StaffContract;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StaffContractMapper {

    StaffContractMapper INSTANCE = Mappers.getMapper( StaffContractMapper.class );

    StaffContractEntity toEntity(StaffContract staffContract);

    StaffContract toClass(StaffContractEntity staffContract);
}
