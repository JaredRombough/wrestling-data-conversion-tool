package openwrestling.mapper;

import openwrestling.entities.WorkerEntity;
import openwrestling.model.gameObjects.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkerMapper {

    WorkerMapper INSTANCE = Mappers.getMapper( WorkerMapper.class );

    WorkerEntity toEntity(Worker worker);

    Worker toClass(WorkerEntity workerEntity);
}
