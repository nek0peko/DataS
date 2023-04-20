package pers.nek0peko.datas.command;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pers.nek0peko.datas.dto.command.ChartModifyCmd;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.chart.ChartDTO;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.ChartDomainServiceFactory;
import pers.nek0peko.datas.gateway.ChartGateway;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 修改图表
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@Component
public class ChartModifyCmdExe {

    @Resource
    private transient ChartGateway chartGateway;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(ChartModifyCmd cmd) {
        final ChartDTO chart = chartGateway.listById(cmd.getId());
        if (Objects.isNull(chart)) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_NOT_EXISTS);
        }

        final ChartDomainServiceI service = ChartDomainServiceFactory.getService(chart.getType());
        cmd.setConfig(service.validateAndFilterConfig(cmd.getConfig()));
        BeanUtil.copyProperties(cmd, chart, CopyOptions.create().ignoreNullValue());

        try {
            chartGateway.save(chart);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_EXISTS);
        }
        return Response.buildSuccess();
    }

}
