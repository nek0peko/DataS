package pers.nek0peko.datas.factory;

import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceTypeEnum;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;
import pers.nek0peko.datas.service.domain.DatasourceSchemaDomainServiceI;
import pers.nek0peko.datas.util.ApplicationContextHelper;

import java.util.Objects;

/**
 * 工厂模式获取数据源服务
 *
 * @author nek0peko
 * @date 2023/04/18
 */
public class DatasourceDomainServiceFactory {

    private DatasourceDomainServiceFactory() {
    }

    public static DatasourceDomainServiceI getService(String type) {
        if (!DatasourceTypeEnum.isSupportedType(type)) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_UNSUPPORTED);
        }
        return Objects.requireNonNull(ApplicationContextHelper.getBean(type, DatasourceDomainServiceI.class));
    }

    public static DatasourceSchemaDomainServiceI getSchemaService(String type) {
        if (!DatasourceTypeEnum.isSupportedType(type)) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_UNSUPPORTED);
        }
        if (!DatasourceTypeEnum.isSupportSchema(type)) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_SCHEMA_UNSUPPORTED);
        }
        return Objects.requireNonNull(ApplicationContextHelper.getBean(type, DatasourceSchemaDomainServiceI.class));
    }

}
