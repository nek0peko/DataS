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
 * 预览图表
 *
 * @author nek0peko
 * @date 2023/03/24
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class ChartPreviewCmd extends BaseCommand {

    @ApiModelProperty(value = "类型", required = true, allowableValues = "bar, line")
    @NotBlank(message = "图表类型不能为空")
    private String type;

    @ApiModelProperty(value = "数据源ID", required = true, position = 1)
    @NotNull(message = "数据源ID不能为空")
    private Long datasourceId;

    @ApiModelProperty(value = "数据表名", required = true, position = 2)
    @Length(min = 1, max = 30, message = "名称长度不超过30")
    @NotBlank(message = "数据表名不能为空")
    private String tableName;

    @ApiModelProperty(value = "配置", required = true, position = 3)
    @NotNull(message = "图表配置不能为空")
    private JSONObject config;

}
