package openwrestling.mapper;

import openwrestling.entities.ContractEntity;
import openwrestling.model.gameObjects.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContractMapper {

    ContractMapper INSTANCE = Mappers.getMapper( ContractMapper.class );

    ContractEntity toEntity(Contract contract);

    Contract toClass(ContractEntity contractEntity);
}
