package pers.nek0peko.datas.service.impl;

import org.springframework.stereotype.Service;
import pers.nek0peko.datas.command.*;
import pers.nek0peko.datas.dto.command.*;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.response.PageResponse;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.service.DatasourceServiceI;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数据源服务
 *
 * @author nek0peko
 * @date 2023/04/19
 */
@Service
public class DatasourceServiceImpl implements DatasourceServiceI {

    @Resource
    private transient DatasourceListColumnQryExe datasourceListColumnQryExe;

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

    @Resource
    private transient DatasourceListTableQryExe datasourceListTableQryExe;

    @Resource
    private transient DatasourceListSchemaQryExe datasourceListSchemaQryExe;

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
    public SingleResponse<Map<String, Boolean>> listType() {
        return datasourceListTypeQryExe.execute();
    }

    @Override
    public SingleResponse<Boolean> testLink(Long id) {
        return datasourceTestLinkCmdExe.execute(id);
    }

    @Override
    public SingleResponse<List<String>> listTable(Long id) {
        return datasourceListTableQryExe.execute(id);
    }

    @Override
    public SingleResponse<List<String>> listColumn(DatasourceListColumnQry qry) {
        return datasourceListColumnQryExe.execute(qry);
    }

    @Override
    public SingleResponse<List<String>> listSchema(DatasourceListSchemaQry qry) {
        return datasourceListSchemaQryExe.execute(qry);
    }

}
