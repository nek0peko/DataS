package pers.nek0peko.datas.service;

import pers.nek0peko.datas.dto.command.ChartCreateCmd;
import pers.nek0peko.datas.dto.command.ChartPreviewCmd;
import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.dto.response.SingleResponse;

import java.util.List;
import java.util.Map;

/**
 * 图表服务
 *
 * @author nek0peko
 * @date 2023/04/04
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

    /**
     * 预览图表
     *
     * @param cmd 预览图表请求
     * @return 图表绘制信息
     */
    SingleResponse<ChartViewDTO> preview(ChartPreviewCmd cmd);

    /**
     * 新增图表
     *
     * @param cmd 新增图表请求
     * @return 是否新增成功
     */
    Response create(ChartCreateCmd cmd);

    /**
     * 删除图表
     *
     * @param id 图表ID
     * @return 是否删除成功
     */
    Response remove(Long id);

}
