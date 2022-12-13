package pers.nek0peko.datas.domain.object;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * BaseDO
 *
 * @author nek0peko
 * @date 2022/12/12
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseDO {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @OrderBy
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public String toString() {
        Long var10000 = this.getId();
        return "BaseDO(id=" + var10000 + ", creator=" + this.getCreator() + ", createTime=" + this.getCreateTime() + ", updateTime=" + this.getUpdateTime() + ")";
    }

}
