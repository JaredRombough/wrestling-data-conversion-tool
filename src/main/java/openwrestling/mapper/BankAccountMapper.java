package openwrestling.mapper;

import openwrestling.entities.BankAccountEntity;
import openwrestling.model.gameObjects.financial.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BankAccountMapper {

    BankAccountMapper INSTANCE = Mappers.getMapper( BankAccountMapper.class );

    BankAccountEntity toEntity(BankAccount bankAccount);

    BankAccount toClass(BankAccountEntity bankAccountEntity);
}
