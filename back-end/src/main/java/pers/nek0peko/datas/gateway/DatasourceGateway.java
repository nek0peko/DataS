package pers.nek0peko.datas.gateway;

import pers.nek0peko.datas.dto.data.DatasourceDTO;

import java.util.List;

/**
 * DatasourceGateway
 *
 * @author nek0peko
 * @date 2022/12/13
 */
public interface DatasourceGateway {

    /**
     * 保存数据源
     *
     * @param datasource 数据源DTO
     */
    void save(DatasourceDTO datasource);

    /**
     * 根据ID获取数据源DTO
     *
     * @param id 数据源ID
     * @return 数据源DTO
     */
    DatasourceDTO getById(Long id);

    /**
     * 批量查询数据源
     *
     * @param ids 数据源ID列表
     * @return 数据源DTO列表
     */
    List<DatasourceDTO> listByIds(List<Long> ids);

    /**
     * 批量删除数据源
     *
     * @param ids 数据源id列表
     */
    void removeByIds(List<Long> ids);

}
