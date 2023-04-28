package pers.nek0peko.datas.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.nek0peko.datas.domain.object.PageHolder;
import pers.nek0peko.datas.domain.convertor.DatasourceConvertor;
import pers.nek0peko.datas.domain.mapper.DatasourceMapper;
import pers.nek0peko.datas.domain.object.DatasourceDO;
import pers.nek0peko.datas.dto.command.DatasourceListQry;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.gateway.DatasourceGateway;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * DatasourceGatewayImpl
 *
 * @author nek0peko
 * @date 2023/04/28
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
        final DatasourceDTO datasourceDTO = convertor.toDTO(mapper.selectById(id));
        if (Objects.isNull(datasourceDTO)) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_NOT_EXISTS);
        }
        return datasourceDTO;
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

    @Override
    public PageHolder<DatasourceDTO> list(DatasourceListQry qry) {
        final LambdaQueryWrapper<DatasourceDO> wrapper = Wrappers.lambdaQuery(DatasourceDO.class)
                .like(StringUtils.isNotEmpty(qry.getType()), DatasourceDO::getType, qry.getType())
                .like(StringUtils.isNotEmpty(qry.getName()), DatasourceDO::getName, qry.getName())
                .orderByDesc(DatasourceDO::getCreateTime);
        final Page<DatasourceDO> page = mapper.selectPage(new Page<>(
                qry.getPageIndex(), qry.getPageSize(), qry.isNeedTotalCount()), wrapper);
        return PageHolder.of(convertor.toDTO(page.getRecords()), page.getTotal(), page.getSize(), page.getCurrent());
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
