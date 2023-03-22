package pers.nek0peko.datas.domain.convertor;

import org.mapstruct.Mapper;
import pers.nek0peko.datas.domain.object.ChartDO;
import pers.nek0peko.datas.dto.data.chart.ChartDTO;

/**
 * ChartConvertor
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Mapper(componentModel = "spring")
public interface ChartConvertor extends Convertor<ChartDTO, ChartDO> {
}
