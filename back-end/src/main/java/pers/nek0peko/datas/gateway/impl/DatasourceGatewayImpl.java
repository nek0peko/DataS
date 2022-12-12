package pers.nek0peko.datas.gateway.impl;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Component;
import pers.nek0peko.datas.domain.convertor.DatasourceConvertor;
import pers.nek0peko.datas.domain.mapper.DatasourceMapper;
import pers.nek0peko.datas.domain.object.DatasourceDO;
import pers.nek0peko.datas.dto.data.DatasourceDTO;
import pers.nek0peko.datas.gateway.DatasourceGateway;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * DatasourceGatewayImpl
 *
 * @author nek0peko
 * @date 2022/12/12
 */
@Component
public class DatasourceGatewayImpl implements DatasourceGateway {

    @Resource
    private transient DatasourceConvertor convertor;

    @Resource
    private transient DatasourceMapper mapper;

    @Override
    public void save(DatasourceDTO datasource) {
        if (Objects.isNull(datasource.getId())) {
            add(datasource);
        } else {
            modify(datasource);
        }
    }

    private void add(DatasourceDTO datasource) {
        final DatasourceDO datasourceDO = convertor.toDO(datasource);
        final int insert = mapper.insert(datasourceDO);
        if (insert < 1) {
            throw new PersistenceException("新增数据源异常");
        }
    }

    private void modify(DatasourceDTO datasource) {
        final DatasourceDO datasourceDO = convertor.toDO(datasource);
        final int update = mapper.updateById(datasourceDO);
        if (update < 1) {
            throw new PersistenceException("更新数据源异常");
        }
    }

}
