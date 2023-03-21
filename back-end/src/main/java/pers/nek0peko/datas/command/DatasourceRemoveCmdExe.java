package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.gateway.DatasourceGateway;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 删除数据源
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Component
public class DatasourceRemoveCmdExe {

    @Resource
    private transient DatasourceGateway datasourceGateway;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(List<Long> ids) {
        final List<DatasourceDTO> datasourceList = datasourceGateway.listByIds(ids);
        if (!datasourceList.isEmpty()) {
            ids = datasourceList.stream().map(DatasourceDTO::getId).collect(Collectors.toList());
            datasourceGateway.removeByIds(ids);
        }
        return Response.buildSuccess();
    }

}
