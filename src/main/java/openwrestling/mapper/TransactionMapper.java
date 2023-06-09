package openwrestling.mapper;

import openwrestling.entities.TransactionEntity;
import openwrestling.model.gameObjects.financial.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper( TransactionMapper.class );

    TransactionEntity toEntity(Transaction transaction);

    Transaction toClass(TransactionEntity transactionEntity);
}
