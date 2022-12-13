package pers.nek0peko.datas.dto.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BasePageQuery
 *
 * @author nek0peko
 * @date 2022/12/13
 */
@NoArgsConstructor
public class BasePageQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final int DEFAULT_PAGE_SIZE = 10;

    private int pageSize = 10;

    private int pageIndex = 1;

    @Getter
    private String orderBy;

    @Getter
    private String orderDirection = "DESC";

    @Getter
    @Setter
    private String groupBy;

    @Getter
    @Setter
    private boolean needTotalCount = true;

    public int getPageIndex() {
        return Math.max(this.pageIndex, 1);
    }

    public int getPageSize() {
        if (this.pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        return this.pageSize;
    }

    public int getOffset() {
        return (this.getPageIndex() - 1) * this.getPageSize();
    }

    public BasePageQuery setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public BasePageQuery setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
        return this;
    }

    public BasePageQuery setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public BasePageQuery setOrderDirection(String orderDirection) {
        if (ASC.equalsIgnoreCase(orderDirection) || DESC.equalsIgnoreCase(orderDirection)) {
            this.orderDirection = orderDirection;
        }
        return this;
    }

}
