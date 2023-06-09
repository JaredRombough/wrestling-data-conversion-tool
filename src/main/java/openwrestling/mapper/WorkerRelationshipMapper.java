package openwrestling.mapper;

import openwrestling.entities.WorkerRelationshipEntity;
import openwrestling.model.gameObjects.WorkerRelationship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkerRelationshipMapper {

    WorkerRelationshipMapper INSTANCE = Mappers.getMapper( WorkerRelationshipMapper.class );

    WorkerRelationshipEntity toEntity(WorkerRelationship workerRelationship);

    WorkerRelationship toClass(WorkerRelationshipEntity workerRelationshipEntity);
}
