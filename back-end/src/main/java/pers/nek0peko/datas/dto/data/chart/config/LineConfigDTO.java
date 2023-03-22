package pers.nek0peko.datas.dto.data.chart.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * LineConfigDTO
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LineConfigDTO extends ChartConfigDTO {

    @ApiModelProperty(value = "横轴的数据列名", position = 1, required = true)
    private String axisX;

    @ApiModelProperty(value = "数据列名", position = 2, required = true)
    private List<String> columns;

}
