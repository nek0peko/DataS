package pers.nek0peko.datas.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pers.nek0peko.datas.domain.object.DatasourceDO;

/**
 * DatasourceMapper
 *
 * @author nek0peko
 * @date 2022/12/12
 */
@Repository
public interface DatasourceMapper extends BaseMapper<DatasourceDO> {
}