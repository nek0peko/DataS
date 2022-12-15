package pers.nek0peko.datas.dto.data;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pers.nek0peko.datas.dto.BaseDTO;

import java.time.LocalDateTime;

/**
 * DatasourceDTO
 *
 * @author nek0peko
 * @date 2022/12/12
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DatasourceDTO extends BaseDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据源名
     */
    private String name;

    /**
     * 数据源类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 配置
     */
    private JSONObject config;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

}
