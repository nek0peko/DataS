package pers.nek0peko.datas.dto.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 条件查询数据源
 *
 * @author nek0peko
 * @date 2022/12/13
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class DatasourceListQry extends BasePageQuery {

    @ApiModelProperty(value = "类型", position = 1)
    private String type;

    @ApiModelProperty(value = "名称", position = 2)
    private String name;

}
