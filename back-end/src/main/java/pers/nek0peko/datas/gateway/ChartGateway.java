package pers.nek0peko.datas.gateway;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.data.chart.ChartDTO;

import java.util.List;

/**
 * ChartGateway
 *
 * @author nek0peko
 * @date 2023/04/04
 */
@Component
public interface ChartGateway {

    /**
     * 保存图表
     *
     * @param chart 图表DTO
     */
    void save(ChartDTO chart);

    /**
     * 删除图表
     *
     * @param id 图表id
     */
    void removeById(Long id);

    /**
     * 根据ID查询图表
     *
     * @param id 图表ID
     * @return 图表DTO
     */
    ChartDTO listById(Long id);

    /**
     * 根据图表类型查询
     *
     * @param types 图表类型
     * @return 图表DTO
     */
    List<ChartDTO> list(List<String> types);

}
