package pers.nek0peko.datas.service.domain.impl.datasource;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.dto.data.datasource.config.OracleConfigDTO;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.service.domain.DatasourceSchemaDomainServiceI;
import pers.nek0peko.datas.util.BaseClassLoaderProvider;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * OracleDatasourceDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/04/28
 */
@Service("Oracle")
public class OracleDatasourceDomainServiceImpl extends BaseClassLoaderProvider implements DatasourceSchemaDomainServiceI<OracleConfigDTO> {

    private final static String DRIVER = "oracle.jdbc.OracleDriver";

    private final static String SERVICE_NAME = "serviceName";

    private final static String SID = "sid";

    @Override
    public List<String> listSchema(JSONObject configJson) {
        final DatasourceResultHolder resultHolder = queryColumn(configJson, "SELECT * FROM all_users");
        if (resultHolder.isSuccess()) {
            return (List<String>) resultHolder.getData();
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }
    }

    @Override
    public DatasourceResultHolder queryTableList(JSONObject configJson) {
        return queryColumn(configJson, String.format(
                "SELECT TABLE_NAME FROM DBA_TABLES WHERE OWNER = '%s'",
                configJson.toJavaObject(OracleConfigDTO.class).getSchema()));
    }

    @Override
    public List<String> listColumn(JSONObject configJson, String tableName) {
        final DatasourceResultHolder resultHolder = queryColumn(configJson, String.format(
                "SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = '%s' AND OWNER = '%s'",
                tableName, configJson.toJavaObject(OracleConfigDTO.class).getSchema()));
        if (resultHolder.isSuccess()) {
            return (List<String>) resultHolder.getData();
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }
    }

    @Override
    public DatasourceResultHolder queryColumnGroupBy(JSONObject configJson, String tableName, String column, String groupBy) {
        final String schema = configJson.toJavaObject(OracleConfigDTO.class).getSchema();
        return queryColumn(configJson, String.format(
                "SELECT %s FROM %s.%s GROUP BY %s ORDER BY %s", column, schema, tableName, groupBy, groupBy));
    }

    @Override
    public DatasourceResultHolder queryColumnSumGroupBy(JSONObject configJson, String tableName, String column, String groupBy) {
        final String schema = configJson.toJavaObject(OracleConfigDTO.class).getSchema();
        return queryColumn(configJson, String.format(
                "SELECT SUM(%s) FROM %s.%s GROUP BY %s ORDER BY %s", column, schema, tableName, groupBy, groupBy));
    }

    private DatasourceResultHolder queryColumn(JSONObject configJson, String sql) {
        final OracleConfigDTO config = configJson.toJavaObject(OracleConfigDTO.class);

        final String url;
        if (SERVICE_NAME.equalsIgnoreCase(config.getConnectionType())) {
            url =  String.format("jdbc:oracle:thin:@%s:%s/%s", config.getHost(), config.getPort(), config.getDatabase());
        } else if (SID.equalsIgnoreCase(config.getConnectionType())) {
            url =  String.format("jdbc:oracle:thin:@%s:%s:%s", config.getHost(), config.getPort(), config.getDatabase());
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_ORACLE_METHOD_ERROR);
        }

        final Properties props = new Properties();
        props.put("oracle.net.CONNECT_TIMEOUT", "5000");
        props.setProperty("user", Optional.ofNullable(config.getUsername()).orElse(""));
        props.setProperty("password", Optional.ofNullable(config.getPassword()).orElse(""));

        return queryColumnByDriver(myClassLoader, DRIVER, sql, url, props);
    }

}
