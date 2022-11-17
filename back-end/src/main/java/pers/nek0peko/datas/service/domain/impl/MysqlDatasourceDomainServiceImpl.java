package pers.nek0peko.datas.service.domain.impl;

import org.springframework.stereotype.Service;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

/**
 * MysqlDatasourceDomainServiceImpl
 *
 * @author nek0peko
 * @date 2022/11/17
 */
@Service("MySQL")
public class MysqlDatasourceDomainServiceImpl implements DatasourceDomainServiceI {

    public void test() {
        System.out.println("Factory Success!");
    }

}
