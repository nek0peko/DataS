package pers.nek0peko.datas.dto.data.chart.option;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * PieOptionDTO
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@ApiModel
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class PieOptionDTO extends ChartOptionDTO {

    private List<Series> series;

    @ApiModel
    @Builder
    @Data
    public static class Series {

        private String type;

        private List<Map<String, Object>> data;

    }

}
