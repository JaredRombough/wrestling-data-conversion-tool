package openwrestling.mapper;

import openwrestling.entities.InjuryEntity;
import openwrestling.model.gameObjects.Injury;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InjuryMapper {

    InjuryMapper INSTANCE = Mappers.getMapper( InjuryMapper.class );

    InjuryEntity toEntity(Injury injury);

    Injury toClass(InjuryEntity injuryEntity);
}
