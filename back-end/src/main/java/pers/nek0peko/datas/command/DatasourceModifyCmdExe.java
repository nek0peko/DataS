package pers.nek0peko.datas.command;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pers.nek0peko.datas.dto.command.DatasourceModifyCmd;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.gateway.DatasourceGateway;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import javax.annotation.Resource;

/**
 * 修改数据源
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@Component
public class DatasourceModifyCmdExe {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(DatasourceModifyCmd cmd) {
        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(cmd.getType());
        cmd.setConfig(service.validateAndFilterConfig(cmd.getConfig()));

        final DatasourceDTO datasource = datasourceGateway.getById(cmd.getId());
        BeanUtil.copyProperties(cmd, datasource, CopyOptions.create().ignoreNullValue());
        try {
            datasourceGateway.save(datasource);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_EXISTS);
        }
        return Response.buildSuccess();
    }

}
