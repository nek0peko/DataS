package pers.nek0peko.datas.dto.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询数据表中所有列名
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class DatasourceListColumnQry extends BaseQuery {

    @ApiModelProperty(value = "数据源ID", position = 1)
    private Long id;

    @ApiModelProperty(value = "表名", position = 2)
    private String tableName;

}
