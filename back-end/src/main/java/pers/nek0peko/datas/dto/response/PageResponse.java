package pers.nek0peko.datas.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * PageResponse
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@NoArgsConstructor
public class PageResponse<T> extends Response {

    @Getter
    @Setter
    private int totalCount = 0;

    private int pageSize = 1;

    private int pageIndex = 1;

    @Setter
    private Collection<T> data;

    public int getPageSize() {
        return Math.max(this.pageSize, 1);
    }

    public int getPageIndex() {
        return Math.max(this.pageIndex, 1);
    }

    public List<T> getData() {
        return null == this.data ? Collections.emptyList() : new ArrayList(this.data);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, 1);
    }

    public boolean isEmpty() {
        return this.data == null || this.data.isEmpty();
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public int getTotalPages() {
        return this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize : this.totalCount / this.pageSize + 1;
    }

    public static PageResponse buildSuccess() {
        final PageResponse response = new PageResponse();
        response.setSuccess(true);
        return response;
    }

    public static PageResponse buildFailure(String errCode, String errMessage) {
        final PageResponse response = new PageResponse();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> PageResponse<T> of(int pageSize, int pageIndex) {
        return of(Collections.emptyList(), 0, pageSize, pageIndex);
    }

    public static <T> PageResponse<T> of(Collection<T> data, int totalCount, int pageSize, int pageIndex) {
        final PageResponse<T> response = new PageResponse();
        response.setSuccess(true);
        response.setData(data);
        response.setTotalCount(totalCount);
        response.setPageSize(pageSize);
        response.setPageIndex(pageIndex);
        return response;
    }

}
