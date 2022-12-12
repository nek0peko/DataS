package pers.nek0peko.datas.service;

import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;
import pers.nek0peko.datas.dto.command.DatasourceModifyCmd;
import pers.nek0peko.datas.dto.response.Response;

import java.util.List;

/**
 * 数据源服务
 *
 * @author nek0peko
 * @date 2022/12/13
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

}
