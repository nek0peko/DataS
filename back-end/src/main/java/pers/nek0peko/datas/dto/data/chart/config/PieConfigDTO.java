package pers.nek0peko.datas.dto.data.chart.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * PieConfigDTO
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PieConfigDTO extends ChartConfigDTO {

    @ApiModelProperty(value = "数据列名", position = 1, required = true)
    private String valueColumn;

    @ApiModelProperty(value = "类别列名", position = 2, required = true)
    private String typeColumn;

}
