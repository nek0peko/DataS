package pers.nek0peko.datas.dto.data.chart.option;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * FunnelOptionDTO
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@ApiModel
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class FunnelOptionDTO extends ChartOptionDTO {

    private List<Series> series;

    private Legend legend;

    private Tooltip tooltip;

    @ApiModel
    @Builder
    @Data
    public static class Series {

        private String type;

        private List<Map<String, Object>> data;

    }

    @ApiModel
    @Builder
    @Data
    public static class Legend {

        private String orient;

        private String left;

        private String top;

    }

    @ApiModel
    @Builder
    @Data
    public static class Tooltip {

        private String trigger;

    }

}
