package pers.nek0peko.datas.command;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pers.nek0peko.datas.domain.convertor.DatasourceConvertor;
import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.gateway.DatasourceGateway;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import javax.annotation.Resource;

/**
 * 新增数据源
 *
 * @author nek0peko
 * @date 2022/12/12
 */
@Component
public class DatasourceCreateCmdExe {

    @Resource
    private transient DatasourceConvertor convertor;

    @Resource
    private transient DatasourceGateway gateway;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(DatasourceCreateCmd cmd) {
        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(cmd.getType());
//        cmd.setConfig(service.validateAndFilterConfig(cmd.getConfig()));
        try {
            gateway.save(convertor.toDTO(cmd));
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_EXISTS);
        }
        return Response.buildSuccess();
    }

}
