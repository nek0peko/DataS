package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.domain.object.PageHolder;
import pers.nek0peko.datas.dto.command.DatasourceListQry;
import pers.nek0peko.datas.dto.data.DatasourceDTO;
import pers.nek0peko.datas.dto.response.PageResponse;
import pers.nek0peko.datas.gateway.DatasourceGateway;

import javax.annotation.Resource;

/**
 * 条件查询数据源列表
 *
 * @author nek0peko
 * @date 2022/12/13
 */
@Component
public class DatasourceListQryExe {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    public PageResponse<DatasourceDTO> execute(DatasourceListQry qry) {
        final PageHolder<DatasourceDTO> pageHolder = datasourceGateway.list(qry);
        return PageResponse.of(pageHolder.getData(),
                pageHolder.getTotalCount(),
                pageHolder.getPageSize(),
                pageHolder.getPageIndex());
    }

}
