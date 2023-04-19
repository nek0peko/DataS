package pers.nek0peko.datas.service;

import pers.nek0peko.datas.dto.command.*;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.response.PageResponse;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.dto.response.SingleResponse;

import java.util.List;
import java.util.Map;

/**
 * 数据源服务
 *
 * @author nek0peko
 * @date 2023/04/19
 */
public interface DatasourceServiceI {

    /**
     * 新增数据源
     *
     * @param cmd 新增数据源请求
     * @return Response 是否新增成功
     */
    Response create(DatasourceCreateCmd cmd);

    /**
     * 修改数据源
     *
     * @param cmd 修改数据源请求
     * @return Response 是否修改成功
     */
    Response modify(DatasourceModifyCmd cmd);

    /**
     * 删除数据源
     *
     * @param ids 要删除的数据源ID列表
     * @return 返回删除结果
     */
    Response remove(List<Long> ids);

    /**
     * 查询单个数据源
     *
     * @param id 数据源ID
     * @return 数据源信息
     */
    SingleResponse<DatasourceDTO> view(Long id);

    /**
     * 条件查询数据源列表
     *
     * @param qry 查询条件
     * @return 数据源列表
     */
    PageResponse<DatasourceDTO> list(DatasourceListQry qry);

    /**
     * 查询数据源类型列表
     *
     * @return 数据源类型列表
     */
    SingleResponse<Map<String, Boolean>> listType();

    /**
     * 测试数据源连接
     *
     * @param id 数据源ID
     * @return 是否连接成功
     */
    SingleResponse<Boolean> testLink(Long id);

    /**
     * 查询数据源中所有表名
     *
     * @param id 数据源ID
     * @return 数据表名列表
     */
    SingleResponse<List<String>> listTable(Long id);

    /**
     * 查询数据表中所有列名
     *
     * @param qry 指定数据源和表
     * @return 数据列名列表
     */
    SingleResponse<List<String>> listColumn(DatasourceListColumnQry qry);

    /**
     * 查询数据源中所有Schema名
     *
     * @param qry 数据源连接信息
     * @return Schema列表
     */
    SingleResponse<List<String>> listSchema(DatasourceListSchemaQry qry);

}
