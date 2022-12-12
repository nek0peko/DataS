package pers.nek0peko.datas.service.impl;

import org.springframework.stereotype.Service;
import pers.nek0peko.datas.command.DatasourceCreateCmdExe;
import pers.nek0peko.datas.command.DatasourceModifyCmdExe;
import pers.nek0peko.datas.command.DatasourceRemoveCmdExe;
import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;
import pers.nek0peko.datas.dto.command.DatasourceModifyCmd;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.service.DatasourceServiceI;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据源服务
 *
 * @author nek0peko
 * @date 2022/12/13
 */
@Service
public class DatasourceServiceImpl implements DatasourceServiceI {

    @Resource
    private transient DatasourceCreateCmdExe datasourceCreateCmdExe;

    @Resource
    private transient DatasourceModifyCmdExe datasourceModifyCmdExe;

    @Resource
    private transient DatasourceRemoveCmdExe datasourceRemoveCmdExe;

    @Override
    public Response create(DatasourceCreateCmd cmd) {
        return datasourceCreateCmdExe.execute(cmd);
    }

    @Override
    public Response modify(DatasourceModifyCmd cmd) {
        return datasourceModifyCmdExe.execute(cmd);
    }

    @Override
    public Response remove(List<Long> ids) {
        return datasourceRemoveCmdExe.execute(ids);
    }

}
