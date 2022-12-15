package pers.nek0peko.datas.service.domain;

import com.alibaba.fastjson.JSONObject;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.util.ApplicationContextHelper;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;

/**
 * DatasourceDomainServiceI
 *
 * @author nek0peko
 * @date 2022/12/15
 */
public interface DatasourceDomainServiceI<T> {

    /**
     * test
     */
    void test();

    /**
     * 校验和过滤数据源配置
     *
     * @param configJson 数据源配置的JSON对象
     * @return 过滤后的配置
     */
    default JSONObject validateAndFilterConfig(JSONObject configJson) {
        T config;
        Class<T> clazz = null;
        try {
            for (final Type type : getClass().getGenericInterfaces()) {
                if (type instanceof ParameterizedType) {
                    final ParameterizedType parameterizedType = (ParameterizedType) type;
                    if (parameterizedType.getRawType() != DatasourceDomainServiceI.class) {
                        continue;
                    }
                    clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
                }
            }
            config = configJson.toJavaObject(Objects.requireNonNull(clazz));
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_INVALID_CONFIG, e);
        }

        final Validator validator = ApplicationContextHelper.getBean(Validator.class);
        final Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(config);
        constraintViolationSet.stream()
                .findFirst()
                .ifPresent(x -> {
                    throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_PARAMETER_ERROR);
                });

        // 由于前端会传入额外字段，在这里过滤掉其它数据源的字段；虽然这些字段并不会造成影响，但会让数据库存储冗余数据。
        return JSONObject.parseObject(JSONObject.toJSONString(configJson.toJavaObject(clazz)));
    }

}
