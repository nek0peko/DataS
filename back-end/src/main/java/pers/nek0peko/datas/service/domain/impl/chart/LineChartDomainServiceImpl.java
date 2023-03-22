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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * LineChartDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Service("line")
public class LineChartDomainServiceImpl implements ChartDomainServiceI<LineConfigDTO> {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    @Override
    public LineOptionDTO loadDataToOption(Long datasourceId, String tableName, JSONObject configJson) {
        final LineConfigDTO config = configJson.toJavaObject(LineConfigDTO.class);
        final DatasourceDTO datasource = datasourceGateway.getById(datasourceId);
        if (Objects.isNull(datasource)) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_NOT_EXISTS);
        }
        if (CollectionUtils.isEmpty(config.getColumns()) || StringUtils.isEmpty(config.getAxisX())) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_INVALID_CONFIG);
        }

        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(datasource.getType());
        final List<LineOptionDTO.Series> series = config.getColumns().parallelStream()
                .map(column -> {
                    final DatasourceResultHolder columnResultHolder = service.queryColumnSumGroupBy(
                            datasource.getConfig(), column, tableName, config.getAxisX());
                    if (columnResultHolder.isSuccess()) {
                        try {
                            return LineOptionDTO.Series.builder()
                                    .data(((List<String>) columnResultHolder.getData()).stream()
                                            .map(Integer::parseInt)
                                            .collect(Collectors.toList()))
                                    .type(ChartTypeEnum.LINE.getType())
                                    .build();
                        } catch (Exception e) {
                            throw new BusinessException(BusinessErrorEnum.B_CHART_COLUMN_TYPE_ERROR);
                        }
                    } else {
                        throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
                    }
                })
                .collect(Collectors.toList());

        final DatasourceResultHolder xAxisResultHolder = service.queryColumnGroupBy(
                datasource.getConfig(), config.getAxisX(), tableName, config.getAxisX());

        if (xAxisResultHolder.isSuccess()) {
            return LineOptionDTO.builder()
                    .axisX(LineOptionDTO.AxisX.builder()
                            .data((List<String>) xAxisResultHolder.getData())
                            .build())
                    .axisY(LineOptionDTO.AxisY.builder().build())
                    .series(series)
                    .build();
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }
    }

}
