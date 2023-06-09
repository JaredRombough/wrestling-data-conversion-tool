package openwrestling.mapper;

import openwrestling.entities.TitleEntity;
import openwrestling.model.gameObjects.Title;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TitleMapper {

    TitleMapper INSTANCE = Mappers.getMapper( TitleMapper.class );

    TitleEntity toEntity(Title title, @Context CycleAvoidingMappingContext context);

    Title toClass(TitleEntity titleEntity, @Context CycleAvoidingMappingContext context);
}
