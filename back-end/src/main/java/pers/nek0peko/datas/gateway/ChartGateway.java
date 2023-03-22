package pers.nek0peko.datas.gateway;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.data.chart.ChartDTO;

import java.util.List;

/**
 * ChartGateway
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Component
public interface ChartGateway {

    /**
     * 根据图表类型查询
     *
     * @param types 图表类型
     * @return 图表DTO
     */
    List<ChartDTO> list(List<String> types);

}
