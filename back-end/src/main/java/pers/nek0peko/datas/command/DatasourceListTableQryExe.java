package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.gateway.DatasourceGateway;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 查询数据源中所有表名
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Component
public class DatasourceListTableQryExe {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    public SingleResponse<List<String>> execute(Long id) {
        final DatasourceDTO datasource = datasourceGateway.getById(id);
        if (Objects.isNull(datasource)) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_NOT_EXISTS);
        }
        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(datasource.getType());
        return SingleResponse.of(service.listTable(datasource.getConfig()));
    }

}
