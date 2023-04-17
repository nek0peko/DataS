package pers.nek0peko.datas.dto.data.datasource.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * JdbcConfigDTO
 *
 * @author nek0peko
 * @date 2023/04/17
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class JdbcConfigDTO extends DatasourceConfigDTO {

    @ApiModelProperty(value = "主机名/IP地址", position = 1, required = true)
    @Pattern(regexp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$|^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)+([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$", message = "主机名/IP格式错误")
    private String host;

    @ApiModelProperty(value = "端口", position = 2, required = true)
    @Pattern(regexp = "^([1-9]\\d{0,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$", message = "端口格式错误或超出范围")
    private String port;

    @ApiModelProperty(value = "用户名", position = 3, required = true)
    @Pattern(regexp = "\\S{0,100}", message = "用户名必须是有效的字符串")
    private String username;

    @ApiModelProperty(value = "密码", position = 4, required = true)
    @Pattern(regexp = "\\S{0,100}", message = "密码必须是有效的字符串")
    private String password;

    @ApiModelProperty(value = "数据库", position = 5, required = true)
    @Pattern(regexp = "[.A-Za-z\\d_-]{1,100}", message = "数据库必须是有效的字符串")
    private String database;

    @ApiModelProperty(value = "JDBC连接参数", position = 6)
    @Pattern(regexp = "(([.A-Za-z\\d\\-_/]+=[.A-Za-z\\d\\-_/]+)(&[.A-Za-z\\d\\-_/]+=[.A-Za-z\\d\\-_/]+)*)?", message = "JDBC参数必须是有效的字符串")
    private String jdbc;

}
