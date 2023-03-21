package pers.nek0peko.datas.dto.data.datasource.config;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MysqlConfigDTO
 *
 * @author nek0peko
 * @date 2022/12/15
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MysqlConfigDTO extends JdbcConfigDTO {
}
