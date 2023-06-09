package openwrestling.mapper;

import openwrestling.entities.EventEntity;
import openwrestling.model.gameObjects.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper( EventMapper.class );

    EventEntity toEntity(Event event);

    Event toClass(EventEntity eventEntity);
}
