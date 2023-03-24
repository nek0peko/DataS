package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.command.ChartPreviewCmd;
import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.factory.ChartDomainServiceFactory;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;

/**
 * 预览图表
 *
 * @author nek0peko
 * @date 2023/03/24
 */
@Component
public class ChartPreviewCmdExe {

    public SingleResponse<ChartViewDTO> execute(ChartPreviewCmd cmd) {
        final ChartDomainServiceI service = ChartDomainServiceFactory.getService(cmd.getType());
        return SingleResponse.of(ChartViewDTO.builder()
                .option(service.loadDataToOption(
                        cmd.getDatasourceId(),
                        cmd.getTableName(),
                        cmd.getConfig()))
                .build());
    }

}
