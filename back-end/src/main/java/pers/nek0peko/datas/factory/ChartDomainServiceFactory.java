package pers.nek0peko.datas.factory;

import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.chart.ChartTypeEnum;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.service.domain.ChartDomainServiceI;
import pers.nek0peko.datas.util.ApplicationContextHelper;

import java.util.Objects;

/**
 * 工厂模式获取图表服务
 *
 * @author nek0peko
 * @date 2023/03/22
 */
public class ChartDomainServiceFactory {

    private ChartDomainServiceFactory() {
    }

    public static ChartDomainServiceI getService(String type) {
        if (!ChartTypeEnum.isSupportedType(type)) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_UNSUPPORTED);
        }
        return Objects.requireNonNull(ApplicationContextHelper.getBean(type, ChartDomainServiceI.class));
    }

}
