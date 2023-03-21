package pers.nek0peko.datas.dto.data.chart;

import io.swagger.annotations.ApiModel;
import lombok.*;
import pers.nek0peko.datas.dto.BaseDTO;
import pers.nek0peko.datas.dto.data.chart.option.ChartOptionDTO;

/**
 * ChartViewDTO
 *
 * @author nek0peko
 * @date 2023/03/21
 */
@ApiModel
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ChartViewDTO extends BaseDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 视图名
     */
    private String name;

    /**
     * 视图详情
     */
    private ChartOptionDTO option;

}
