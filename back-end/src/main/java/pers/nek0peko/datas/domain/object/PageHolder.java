package pers.nek0peko.datas.domain.object;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * PageHolder
 *
 * @author nek0peko
 * @date 2022/12/13
 */
public class PageHolder<T> {

    @Getter
    @Setter
    private int totalCount;

    private int pageSize;

    private int pageIndex;

    @Setter
    private Collection<T> data;

    public int getPageSize() {
        return Math.max(pageSize, 1);
    }

    public int getPageIndex() {
        return Math.max(pageIndex, 1);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, 1);
    }

    public List<T> getData() {
        return null == data ? Collections.emptyList() : new ArrayList<>(data);
    }

    public static <T> PageHolder<T> of(Collection<T> data, int totalCount, int pageSize, int pageIndex) {
        final PageHolder<T> pageHolder = new PageHolder<>();
        pageHolder.setData(data);
        pageHolder.setTotalCount(totalCount);
        pageHolder.setPageSize(pageSize);
        pageHolder.setPageIndex(pageIndex);
        return pageHolder;
    }

    public static <T> PageHolder<T> of(int pageSize, int pageIndex) {
        return of(Collections.emptyList(), 0, pageSize, pageIndex);
    }

    public static <T> PageHolder<T> of(Collection<T> data, long totalCount, long pageSize, long pageIndex) {
        return of(data, (int) totalCount, (int) pageSize, (int) pageIndex);
    }

}
