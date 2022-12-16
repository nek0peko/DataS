package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.data.DatasourceDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.gateway.DatasourceGateway;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import javax.annotation.Resource;

/**
 * 测试数据源连接
 *
 * @author nek0peko
 * @date 2022/12/16
 */
@Component
public class DatasourceTestLinkCmdExe {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    public SingleResponse<Boolean> execute(Long id) {
        DatasourceDTO datasource = datasourceGateway.getById(id);
        DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(datasource.getType());
        return SingleResponse.of(service.testLink(datasource.getConfig()));
    }

}
