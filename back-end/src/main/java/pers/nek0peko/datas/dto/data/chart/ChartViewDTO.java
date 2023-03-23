package pers.nek0peko.datas.dto.data.chart;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;
import pers.nek0peko.datas.dto.BaseDTO;
import pers.nek0peko.datas.dto.data.chart.option.ChartOptionDTO;

import java.time.LocalDateTime;

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

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

}
