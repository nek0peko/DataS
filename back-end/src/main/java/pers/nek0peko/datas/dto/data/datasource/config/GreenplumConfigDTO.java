package pers.nek0peko.datas.dto.data.datasource.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * GreenplumConfigDTO
 *
 * @author nek0peko
 * @date 2023/04/30
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class GreenplumConfigDTO extends JdbcConfigDTO {

    @ApiModelProperty(value = "schema", position = 7)
    private String schema;

}
