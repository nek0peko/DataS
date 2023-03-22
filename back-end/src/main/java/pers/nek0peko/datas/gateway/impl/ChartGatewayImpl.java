package pers.nek0peko.datas.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Component;
import pers.nek0peko.datas.domain.convertor.ChartConvertor;
import pers.nek0peko.datas.domain.mapper.ChartMapper;
import pers.nek0peko.datas.domain.object.ChartDO;
import pers.nek0peko.datas.dto.data.chart.ChartDTO;
import pers.nek0peko.datas.gateway.ChartGateway;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * ChartGatewayImpl
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Component
public class ChartGatewayImpl implements ChartGateway {

    @Resource
    private transient ChartConvertor convertor;

    @Resource
    private transient ChartMapper mapper;

    @Override
    public void save(ChartDTO chart) {
        if (Objects.isNull(chart.getId())) {
            add(chart);
        } else {
            modify(chart);
        }
    }

    @Override
    public List<ChartDTO> list(List<String> types) {
        if (CollectionUtils.isEmpty(types)) {
            return Collections.emptyList();
        }
        final LambdaQueryWrapper<ChartDO> wrapper = Wrappers.lambdaQuery(ChartDO.class)
                .in(ChartDO::getType, types);
        final List<ChartDO> doList = mapper.selectList(wrapper);
        return convertor.toDTO(doList);
    }

    private void add(ChartDTO chart) {
        final ChartDO chartDO = convertor.toDO(chart);
        final int insert = mapper.insert(chartDO);
        if (insert < 1) {
            throw new PersistenceException("新增图表异常");
        }
    }

    private void modify(ChartDTO chart) {
        final ChartDO chartDO = convertor.toDO(chart);
        final int update = mapper.updateById(chartDO);
        if (update < 1) {
            throw new PersistenceException("更新图表异常");
        }
    }

}
