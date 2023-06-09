package openwrestling.mapper;

import openwrestling.entities.TitleReignEntity;
import openwrestling.model.gameObjects.TitleReign;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TitleReignMapper {

    TitleReignMapper INSTANCE = Mappers.getMapper( TitleReignMapper.class );

    TitleReignEntity toEntity(TitleReign titleReign);

    TitleReign toClass(TitleReignEntity titleReignEntity);
}
