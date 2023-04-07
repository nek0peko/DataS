package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.command.DatasourceListColumnQry;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.gateway.DatasourceGateway;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询数据表中所有列名
 *
 * @author nek0peko
 * @date 2023/04/07
 */
@Component
public class DatasourceListColumnQryExe {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    public SingleResponse<List<String>> execute(DatasourceListColumnQry qry) {
        final DatasourceDTO datasource = datasourceGateway.getById(qry.getId());
        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(datasource.getType());
        return SingleResponse.of(service.listColumn(datasource.getConfig(), qry.getTableName()));
    }

}
