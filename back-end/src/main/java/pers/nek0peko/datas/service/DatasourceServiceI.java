package pers.nek0peko.datas.service;

import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;

/**
 * 数据源服务
 *
 * @author nek0peko
 * @date 2022/11/17
 */
public interface DatasourceServiceI {

    /**
     * 新增数据源
     *
     * @param cmd 新增数据源请求
     */
    void create(DatasourceCreateCmd cmd);

}
