package pers.nek0peko.datas.dto.data.chart;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;
import pers.nek0peko.datas.dto.BaseDTO;
import pers.nek0peko.datas.dto.data.chart.config.ChartConfigDTO;
import pers.nek0peko.datas.dto.data.chart.option.ChartOptionDTO;

import java.time.LocalDateTime;

/**
 * ChartViewDTO
 *
 * @author nek0peko
 * @date 2023/04/20
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
     * 图表类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 数据源ID
     */
    private Long datasourceId;

    /**
     * 数据源名
     */
    private String datasourceName;

    /**
     * 数据源类型
     */
    private String datasourceType;

    /**
     * 数据表名
     */
    private String tableName;

    /**
     * 视图详情
     */
    private ChartOptionDTO option;

    /**
     * 视图配置
     */
    private JSONObject config;

    /**
     * 创建者
     */
    private String creator;

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
