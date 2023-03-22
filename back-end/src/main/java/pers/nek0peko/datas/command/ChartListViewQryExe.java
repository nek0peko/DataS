package pers.nek0peko.datas.command;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.data.chart.ChartDTO;
import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.data.chart.config.BarConfigDTO;
import pers.nek0peko.datas.dto.data.chart.option.ChartOptionDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.factory.ChartDomainServiceFactory;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 获取图表绘制信息
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Component
public class ChartListViewQryExe {

    public SingleResponse<List<ChartViewDTO>> execute() {
        final BarConfigDTO barConfigDTO = new BarConfigDTO();
        barConfigDTO.setAxisX("week");
        barConfigDTO.setColumns(Arrays.asList("num", "num2"));
        final ChartDTO chartDTO = new ChartDTO();
        chartDTO.setId(Long.parseLong("11111111111"));
        chartDTO.setName("TestTestTest");
        chartDTO.setType("bar");
        chartDTO.setDatasourceId(Long.parseLong("1603321370415116289"));
        chartDTO.setTable("test");
        chartDTO.setConfig(JSONObject.parseObject(JSONObject.toJSONString(barConfigDTO)));

        final ChartDomainServiceI service = ChartDomainServiceFactory.getService(chartDTO.getType());
        final ChartOptionDTO chartOptionDTO =
                service.loadDataToOption(chartDTO.getDatasourceId(), chartDTO.getTable(), service.validateAndFilterConfig(chartDTO.getConfig()));

        final List<ChartViewDTO> chartViewDtos = new ArrayList<>();
        chartViewDtos.add(ChartViewDTO.builder()
                .id(chartDTO.getId())
                .name(chartDTO.getName())
                .option(chartOptionDTO)
                .build());
        chartViewDtos.add(ChartViewDTO.builder()
                .id(chartDTO.getId())
                .name(chartDTO.getName())
                .option(chartOptionDTO)
                .build());
        return SingleResponse.of(chartViewDtos);
    }

}
