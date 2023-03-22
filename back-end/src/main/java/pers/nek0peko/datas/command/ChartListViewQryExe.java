package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.chart.ChartDTO;
import pers.nek0peko.datas.dto.data.chart.ChartTypeEnum;
import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.data.chart.option.ChartOptionDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.ChartDomainServiceFactory;
import pers.nek0peko.datas.gateway.ChartGateway;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取图表绘制信息
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Component
public class ChartListViewQryExe {

    @Resource
    private transient ChartGateway gateway;

    public SingleResponse<List<ChartViewDTO>> execute(List<String> types) {
        if (CollectionUtils.isEmpty(types)) {
            return SingleResponse.of(Collections.emptyList());
        }
        if (!ChartTypeEnum.isSupportedType(types)) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_UNSUPPORTED);
        }

        final List<ChartDTO> chartDtos = gateway.list(types);
        if (CollectionUtils.isEmpty(chartDtos)) {
            return SingleResponse.of(Collections.emptyList());
        }

        final List<ChartViewDTO> chartViewDtos = chartDtos.parallelStream()
                .map(chartDTO -> {
                    final ChartDomainServiceI service = ChartDomainServiceFactory.getService(chartDTO.getType());
                    final ChartOptionDTO chartOptionDTO = service.loadDataToOption(
                            chartDTO.getDatasourceId(),
                            chartDTO.getTableName(),
                            service.validateAndFilterConfig(chartDTO.getConfig()));
                    return ChartViewDTO.builder()
                            .id(chartDTO.getId())
                            .name(chartDTO.getName())
                            .option(chartOptionDTO)
                            .build();
                })
                .collect(Collectors.toList());
        return SingleResponse.of(chartViewDtos);
    }

}
