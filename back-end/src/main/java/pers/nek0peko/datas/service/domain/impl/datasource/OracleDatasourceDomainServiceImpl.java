package pers.nek0peko.datas.service.domain.impl.datasource;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.dto.data.datasource.config.OracleConfigDTO;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;
import pers.nek0peko.datas.util.BaseClassLoaderProvider;
import pers.nek0peko.datas.util.MyClassLoader;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * OracleDatasourceDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/04/17
 */
@Service("Oracle")
public class OracleDatasourceDomainServiceImpl extends BaseClassLoaderProvider implements DatasourceDomainServiceI {

    private final static String DRIVER = "oracle.jdbc.OracleDriver";

    private final static String SERVICE_NAME = "serviceName";

    private final static String SID = "sid";

    @Override
    public boolean testLink(JSONObject configJson) {
        return false;
    }

    @Override
    public List<String> listTable(JSONObject configJson) {
        return null;
    }

    @Override
    public List<String> listColumn(JSONObject configJson, String tableName) {
        return null;
    }

    @Override
    public DatasourceResultHolder queryColumnGroupBy(JSONObject configJson, String tableName, String column, String groupBy) {
        return null;
    }

    @Override
    public DatasourceResultHolder queryColumnSumGroupBy(JSONObject configJson, String tableName, String column, String groupBy) {
        return null;
    }

    public List<String> querySchema(JSONObject configJson) {
        final DatasourceResultHolder resultHolder = queryColumn(configJson, "SELECT * FROM all_users");
        if (resultHolder.isSuccess()) {
            return (List<String>) resultHolder.getData();
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }
    }

    private DatasourceResultHolder queryColumn(JSONObject configJson, String sql) {
        final OracleConfigDTO config = configJson.toJavaObject(OracleConfigDTO.class);

        Driver driverClass;
        final MyClassLoader newClassLoader = myClassLoader;
        try {
            driverClass = (Driver) newClassLoader.loadClass(DRIVER).getConstructor().newInstance();
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_DRIVER_NOT_FOUND);
        }

        Connection conn = null;
        Statement stmt = null;
        final List<String> tableList = new ArrayList<>();
        // 获取当前类加载器（用于还原）
        final ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        // 设置新类加载器
        Thread.currentThread().setContextClassLoader(newClassLoader);
        try {
            conn = driverClass.connect(configUrl(config), configProperties(config.getUsername(), config.getPassword()));
            stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tableList.add(rs.getString(1));
            }
            rs.close();
        } catch (Exception e) {
            return DatasourceResultHolder.buildEmptyFailure();
        } finally {
            // 还原类加载器
            Thread.currentThread().setContextClassLoader(oldClassLoader);
            closeConnection(conn, stmt);
        }
        return DatasourceResultHolder.buildSuccessWithData(tableList);
    }

    private static String configUrl(OracleConfigDTO config) {
        if (SERVICE_NAME.equalsIgnoreCase(config.getConnectionType())) {
            return String.format("jdbc:oracle:thin:@%s:%s/%s", config.getHost(), config.getPort(), config.getDatabase());
        } else if (SID.equalsIgnoreCase(config.getConnectionType())) {
            return String.format("jdbc:oracle:thin:@%s:%s:%s", config.getHost(), config.getPort(), config.getDatabase());
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_ORACLE_METHOD_ERROR);
        }
    }

    private static Properties configProperties(String user, String password) {
        final Properties props = new Properties();
        props.put("oracle.net.CONNECT_TIMEOUT", "5000");
        props.setProperty("user", user);
        props.setProperty("password", password);
        return props;
    }

}
