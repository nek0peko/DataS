package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.data.chart.ChartTypeEnum;
import pers.nek0peko.datas.dto.response.SingleResponse;

import java.util.List;
import java.util.Map;

/**
 * 查询图表类型列表
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Component
public class ChartListTypeQryExe {

    public SingleResponse<List<Map<String, String>>> execute() {
        return SingleResponse.of(ChartTypeEnum.listType());
    }

}
