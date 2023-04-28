package pers.nek0peko.datas.dto.command;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 查询数据源中所有Schema名
 *
 * @author nek0peko
 * @date 2023/04/28
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class DatasourceListSchemaQry extends BaseQuery {

    @ApiModelProperty(value = "类型", required = true, allowableValues = "Oracle, Postgres")
    @NotBlank(message = "数据源类型不能为空")
    private String type;

    @ApiModelProperty(value = "配置", required = true, position = 1)
    @NotNull(message = "数据源配置不能为空")
    private JSONObject config;

}
