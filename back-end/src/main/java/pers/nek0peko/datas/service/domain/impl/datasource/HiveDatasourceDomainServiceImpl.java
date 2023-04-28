package pers.nek0peko.datas.service.domain.impl.datasource;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.dto.data.datasource.config.HiveConfigDTO;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;
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
@Service("Hive")
public class HiveDatasourceDomainServiceImpl extends BaseClassLoaderProvider implements DatasourceDomainServiceI<HiveConfigDTO> {

    private static final String DRIVER = "org.apache.hive.jdbc.HiveDriver";

    @Override
    public DatasourceResultHolder queryTableList(JSONObject configJson) {
        return queryColumn(configJson, String.format("SHOW TABLES IN %s",
                configJson.toJavaObject(HiveConfigDTO.class).getDatabase()));
    }

    @Override
    public List<String> listColumn(JSONObject configJson, String tableName) {
        final DatasourceResultHolder resultHolder = queryColumn(configJson, String.format("DESCRIBE %s.%s",
                configJson.toJavaObject(HiveConfigDTO.class).getDatabase(), tableName));
        if (resultHolder.isSuccess()) {
            return (List<String>) resultHolder.getData();
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }
    }

    @Override
    public DatasourceResultHolder queryColumnGroupBy(JSONObject configJson, String tableName, String column, String groupBy) {
        return queryColumn(configJson, String.format(
                "SELECT %s FROM %s GROUP BY %s ORDER BY %s", column, tableName, groupBy, groupBy));
    }

    @Override
    public DatasourceResultHolder queryColumnSumGroupBy(JSONObject configJson, String tableName, String column, String groupBy) {
        return queryColumn(configJson, String.format(
                "SELECT SUM(%s) FROM %s GROUP BY %s ORDER BY %s", column, tableName, groupBy, groupBy));
    }

    private DatasourceResultHolder queryColumn(JSONObject configJson, String sql) {
        final HiveConfigDTO config = configJson.toJavaObject(HiveConfigDTO.class);

        final String url = String.format("jdbc:hive2://%s:%s/%s", config.getHost(), config.getPort(),
                config.getDatabase()) + (StringUtils.isEmpty(config.getJdbc()) ? "" : ";" + config.getJdbc());

        final Properties props = new Properties();
        props.setProperty("user", Optional.ofNullable(config.getUsername()).orElse(""));
        props.setProperty("password", Optional.ofNullable(config.getPassword()).orElse(""));

        return queryColumnByDriver(myClassLoader, DRIVER, sql, url, props);
    }

}
