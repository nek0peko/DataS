package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.DatasourceDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.gateway.DatasourceGateway;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 查询单个数据源详细信息
 *
 * @author nek0peko
 * @date 2022/12/13
 */
@Component
public class DatasourceViewQryExe {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    public SingleResponse<DatasourceDTO> execute(Long id) {
        final DatasourceDTO datasource = datasourceGateway.getById(id);
        if (Objects.isNull(datasource)) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_NOT_EXISTS);
        }
        return SingleResponse.of(datasource);
    }

}
