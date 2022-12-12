package pers.nek0peko.datas.domain.convertor;

import pers.nek0peko.datas.domain.object.DatasourceDO;
import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;
import pers.nek0peko.datas.dto.data.DatasourceDTO;

import org.mapstruct.Mapper;

/**
 * DatasourceConvertor
 *
 * @author nek0peko
 * @date 2022/12/12
 */
@Mapper(componentModel = "spring")
public interface DatasourceConvertor extends Convertor<DatasourceDTO, DatasourceDO> {

    /**
     * 数据源创建命令转数据源DTO
     *
     * @param cmd 数据源创建命令
     * @return 数据源DTO
     */
    DatasourceDTO toDTO(DatasourceCreateCmd cmd);

}
