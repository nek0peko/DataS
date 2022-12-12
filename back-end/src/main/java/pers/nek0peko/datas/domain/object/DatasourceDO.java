package pers.nek0peko.datas.domain.object;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DatasourceDO
 *
 * @author nek0peko
 * @date 2022/12/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "datasource", autoResultMap = true)
public class DatasourceDO extends BaseDO {

    /**
     * 数据源名
     */
    private String name;

    /**
     * 数据源类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 配置
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONObject config;

}
