package openwrestling.mapper;

import openwrestling.entities.RosterSplitWorkerEntity;
import openwrestling.model.gameObjects.RosterSplitWorker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RosterSplitWorkerMapper {

    RosterSplitWorkerMapper INSTANCE = Mappers.getMapper( RosterSplitWorkerMapper.class );

    RosterSplitWorkerEntity toEntity(RosterSplitWorker rosterSplitWorker);

    RosterSplitWorker toClass(RosterSplitWorkerEntity rosterSplitWorkerEntity);
}
