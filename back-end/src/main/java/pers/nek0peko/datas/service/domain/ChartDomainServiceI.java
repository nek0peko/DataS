package pers.nek0peko.datas.service.domain;

import com.alibaba.fastjson.JSONObject;
import pers.nek0peko.datas.common.constant.Constants;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.chart.config.ChartConfigDTO;
import pers.nek0peko.datas.dto.data.chart.option.ChartOptionDTO;
import pers.nek0peko.datas.dto.data.datasource.DatasourceTypeEnum;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.util.ApplicationContextHelper;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;

/**
 * ChartDomainServiceI
 *
 * @author nek0peko
 * @date 2023/04/30
 */
public interface ChartDomainServiceI<T extends ChartConfigDTO> {

    /**
     * 从数据源中查询得到绘制图表所需数据
     *
     * @param dsType 数据源类型
     * @param dsConfig 数据源JSON配置
     * @param tableName 数据表名
     * @param configJson 图表JSON配置
     * @return 包含数据的图表配置
     */
    ChartOptionDTO loadDataToOption(String dsType, JSONObject dsConfig, String tableName, JSONObject configJson);

    /**
     * 从定时同步的数据源中查询得到绘制图表所需数据
     *
     * @param datasourceId 数据源ID
     * @param tableName 数据表名
     * @param configJson 图表JSON配置
     * @return 包含数据的图表配置
     */
    default ChartOptionDTO loadSyncDataToOption(Long datasourceId, String tableName, JSONObject configJson) {
        return loadDataToOption(DatasourceTypeEnum.GREENPLUM.getType(),
                JSONObject.parseObject(Constants.GREENPLUM_CONFIG_JSON),
                datasourceId + "-" + tableName, configJson);
    }

    /**
     * 校验和过滤图表配置
     *
     * @param configJson 图表配置的JSON对象
     * @return 过滤后的配置
     */
    default JSONObject validateAndFilterConfig(JSONObject configJson) {
        T config;
        Class<T> clazz = null;
        try {
            for (final Type type : getClass().getGenericInterfaces()) {
                if (type instanceof ParameterizedType) {
                    final ParameterizedType parameterizedType = (ParameterizedType) type;
                    if (parameterizedType.getRawType() == ChartDomainServiceI.class) {
                        clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
                    }
                }
            }
            config = configJson.toJavaObject(Objects.requireNonNull(clazz));
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorEnum.B_CHART_INVALID_CONFIG, e);
        }

        final Validator validator = ApplicationContextHelper.getBean(Validator.class);
        final Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(config);
        constraintViolationSet.stream()
                .findFirst()
                .ifPresent(x -> {
                    throw new BusinessException(BusinessErrorEnum.B_CHART_INVALID_CONFIG);
                });

        return JSONObject.parseObject(JSONObject.toJSONString(configJson.toJavaObject(clazz)));
    }

}
