package pers.nek0peko.datas.service.domain;

import com.alibaba.fastjson.JSONObject;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.dto.data.datasource.config.DatasourceConfigDTO;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.util.ApplicationContextHelper;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * DatasourceDomainServiceI
 *
 * @author nek0peko
 * @date 2023/04/17
 */
public interface DatasourceDomainServiceI<T extends DatasourceConfigDTO> {

    /**
     * 测试数据源连接
     *
     * @param configJson 数据源配置的JSON对象
     * @return 是否连接成功
     */
    boolean testLink(JSONObject configJson);

    /**
     * 查询数据源中所有表名
     *
     * @param configJson 数据源配置的JSON对象
     * @return 数据表名列表
     */
    List<String> listTable(JSONObject configJson);

    /**
     * 查询数据表中所有列名
     *
     * @param configJson 数据源配置的JSON对象
     * @param tableName 数据表名
     * @return 数据列名列表
     */
    List<String> listColumn(JSONObject configJson, String tableName);

    /**
     * 查询按指定列GroupBy后的指定列
     *
     * @param configJson 数据源配置
     * @param tableName 表名
     * @param column 查询的列名
     * @param groupBy 按此列GroupBy
     * @return 指定列结果
     */
    DatasourceResultHolder queryColumnGroupBy(JSONObject configJson, String tableName, String column, String groupBy);

    /**
     * 查询按指定列GroupBy后的指定列总和
     *
     * @param configJson 数据源配置
     * @param tableName 表名
     * @param column 查询的列名
     * @param groupBy 按此列GroupBy
     * @return 指定列总和结果
     */
    DatasourceResultHolder queryColumnSumGroupBy(JSONObject configJson, String tableName, String column, String groupBy);

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
                    throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_INVALID_CONFIG);
                });

        // 由于前端会传入额外字段，在这里过滤掉其它数据源的字段；虽然这些字段并不会造成影响，但会让数据库存储冗余数据。
        return JSONObject.parseObject(JSONObject.toJSONString(configJson.toJavaObject(clazz)));
    }

    /**
     * 关闭数据源连接
     *
     * @param conn connection
     * @param stmt statement
     */
    default void closeConnection(Connection conn, Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

}
