package pers.nek0peko.datas.service.impl;

import org.springframework.stereotype.Service;
import pers.nek0peko.datas.command.ChartListTypeQryExe;
import pers.nek0peko.datas.command.ChartListViewQryExe;
import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.service.ChartServiceI;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 图表服务
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Service
public class ChartServiceImpl implements ChartServiceI {

    @Resource
    private transient ChartListTypeQryExe chartListTypeQryExe;

    @Resource
    private transient ChartListViewQryExe chartListViewQryExe;

    @Override
    public SingleResponse<List<Map<String, String>>> listType() {
        return chartListTypeQryExe.execute();
    }

    @Override
    public SingleResponse<List<ChartViewDTO>> listView(List<String> types) {
        return chartListViewQryExe.execute(types);
    }

}
