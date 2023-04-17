package pers.nek0peko.datas.service.domain;

import com.alibaba.fastjson.JSONObject;
import pers.nek0peko.datas.dto.data.datasource.config.DatasourceConfigDTO;

import java.util.List;

/**
 * DatasourceSchemaDomainServiceI
 *
 * @author nek0peko
 * @date 2023/04/18
 */
public interface DatasourceSchemaDomainServiceI<T extends DatasourceConfigDTO> extends DatasourceDomainServiceI<T> {

    /**
     * 查询数据源中所有Schema
     *
     * @param configJson 数据源配置的JSON对象
     * @return Schema列表
     */
    List<String> listSchema(JSONObject configJson);

}
