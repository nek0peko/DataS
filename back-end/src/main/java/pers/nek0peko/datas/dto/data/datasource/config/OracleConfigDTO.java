package pers.nek0peko.datas.dto.data.datasource.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * OracleConfigDTO
 *
 * @author nek0peko
 * @date 2023/04/17
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OracleConfigDTO extends JdbcConfigDTO {

    @ApiModelProperty(value = "schema", position = 7)
    private String schema;

    @ApiModelProperty(value = "服务名/SID", position = 8, required = true, allowableValues = "serviceName, sid")
    private String connectionType;

}
