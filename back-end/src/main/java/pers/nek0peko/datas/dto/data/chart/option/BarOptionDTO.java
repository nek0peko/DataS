package pers.nek0peko.datas.dto.data.chart.option;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

/**
 * BarOptionDTO
 *
 * @author nek0peko
 * @date 2023/05/07
 */
@ApiModel
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class BarOptionDTO extends ChartOptionDTO {

    @JsonProperty("xAxis")
    private AxisX axisX;

    @JsonProperty("yAxis")
    private AxisY axisY;

    private List<Series> series;

    private Legend legend;

    private Tooltip tooltip;

    @ApiModel
    @Builder
    @Data
    public static class AxisX {

        private List<String> data;

    }

    @ApiModel
    @Builder
    @Data
    public static class AxisY {
    }

    @ApiModel
    @Builder
    @Data
    public static class Series {

        private String type;

        private String name;

        private List<Float> data;

    }

    @ApiModel
    @Builder
    @Data
    public static class Legend {

        private String left;

    }

    @ApiModel
    @Builder
    @Data
    public static class Tooltip {

        private String trigger;

        private AxisPointer axisPointer;

        @ApiModel
        @Builder
        @Data
        public static class AxisPointer {

            private String type;

        }

    }

}
