package pers.nek0peko.datas.domain.object;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ChartDO
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "chart", autoResultMap = true)
public class ChartDO extends BaseDO {

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
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONObject config;

}