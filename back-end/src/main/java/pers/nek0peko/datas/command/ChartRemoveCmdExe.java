package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.gateway.ChartGateway;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 删除图表
 *
 * @author nek0peko
 * @date 2023/04/04
 */
@Component
public class ChartRemoveCmdExe {

    @Resource
    private transient ChartGateway gateway;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(Long id) {
        if (Objects.isNull(gateway.listById(id))) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_NOT_EXISTS);
        }
        gateway.removeById(id);
        return Response.buildSuccess();
    }

}
