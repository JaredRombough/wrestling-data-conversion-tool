package openwrestling.mapper;

import openwrestling.entities.EventTemplateEntity;
import openwrestling.model.gameObjects.EventTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventTemplateMapper {

    EventTemplateMapper INSTANCE = Mappers.getMapper( EventTemplateMapper.class );

    EventTemplateEntity toEntity(EventTemplate eventTemplate);

    EventTemplate toClass(EventTemplateEntity eventTemplateEntity);
}
