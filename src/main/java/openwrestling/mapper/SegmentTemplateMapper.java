package openwrestling.mapper;

import openwrestling.entities.SegmentTemplateEntity;
import openwrestling.model.gameObjects.SegmentTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SegmentTemplateMapper {

    SegmentTemplateMapper INSTANCE = Mappers.getMapper( SegmentTemplateMapper.class );

    SegmentTemplateEntity toEntity(SegmentTemplate segmentTemplate);

    SegmentTemplate toClass(SegmentTemplateEntity segmentTemplateEntity);
}
