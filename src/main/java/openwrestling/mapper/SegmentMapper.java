package openwrestling.mapper;

import openwrestling.entities.SegmentEntity;
import openwrestling.model.gameObjects.Segment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SegmentMapper {

    SegmentMapper INSTANCE = Mappers.getMapper( SegmentMapper.class );

    SegmentEntity toEntity(Segment segment);

    Segment toClass(SegmentEntity segmentEntity);
}
