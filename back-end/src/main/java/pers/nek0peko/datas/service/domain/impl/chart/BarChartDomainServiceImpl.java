package pers.nek0peko.datas.service.domain.impl.chart;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.chart.ChartTypeEnum;
import pers.nek0peko.datas.dto.data.chart.config.BarConfigDTO;
import pers.nek0peko.datas.dto.data.chart.option.BarOptionDTO;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * BarChartDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/05/07
 */
@Service("bar")
public class BarChartDomainServiceImpl implements ChartDomainServiceI<BarConfigDTO> {

    @Override
    public BarOptionDTO loadDataToOption(String dsType, JSONObject dsConfig, String tableName, JSONObject configJson) {
        final BarConfigDTO config = configJson.toJavaObject(BarConfigDTO.class);
        if (CollectionUtils.isEmpty(config.getColumns()) || StringUtils.isEmpty(config.getAxisX())) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_INVALID_CONFIG);
        }

        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(dsType);

        final CompletableFuture<List<BarOptionDTO.Series>> seriesFuture = CompletableFuture.supplyAsync(() ->
                config.getColumns().parallelStream().map(column -> {
                            final DatasourceResultHolder columnResultHolder = service.queryColumnSumGroupBy(
                                    dsConfig, tableName, column, config.getAxisX());
                            if (columnResultHolder.isSuccess()) {
                                try {
                                    return BarOptionDTO.Series.builder()
                                            .data(((List<String>) columnResultHolder.getData()).stream()
                                                    .map(Float::parseFloat)
                                                    .collect(Collectors.toList()))
                                            .name(column)
                                            .type(ChartTypeEnum.BAR.getType())
                                            .build();
                                } catch (NumberFormatException e) {
                                    throw new BusinessException(BusinessErrorEnum.B_CHART_COLUMN_TYPE_ERROR);
                                }
                            } else {
                                throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
                            }
                        })
                        .collect(Collectors.toList()));

        final CompletableFuture<BarOptionDTO.AxisX> xAxisFuture = CompletableFuture.supplyAsync(() -> {
            final DatasourceResultHolder xAxisResultHolder = service.queryColumnGroupBy(
                    dsConfig, tableName, config.getAxisX(), config.getAxisX());
            if (xAxisResultHolder.isSuccess()) {
                return BarOptionDTO.AxisX.builder().data((List<String>) xAxisResultHolder.getData()).build();
            } else {
                throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
            }
        });

        try {
            CompletableFuture.allOf(seriesFuture, xAxisFuture).join();
        } catch (CompletionException e) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }

        return BarOptionDTO.builder()
                .series(seriesFuture.join())
                .axisX(xAxisFuture.join())
                .axisY(BarOptionDTO.AxisY.builder().build())
                .legend(config.getNeedLegend() ? BarOptionDTO.Legend.builder().left("5%").build() : null)
                .tooltip(BarOptionDTO.Tooltip.builder()
                        .trigger("axis")
                        .axisPointer(BarOptionDTO.Tooltip.AxisPointer.builder().type("shadow").build())
                        .build())
                .build();
    }

}
