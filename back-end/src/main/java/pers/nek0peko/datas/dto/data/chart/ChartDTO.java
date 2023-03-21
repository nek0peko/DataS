package pers.nek0peko.datas.dto.data.chart;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pers.nek0peko.datas.dto.BaseDTO;

import java.time.LocalDateTime;

/**
 * ChartDTO
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ChartDTO extends BaseDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 图表名
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
     * 数据表名
     */
    private String table;

    /**
     * 配置
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
