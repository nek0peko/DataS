package pers.nek0peko.datas.dto.data.chart.option;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

/**
 * LineOptionDTO
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@ApiModel
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class LineOptionDTO extends ChartOptionDTO {

    @JsonProperty("xAxis")
    private AxisX axisX;

    @JsonProperty("yAxis")
    private AxisY axisY;

    private List<Series> series;

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

        private List<Integer> data;

    }

}
