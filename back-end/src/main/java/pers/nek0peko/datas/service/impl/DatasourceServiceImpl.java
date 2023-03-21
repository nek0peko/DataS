package pers.nek0peko.datas.service.impl;

import org.springframework.stereotype.Service;
import pers.nek0peko.datas.command.*;
import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;
import pers.nek0peko.datas.dto.command.DatasourceListQry;
import pers.nek0peko.datas.dto.command.DatasourceModifyCmd;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.response.PageResponse;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.service.DatasourceServiceI;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据源服务
 *
 * @author nek0peko
 * @date 2022/12/16
 */
@Service
public class DatasourceServiceImpl implements DatasourceServiceI {

    @Resource
    private transient DatasourceCreateCmdExe datasourceCreateCmdExe;

    @Resource
    private transient DatasourceModifyCmdExe datasourceModifyCmdExe;

    @Resource
    private transient DatasourceRemoveCmdExe datasourceRemoveCmdExe;

    @Resource
    private transient DatasourceViewQryExe datasourceViewQryExe;

    @Resource
    private transient DatasourceListQryExe datasourceListQryExe;

    @Resource
    private transient DatasourceListTypeQryExe datasourceListTypeQryExe;

    @Resource
    private transient DatasourceTestLinkCmdExe datasourceTestLinkCmdExe;

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

    @Override
    public SingleResponse<DatasourceDTO> view(Long id) {
        return datasourceViewQryExe.execute(id);
    }

    @Override
    public PageResponse<DatasourceDTO> list(DatasourceListQry qry) {
        return datasourceListQryExe.execute(qry);
    }

    @Override
    public SingleResponse<List<String>> listType() {
        return datasourceListTypeQryExe.execute();
    }

    @Override
    public SingleResponse<Boolean> testLink(Long id) {
        return datasourceTestLinkCmdExe.execute(id);
    }

}
