package pers.nek0peko.datas.dto.data.datasource.config;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MysqlConfigDTO
 *
 * @author nek0peko
 * @date 2023/04/28
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HiveConfigDTO extends JdbcConfigDTO {
}
