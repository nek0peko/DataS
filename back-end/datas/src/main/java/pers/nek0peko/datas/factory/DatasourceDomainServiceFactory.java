package pers.nek0peko.datas.factory;

import pers.nek0peko.datas.dto.data.DatasourceTypeEnum;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;
import pers.nek0peko.datas.util.ApplicationContextHelper;

import java.util.Objects;

/**
 * 工厂模式获取数据源服务
 *
 * @author nek0peko
 * @date 2022/11/17
 */
public class DatasourceDomainServiceFactory {

    private DatasourceDomainServiceFactory() {

    }

    public static DatasourceDomainServiceI getService(String type) {
        if (!DatasourceTypeEnum.isSupportedType(type)) {
            // TODO: throw new BizException(ErrorCodeEnum.B_DATASOURCE_UNSUPPORTED);
            System.out.println("Unsupported Datasource Type!");
        }
        return Objects.requireNonNull(ApplicationContextHelper.getBean(type, DatasourceDomainServiceI.class));
    }

}
