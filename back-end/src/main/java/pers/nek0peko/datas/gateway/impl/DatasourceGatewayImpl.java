package pers.nek0peko.datas.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.nek0peko.datas.domain.convertor.DatasourceConvertor;
import pers.nek0peko.datas.domain.mapper.DatasourceMapper;
import pers.nek0peko.datas.domain.object.DatasourceDO;
import pers.nek0peko.datas.dto.data.DatasourceDTO;
import pers.nek0peko.datas.gateway.DatasourceGateway;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * DatasourceGatewayImpl
 *
 * @author nek0peko
 * @date 2022/12/13
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

    @Override
    public DatasourceDTO getById(Long id) {
        return convertor.toDTO(mapper.selectById(id));
    }

    @Override
    public List<DatasourceDTO> listByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        final LambdaQueryWrapper<DatasourceDO> wrapper = Wrappers.lambdaQuery(DatasourceDO.class)
                .in(DatasourceDO::getId, ids);
        final List<DatasourceDO> doList = mapper.selectList(wrapper);
        return convertor.toDTO(doList);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        mapper.deleteBatchIds(ids);
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
