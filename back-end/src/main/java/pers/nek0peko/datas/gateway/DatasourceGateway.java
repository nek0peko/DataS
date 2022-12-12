package pers.nek0peko.datas.gateway;

import pers.nek0peko.datas.dto.data.DatasourceDTO;

/**
 * DatasourceGateway
 *
 * @author nek0peko
 * @date 2022/12/12
 */
public interface DatasourceGateway {

    /**
     * 保存数据源
     *
     * @param datasource 数据源DTO
     */
    void save(DatasourceDTO datasource);

}
