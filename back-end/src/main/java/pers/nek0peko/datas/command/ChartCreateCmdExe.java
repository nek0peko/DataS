package pers.nek0peko.datas.command;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pers.nek0peko.datas.domain.convertor.ChartConvertor;
import pers.nek0peko.datas.dto.command.ChartCreateCmd;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.ChartDomainServiceFactory;
import pers.nek0peko.datas.gateway.ChartGateway;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;

import javax.annotation.Resource;

/**
 * 新增图表
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Component
public class ChartCreateCmdExe {

    @Resource
    private transient ChartConvertor convertor;

    @Resource
    private transient ChartGateway gateway;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(ChartCreateCmd cmd) {
        final ChartDomainServiceI service = ChartDomainServiceFactory.getService(cmd.getType());
        cmd.setConfig(service.validateAndFilterConfig(cmd.getConfig()));
        try {
            gateway.save(convertor.toDTO(cmd));
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_EXISTS);
        }
        return Response.buildSuccess();
    }

}
