package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

/**
 * 新增数据源
 *
 * @author nek0peko
 * @date 2022/11/17
 */
@Component
public class DatasourceCreateCmdExe {

    @Transactional(rollbackFor = Exception.class)
    public void execute(DatasourceCreateCmd cmd) {
        try {
            final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(cmd.getType());
            service.test();
        } catch (Exception ignored) {
            // 未获取到该数据源类型的服务
        }
    }

}
