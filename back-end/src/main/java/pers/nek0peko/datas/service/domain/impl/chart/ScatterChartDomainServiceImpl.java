package pers.nek0peko.datas.service.domain.impl.chart;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.chart.ChartTypeEnum;
import pers.nek0peko.datas.dto.data.chart.config.ScatterConfigDTO;
import pers.nek0peko.datas.dto.data.chart.option.ScatterOptionDTO;
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
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * ScatterChartDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@Service("scatter")
public class ScatterChartDomainServiceImpl implements ChartDomainServiceI<ScatterConfigDTO> {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    @Override
    public ScatterOptionDTO loadDataToOption(Long datasourceId, String tableName, JSONObject configJson) {
        final ScatterConfigDTO config = configJson.toJavaObject(ScatterConfigDTO.class);
        if (CollectionUtils.isEmpty(config.getColumns()) || StringUtils.isEmpty(config.getAxisX())) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_INVALID_CONFIG);
        }

        final DatasourceDTO datasource = datasourceGateway.getById(datasourceId);
        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(datasource.getType());

        final CompletableFuture<List<ScatterOptionDTO.Series>> seriesFuture = CompletableFuture.supplyAsync(() ->
                config.getColumns().parallelStream().map(column -> {
                            final DatasourceResultHolder columnResultHolder = service.queryColumnSumGroupBy(
                                    datasource.getConfig(), tableName, column, config.getAxisX());
                            if (columnResultHolder.isSuccess()) {
                                try {
                                    return ScatterOptionDTO.Series.builder()
                                            .data(((List<String>) columnResultHolder.getData()).stream()
                                                    .map(Integer::parseInt)
                                                    .collect(Collectors.toList()))
                                            .type(ChartTypeEnum.SCATTER.getType())
                                            .build();
                                } catch (NumberFormatException e) {
                                    throw new BusinessException(BusinessErrorEnum.B_CHART_COLUMN_TYPE_ERROR);
                                }
                            } else {
                                throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
                            }
                        })
                        .collect(Collectors.toList()));

        final CompletableFuture<ScatterOptionDTO.AxisX> xAxisFuture = CompletableFuture.supplyAsync(() -> {
            final DatasourceResultHolder xAxisResultHolder = service.queryColumnGroupBy(
                    datasource.getConfig(), tableName, config.getAxisX(), config.getAxisX());
            if (xAxisResultHolder.isSuccess()) {
                return ScatterOptionDTO.AxisX.builder().data((List<String>) xAxisResultHolder.getData()).build();
            } else {
                throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
            }
        });

        try {
            CompletableFuture.allOf(seriesFuture, xAxisFuture).join();
        } catch (CompletionException e) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }

        return ScatterOptionDTO.builder()
                .series(seriesFuture.join())
                .axisX(xAxisFuture.join())
                .axisY(ScatterOptionDTO.AxisY.builder().build())
                .build();
    }

}
