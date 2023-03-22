package pers.nek0peko.datas.service.domain.impl.chart;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.chart.ChartTypeEnum;
import pers.nek0peko.datas.dto.data.chart.config.BarConfigDTO;
import pers.nek0peko.datas.dto.data.chart.option.BarOptionDTO;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.gateway.DatasourceGateway;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * BarChartDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Service("bar")
public class BarChartDomainServiceImpl implements ChartDomainServiceI<BarConfigDTO> {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    @Override
    public BarOptionDTO loadDataToOption(Long datasourceId, String table, JSONObject configJson) {
        final BarConfigDTO config = configJson.toJavaObject(BarConfigDTO.class);
        final DatasourceDTO datasource = datasourceGateway.getById(datasourceId);
        if (Objects.isNull(datasource)) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_NOT_EXISTS);
        }

        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(datasource.getType());

        final List<BarOptionDTO.Series> series = new ArrayList<>();
        for (final String column : config.getColumns()) {
            final DatasourceResultHolder columnResultHolder = service.queryColumn(datasource.getConfig(), String.format(
                    "SELECT SUM(%s) FROM %s GROUP BY %s ORDER BY %s;", column, table, config.getAxisX(), config.getAxisX()));
            if (columnResultHolder.isSuccess()) {
                try {
                    series.add(BarOptionDTO.Series.builder()
                            .data(((List<String>) columnResultHolder.getData()).stream()
                                    .map(Integer::parseInt)
                                    .collect(Collectors.toList()))
                            .type(ChartTypeEnum.BAR.getType())
                            .build());
                } catch (Exception e) {
                    throw new BusinessException(BusinessErrorEnum.B_CHART_COLUMN_TYPE_ERROR);
                }
            } else {
                throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
            }
        }

        final DatasourceResultHolder xAxisResultHolder = service.queryColumn(datasource.getConfig(), String.format(
                "SELECT %s FROM %s GROUP BY %s ORDER BY %s;", config.getAxisX(), table, config.getAxisX(), config.getAxisX()));

        if (xAxisResultHolder.isSuccess()) {
            return BarOptionDTO.builder()
                    .axisX(BarOptionDTO.AxisX.builder()
                            .data((List<String>) xAxisResultHolder.getData())
                            .build())
                    .axisY(BarOptionDTO.AxisY.builder().build())
                    .series(series)
                    .build();
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }
    }

}
