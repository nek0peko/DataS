package pers.nek0peko.datas.domain.object;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * BaseDO
 *
 * @author nek0peko
 * @date 2022/12/12
 */
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

    public BaseDO() {
    }

    public Long getId() {
        return this.id;
    }

    public String getCreator() {
        return this.creator;
    }

    public LocalDateTime getCreateTime() {
        return this.createTime;
    }

    public LocalDateTime getUpdateTime() {
        return this.updateTime;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setCreator(final String creator) {
        this.creator = creator;
    }

    public void setCreateTime(final LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(final LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
        Long var10000 = this.getId();
        return "BaseDO(id=" + var10000 + ", creator=" + this.getCreator() + ", createTime=" + this.getCreateTime() + ", updateTime=" + this.getUpdateTime() + ")";
    }

}
