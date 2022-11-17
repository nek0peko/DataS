package pers.nek0peko.datas.service.impl;

import org.springframework.stereotype.Service;
import pers.nek0peko.datas.command.DatasourceCreateCmdExe;
import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;
import pers.nek0peko.datas.service.DatasourceServiceI;

import javax.annotation.Resource;

/**
 * 数据源服务
 *
 * @author nek0peko
 * @date 2022/11/17
 */
@Service
public class DatasourceServiceImpl implements DatasourceServiceI {

    @Resource
    private transient DatasourceCreateCmdExe datasourceCreateCmdExe;

    @Override
    public void create(DatasourceCreateCmd cmd) {
        datasourceCreateCmdExe.execute(cmd);
    }

}
