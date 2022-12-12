package pers.nek0peko.datas.dto.command;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改数据源
 *
 * @author nek0peko
 * @date 2022/12/13
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class DatasourceModifyCmd extends BaseCommand {

    @ApiModelProperty(value = "数据源ID", required = true)
    @NotNull(message = "数据源ID不能为空")
    private Long id;

    @ApiModelProperty(value = "名称", required = true, position = 1)
    @Length(min = 1, max = 20, message = "名称长度不超过20")
    @NotBlank(message = "数据源名不能为空")
    private String name;

    @ApiModelProperty(value = "描述：长度不超过50", position = 2, allowEmptyValue = true)
    @Length(max = 50, message = "描述长度应不超过50")
    private String description;

    @ApiModelProperty(value = "配置", required = true, position = 3)
    @NotNull(message = "数据源配置不能为空")
    private JSONObject config;

}
