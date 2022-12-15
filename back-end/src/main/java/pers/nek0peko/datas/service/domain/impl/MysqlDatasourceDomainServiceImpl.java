package pers.nek0peko.datas.service.domain.impl;

import org.springframework.stereotype.Service;
import pers.nek0peko.datas.dto.data.config.MysqlConfigDTO;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

/**
 * MysqlDatasourceDomainServiceImpl
 *
 * @author nek0peko
 * @date 2022/12/15
 */
@Service("MySQL")
public class MysqlDatasourceDomainServiceImpl implements DatasourceDomainServiceI<MysqlConfigDTO> {

    public void test() {
        System.out.println("Factory Success!");
    }

}
