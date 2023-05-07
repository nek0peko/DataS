package pers.nek0peko.datas.service.domain.impl.chart;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pers.nek0peko.datas.common.constant.Constants;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.chart.ChartTypeEnum;
import pers.nek0peko.datas.dto.data.chart.config.FunnelConfigDTO;
import pers.nek0peko.datas.dto.data.chart.option.FunnelOptionDTO;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * FunnelChartDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/05/07
 */
@Service("funnel")
public class FunnelChartDomainServiceImpl implements ChartDomainServiceI<FunnelConfigDTO> {

    @Override
    public FunnelOptionDTO loadDataToOption(String dsType, JSONObject dsConfig, String tableName, JSONObject configJson) {
        final FunnelConfigDTO config = configJson.toJavaObject(FunnelConfigDTO.class);
        if (StringUtils.isEmpty(config.getTypeColumn()) || StringUtils.isEmpty(config.getValueColumn())) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_INVALID_CONFIG);
        }

        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(dsType);

        final CompletableFuture<List<String>> typesFuture = CompletableFuture.supplyAsync(() -> {
            final DatasourceResultHolder typesResultHolder = service.queryColumnGroupBy(
                    dsConfig, tableName, config.getTypeColumn(), config.getTypeColumn());
            if (typesResultHolder.isSuccess()) {
                return (List<String>) typesResultHolder.getData();
            } else {
                throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
            }
        });

        final CompletableFuture<List<Float>> valuesFuture = CompletableFuture.supplyAsync(() -> {
            final DatasourceResultHolder valuesResultHolder = service.queryColumnSumGroupBy(
                    dsConfig, tableName, config.getValueColumn(), config.getTypeColumn());
            if (valuesResultHolder.isSuccess()) {
                return (List<Float>) valuesResultHolder.getData();
            } else {
                throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
            }
        });

        try {
            CompletableFuture.allOf(typesFuture, valuesFuture).join();
        } catch (CompletionException e) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }

        final List<Map<String, Object>> data = new ArrayList<>();
        final Iterator<String> typesIterator = typesFuture.join().iterator();
        final Iterator<Float> valuesIterator = valuesFuture.join().iterator();

        while (typesIterator.hasNext() && valuesIterator.hasNext()) {
            final Map<String, Object> map = new HashMap<>(Constants.TWO);
            map.put("value", valuesIterator.next());
            map.put("name", typesIterator.next());
            data.add(map);
        }

        return FunnelOptionDTO.builder()
                .series(Collections.singletonList(
                        FunnelOptionDTO.Series.builder()
                                .type(ChartTypeEnum.FUNNEL.getType())
                                .data(data)
                                .build()))
                .legend(config.getNeedLegend() ? FunnelOptionDTO.Legend.builder()
                        .orient("vertical").left("left").top("bottom").build() : null)
                .tooltip(FunnelOptionDTO.Tooltip.builder().trigger("item").build())
                .build();
    }

}
