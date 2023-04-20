package pers.nek0peko.datas.dto.data.chart.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * BarConfigDTO
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BarConfigDTO extends ChartConfigDTO {

    @ApiModelProperty(value = "横轴的数据列名", position = 1, required = true)
    private String axisX;

    @ApiModelProperty(value = "数据列名", position = 2, required = true)
    private List<String> columns;

    @ApiModelProperty(value = "是否需要图例", position = 3, required = true)
    private Boolean needLegend;

}
