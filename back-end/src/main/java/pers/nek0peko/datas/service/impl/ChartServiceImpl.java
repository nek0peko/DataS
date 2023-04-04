package pers.nek0peko.datas.service.impl;

import org.springframework.stereotype.Service;
import pers.nek0peko.datas.command.*;
import pers.nek0peko.datas.dto.command.ChartCreateCmd;
import pers.nek0peko.datas.dto.command.ChartPreviewCmd;
import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.service.ChartServiceI;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 图表服务
 *
 * @author nek0peko
 * @date 2023/04/04
 */
@Service
public class ChartServiceImpl implements ChartServiceI {

    @Resource
    private transient ChartListTypeQryExe chartListTypeQryExe;

    @Resource
    private transient ChartListViewQryExe chartListViewQryExe;

    @Resource
    private transient ChartPreviewCmdExe chartPreviewCmdExe;

    @Resource
    private transient ChartCreateCmdExe chartCreateCmdExe;

    @Resource
    private transient ChartRemoveCmdExe chartRemoveCmdExe;

    @Override
    public SingleResponse<List<Map<String, String>>> listType() {
        return chartListTypeQryExe.execute();
    }

    @Override
    public SingleResponse<List<ChartViewDTO>> listView(List<String> types) {
        return chartListViewQryExe.execute(types);
    }

    @Override
    public SingleResponse<ChartViewDTO> preview(ChartPreviewCmd cmd) {
        return chartPreviewCmdExe.execute(cmd);
    }

    @Override
    public Response create(ChartCreateCmd cmd) {
        return chartCreateCmdExe.execute(cmd);
    }

    @Override
    public Response remove(Long id) {
        return chartRemoveCmdExe.execute(id);
    }

}
