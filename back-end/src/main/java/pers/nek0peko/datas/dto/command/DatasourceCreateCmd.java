package pers.nek0peko.datas.dto.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 新增数据源
 *
 * @author nek0peko
 * @date 2022/11/17
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class DatasourceCreateCmd extends BaseCommand {

    @ApiModelProperty(value = "类型", required = true, allowableValues = "MySQL")
    @NotBlank(message = "数据源类型不能为空")
    private String type;

}
