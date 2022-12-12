package pers.nek0peko.datas.service;

import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;
import pers.nek0peko.datas.dto.response.Response;

/**
 * 数据源服务
 *
 * @author nek0peko
 * @date 2022/12/12
 */
public interface DatasourceServiceI {

    /**
     * 新增数据源
     *
     * @param cmd 新增数据源请求
     * @return Response 是否新增成功
     */
    Response create(DatasourceCreateCmd cmd);

}
