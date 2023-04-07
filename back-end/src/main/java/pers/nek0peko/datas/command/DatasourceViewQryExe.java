package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.gateway.DatasourceGateway;

import javax.annotation.Resource;

/**
 * 查询单个数据源详细信息
 *
 * @author nek0peko
 * @date 2023/04/07
 */
@Component
public class DatasourceViewQryExe {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    public SingleResponse<DatasourceDTO> execute(Long id) {
        return SingleResponse.of(datasourceGateway.getById(id));
    }

}
