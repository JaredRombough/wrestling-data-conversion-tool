package openwrestling.mapper;

import openwrestling.entities.GameSettingEntity;
import openwrestling.model.gameObjects.gamesettings.GameSetting;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameSettingMapper  {

    GameSettingMapper INSTANCE = Mappers.getMapper( GameSettingMapper.class );

    GameSettingEntity toEntity(GameSetting gameSetting);

    GameSetting toClass(GameSettingEntity gameSettingEntity);
}
