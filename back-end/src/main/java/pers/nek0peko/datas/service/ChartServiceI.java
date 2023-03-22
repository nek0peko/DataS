package pers.nek0peko.datas.service;

import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;

import java.util.List;
import java.util.Map;

/**
 * 图表服务
 *
 * @author nek0peko
 * @date 2023/03/22
 */
public interface ChartServiceI {

    /**
     * 查询图表类型列表
     *
     * @return 图表类型列表
     */
    SingleResponse<List<Map<String, String>>> listType();

    /**
     * 获取图表绘制信息
     *
     * @param types 图表类型
     * @return 图表绘制信息
     */
    SingleResponse<List<ChartViewDTO>> listView(List<String> types);

}
