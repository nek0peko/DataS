package pers.nek0peko.datas.service.domain.impl.datasource;

import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.dto.data.datasource.config.GreenplumConfigDTO;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;
import pers.nek0peko.datas.service.domain.DatasourceSchemaDomainServiceI;
import pers.nek0peko.datas.util.BaseClassLoaderProvider;

import java.sql.*;
import java.util.*;

/**
 * GreenplumDatasourceDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/04/30
 */
@Service("Greenplum")
public class GreenplumDatasourceDomainServiceImpl extends BaseClassLoaderProvider implements DatasourceSchemaDomainServiceI<GreenplumConfigDTO> {

    private final static String DRIVER = "org.postgresql.Driver";

    @Override
    public List<String> listSchema(JSONObject configJson) {
        final DatasourceResultHolder resultHolder = queryColumn(configJson, "SELECT schema_name FROM information_schema.schemata;");
        if (resultHolder.isSuccess()) {
            return (List<String>) resultHolder.getData();
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }
    }

    @Override
    public DatasourceResultHolder queryTableList(JSONObject configJson) {
        return queryColumn(configJson, String.format(
                "SELECT table_name FROM information_schema.tables WHERE table_schema = '%s';",
                configJson.toJavaObject(GreenplumConfigDTO.class).getSchema()));
    }

    @Override
    public List<String> listColumn(JSONObject configJson, String tableName) {
        final DatasourceResultHolder resultHolder = queryColumn(configJson, String.format(
                "SELECT column_name FROM information_schema.columns WHERE table_name = '%s' AND table_schema = '%s'",
                tableName, configJson.toJavaObject(GreenplumConfigDTO.class).getSchema()));
        if (resultHolder.isSuccess()) {
            return (List<String>) resultHolder.getData();
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }
    }

    @Override
    public DatasourceResultHolder queryColumnGroupBy(JSONObject configJson, String tableName, String column, String groupBy) {
        final String schema = configJson.toJavaObject(GreenplumConfigDTO.class).getSchema();
        return queryColumn(configJson, String.format(
                "SELECT %s FROM %s.%s GROUP BY %s ORDER BY %s", column, schema, tableName, groupBy, groupBy));
    }

    @Override
    public DatasourceResultHolder queryColumnSumGroupBy(JSONObject configJson, String tableName, String column, String groupBy) {
        final String schema = configJson.toJavaObject(GreenplumConfigDTO.class).getSchema();
        return queryColumn(configJson, String.format(
                "SELECT SUM(%s) FROM %s.%s GROUP BY %s ORDER BY %s", column, schema, tableName, groupBy, groupBy));
    }

    @Async
    public void migrateTable(String dsType, JSONObject dsConfig, String tableName) {
        final DatasourceDomainServiceI service = DatasourceDomainServiceFactory.getService(dsType);
    }

    private DatasourceResultHolder queryColumn(JSONObject configJson, String sql) {
        final GreenplumConfigDTO config = configJson.toJavaObject(GreenplumConfigDTO.class);
        return queryColumnByDriver(myClassLoader, DRIVER, sql, configUrl(config), configProps(config));
    }

    private String configUrl(GreenplumConfigDTO config) {
        return String.format("jdbc:postgresql://%s:%s/%s", config.getHost(), config.getPort(), config.getDatabase());
    }

    private Properties configProps(GreenplumConfigDTO config) {
        final Properties props = new Properties();
        props.setProperty("user", Optional.ofNullable(config.getUsername()).orElse(""));
        props.setProperty("password", Optional.ofNullable(config.getPassword()).orElse(""));
        return props;
    }

}
