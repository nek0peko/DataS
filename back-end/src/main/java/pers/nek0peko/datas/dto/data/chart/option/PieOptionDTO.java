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

    private Legend legend;

    private Tooltip tooltip;

    @ApiModel
    @Builder
    @Data
    public static class Series {

        private String type;

        private List<Map<String, Object>> data;

        private ItemStyle itemStyle;

        @ApiModel
        @Builder
        @Data
        public static class ItemStyle {

            private String borderColor;

            private Integer borderRadius;

            private Integer borderWidth;

        }

    }

    @ApiModel
    @Builder
    @Data
    public static class Legend {

        private String orient;

        private String left;

    }

    @ApiModel
    @Builder
    @Data
    public static class Tooltip {

        private String trigger;

    }

}
