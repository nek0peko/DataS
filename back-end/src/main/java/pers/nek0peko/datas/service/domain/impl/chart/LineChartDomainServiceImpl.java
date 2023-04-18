package pers.nek0peko.datas.service.domain.impl.chart;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.chart.ChartTypeEnum;
import pers.nek0peko.datas.dto.data.chart.config.LineConfigDTO;
import pers.nek0peko.datas.dto.data.chart.option.LineOptionDTO;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.gateway.DatasourceGateway;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * LineChartDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/04/07
 */
@Service("line")
public class LineChartDomainServiceImpl implements ChartDomainServiceI<LineConfigDTO> {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    @Override
    public LineOptionDTO loadDataToOption(Long datasourceId, String tableName, JSONObject configJson) {
        final LineConfigDTO config = configJson.toJavaObject(LineConfigDTO.class);
        if (CollectionUtils.isEmpty(config.getColumns()) || StringUtils.isEmpty(config.getAxisX())) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_INVALID_CONFIG);
        }

        final DatasourceDTO datasource = datasourceGateway.getById(datasourceId);
        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(datasource.getType());

        final CompletableFuture<List<LineOptionDTO.Series>> seriesFuture = CompletableFuture.supplyAsync(() ->
                config.getColumns().parallelStream().map(column -> {
                            final DatasourceResultHolder columnResultHolder = service.queryColumnSumGroupBy(
                                    datasource.getConfig(), tableName, column, config.getAxisX());
                            if (columnResultHolder.isSuccess()) {
                                try {
                                    return LineOptionDTO.Series.builder()
                                            .data(((List<String>) columnResultHolder.getData()).stream()
                                                    .map(Integer::parseInt)
                                                    .collect(Collectors.toList()))
                                            .type(ChartTypeEnum.LINE.getType())
                                            .build();
                                } catch (NumberFormatException e) {
                                    throw new BusinessException(BusinessErrorEnum.B_CHART_COLUMN_TYPE_ERROR);
                                }
                            } else {
                                throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
                            }
                        })
                        .collect(Collectors.toList()));

        final CompletableFuture<LineOptionDTO.AxisX> xAxisFuture = CompletableFuture.supplyAsync(() -> {
            final DatasourceResultHolder xAxisResultHolder = service.queryColumnGroupBy(
                    datasource.getConfig(), tableName, config.getAxisX(), config.getAxisX());
            if (xAxisResultHolder.isSuccess()) {
                return LineOptionDTO.AxisX.builder().data((List<String>) xAxisResultHolder.getData()).build();
            } else {
                throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
            }
        });

        CompletableFuture.allOf(seriesFuture, xAxisFuture).join();

        return LineOptionDTO.builder()
                .series(seriesFuture.join())
                .axisX(xAxisFuture.join())
                .axisY(LineOptionDTO.AxisY.builder().build())
                .build();
    }

}
