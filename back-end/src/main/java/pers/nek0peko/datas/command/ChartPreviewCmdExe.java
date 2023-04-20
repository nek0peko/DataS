package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.command.ChartPreviewCmd;
import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.factory.ChartDomainServiceFactory;
import pers.nek0peko.datas.gateway.DatasourceGateway;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;

import javax.annotation.Resource;

/**
 * 预览图表
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@Component
public class ChartPreviewCmdExe {

    @Resource
    private transient DatasourceGateway gateway;

    public SingleResponse<ChartViewDTO> execute(ChartPreviewCmd cmd) {
        final DatasourceDTO datasource = gateway.getById(cmd.getDatasourceId());
        final ChartDomainServiceI service = ChartDomainServiceFactory.getService(cmd.getType());
        return SingleResponse.of(ChartViewDTO.builder()
                .option(service.loadDataToOption(
                        datasource.getType(),
                        datasource.getConfig(),
                        cmd.getTableName(),
                        cmd.getConfig()))
                .build());
    }

}
