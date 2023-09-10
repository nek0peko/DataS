package pers.nek0peko.datas.service.domain;

import com.alibaba.fastjson.JSONObject;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.dto.data.datasource.config.DatasourceConfigDTO;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.util.ApplicationContextHelper;
import pers.nek0peko.datas.util.MyClassLoader;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

/**
 * DatasourceDomainServiceI
 *
 * @author nek0peko
 * @date 2023/05/01
 */
public interface DatasourceDomainServiceI<T extends DatasourceConfigDTO> {

    /**
     * 查询数据源中所有表名
     *
     * @param configJson 数据源配置的JSON对象
     * @return 数据表名列表
     */
    DatasourceResultHolder queryTableList(JSONObject configJson);

    /**
     * 查询数据表中所有列名
     *
     * @param configJson 数据源配置的JSON对象
     * @param tableName  数据表名
     * @return 数据列名列表
     */
    List<String> listColumn(JSONObject configJson, String tableName);

    /**
     * 查询按指定列GroupBy后的指定列
     *
     * @param configJson 数据源配置
     * @param tableName  表名
     * @param column     查询的列名
     * @param groupBy    按此列GroupBy
     * @return 指定列结果
     */
    DatasourceResultHolder queryColumnGroupBy(JSONObject configJson, String tableName, String column, String groupBy);

    /**
     * 查询按指定列GroupBy后的指定列总和
     *
     * @param configJson 数据源配置
     * @param tableName  表名
     * @param column     查询的列名
     * @param groupBy    按此列GroupBy
     * @return 指定列总和结果
     */
    DatasourceResultHolder queryColumnSumGroupBy(JSONObject configJson, String tableName, String column, String groupBy);

    /**
     * 测试数据源连接
     *
     * @param configJson 数据源配置的JSON对象
     * @return 是否连接成功
     */
    default boolean testLink(JSONObject configJson) {
        return queryTableList(configJson).isSuccess();
    }

    /**
     * 查询数据源中所有表名
     *
     * @param configJson 数据源配置的JSON对象
     * @return 数据表名列表
     */
    default List<String> listTable(JSONObject configJson) {
        final DatasourceResultHolder resultHolder = queryTableList(configJson);
        if (resultHolder.isSuccess()) {
            return (List<String>) resultHolder.getData();
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }
    }

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
                    if (!DatasourceDomainServiceI.class.isAssignableFrom(
                            Class.forName(parameterizedType.getRawType().getTypeName()))) {
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
     * 加载驱动查询数据列
     *
     * @param driver        驱动名
     * @param url           URL
     * @param props         Properties
     * @param sql           查询语句
     * @param myClassLoader 自定义类加载器
     * @return 数据列
     */
    default DatasourceResultHolder queryColumnByDriver(MyClassLoader myClassLoader, String driver, String sql, String url, Properties props) {
        Connection conn = null;
        Statement stmt = null;
        final List<String> column = new ArrayList<>();

        final Driver driverClass = loadDriver(myClassLoader, driver);
        // 获取当前类加载器（用于还原）
        final ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        // 设置新类加载器
        Thread.currentThread().setContextClassLoader(myClassLoader);

        try {
            conn = driverClass.connect(url, props);
            stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                column.add(rs.getString(1));
            }
            rs.close();
        } catch (Exception e) {
            return DatasourceResultHolder.buildEmptyFailure();
        } finally {
            // 还原类加载器
            Thread.currentThread().setContextClassLoader(oldClassLoader);
            closeConnection(conn, stmt);
        }
        return DatasourceResultHolder.buildSuccessWithData(column);
    }

    /**
     * 加载驱动查询数据列到列表
     *
     * @param driver        驱动名
     * @param url           URL
     * @param props         Properties
     * @param sql           查询语句
     * @param myClassLoader 自定义类加载器
     * @return 数据列列表
     */
    default List<Map<String, Object>> queryToListByDriver(MyClassLoader myClassLoader, String driver, String sql, String url, Properties props) {
        Connection conn = null;
        Statement stmt = null;
        final List<Map<String, Object>> resultList = new ArrayList<>();

        final Driver driverClass = loadDriver(myClassLoader, driver);
        // 获取当前类加载器（用于还原）
        final ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        // 设置新类加载器
        Thread.currentThread().setContextClassLoader(myClassLoader);
        try {
            conn = driverClass.connect(url, props);
            stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery(sql);
            final ResultSetMetaData md = rs.getMetaData();
            final int count = md.getColumnCount();

            while (rs.next()) {
                final Map<String, Object> row = new HashMap<>(count);
                for (int i = 1; i <= count; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                resultList.add(row);
            }
            rs.close();
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        } finally {
            // 还原类加载器
            Thread.currentThread().setContextClassLoader(oldClassLoader);
            closeConnection(conn, stmt);
        }
        return resultList;
    }

    /**
     * 获取数据源驱动
     *
     * @param classLoader 自定义类加载器
     * @param driver      数据源驱动名
     * @return 数据源驱动
     */
    default Driver loadDriver(MyClassLoader classLoader, String driver) {
        Driver driverClass;
        try {
            driverClass = (Driver) classLoader.loadClass(driver).getConstructor().newInstance();
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_DRIVER_NOT_FOUND);
        }
        return driverClass;
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
