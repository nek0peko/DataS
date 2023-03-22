package pers.nek0peko.datas.domain.convertor;

import org.mapstruct.Mapper;
import pers.nek0peko.datas.domain.object.ChartDO;
import pers.nek0peko.datas.dto.command.ChartCreateCmd;
import pers.nek0peko.datas.dto.data.chart.ChartDTO;

/**
 * ChartConvertor
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Mapper(componentModel = "spring")
public interface ChartConvertor extends Convertor<ChartDTO, ChartDO> {

    /**
     * 图表创建命令转图表DTO
     *
     * @param cmd 图表创建命令
     * @return 图表DTO
     */
    ChartDTO toDTO(ChartCreateCmd cmd);

}
