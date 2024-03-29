package pers.nek0peko.datas.service.domain.impl.chart;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pers.nek0peko.datas.common.constant.Constants;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.chart.ChartTypeEnum;
import pers.nek0peko.datas.dto.data.chart.config.PieConfigDTO;
import pers.nek0peko.datas.dto.data.chart.option.PieOptionDTO;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * PieChartDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/05/07
 */
@Service("pie")
public class PieChartDomainServiceImpl implements ChartDomainServiceI<PieConfigDTO> {

    @Override
    public PieOptionDTO loadDataToOption(String dsType, JSONObject dsConfig, String tableName, JSONObject configJson) {
        final PieConfigDTO config = configJson.toJavaObject(PieConfigDTO.class);
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

        return PieOptionDTO.builder()
                .series(Collections.singletonList(
                        PieOptionDTO.Series.builder()
                                .type(ChartTypeEnum.PIE.getType())
                                .data(data)
                                .itemStyle(PieOptionDTO.Series.ItemStyle.builder()
                                        .borderRadius(8).borderWidth(2).borderColor("#fff").build())
                                .build()))
                .legend(config.getNeedLegend() ? PieOptionDTO.Legend.builder()
                        .orient("vertical").left("left").build() : null)
                .tooltip(PieOptionDTO.Tooltip.builder().trigger("item").build())
                .build();
    }

}
