package pers.nek0peko.datas.service.domain.impl.datasource;

import cn.hutool.core.net.url.UrlQuery;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import pers.nek0peko.datas.dto.data.BusinessErrorEnum;
import pers.nek0peko.datas.dto.data.datasource.DatasourceResultHolder;
import pers.nek0peko.datas.dto.data.datasource.config.MysqlConfigDTO;
import pers.nek0peko.datas.exception.BusinessException;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * MysqlDatasourceDomainServiceImpl
 *
 * @author nek0peko
 * @date 2023/04/18
 */
@Service("MySQL")
public class MysqlDatasourceDomainServiceImpl implements DatasourceDomainServiceI<MysqlConfigDTO> {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    @Override
    public DatasourceResultHolder queryTableList(JSONObject configJson) {
        return queryColumn(configJson, String.format(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '%s';",
                configJson.toJavaObject(MysqlConfigDTO.class).getDatabase()));
    }

    @Override
    public List<String> listColumn(JSONObject configJson, String tableName) {
        final DatasourceResultHolder resultHolder = queryColumn(configJson, String.format(
                "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '%s' AND TABLE_SCHEMA = '%s';",
                tableName, configJson.toJavaObject(MysqlConfigDTO.class).getDatabase()));
        if (resultHolder.isSuccess()) {
            return (List<String>) resultHolder.getData();
        } else {
            throw new BusinessException(BusinessErrorEnum.B_DATASOURCE_FAILED);
        }
    }

    @Override
    public DatasourceResultHolder queryColumnGroupBy(JSONObject configJson, String tableName, String column, String groupBy) {
        return queryColumn(configJson, String.format(
                "SELECT %s FROM %s GROUP BY %s ORDER BY %s;", column, tableName, groupBy, groupBy));
    }

    @Override
    public DatasourceResultHolder queryColumnSumGroupBy(JSONObject configJson, String tableName, String column, String groupBy) {
        return queryColumn(configJson, String.format(
                "SELECT SUM(%s) FROM %s GROUP BY %s ORDER BY %s;", column, tableName, groupBy, groupBy));
    }

    private DatasourceResultHolder queryColumn(JSONObject configJson, String sql) {
        final MysqlConfigDTO config = configJson.toJavaObject(MysqlConfigDTO.class);
        String url = String.format("jdbc:mysql://%s:%s/%s?", config.getHost(), config.getPort(), config.getDatabase());

        // 由于TCP/IP的结构，Socket没有办法检测到网络错误，因此应用不能检测到与数据库到连接断开了。
        // 如果没有设置 Socket 超时，应用程序会一直等待数据库返回结果。（即死连接）
        url += UrlQuery.of(config.getJdbc(), null)
                .add("connectTimeout", 3000)
                .add("socketTimeout", 5000)
                .toString();

        Connection conn = null;
        Statement stmt = null;
        final List<String> column = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(url, config.getUsername(), config.getPassword());
            stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                column.add(rs.getString(1));
            }
            rs.close();
        } catch (Exception e) {
            return DatasourceResultHolder.buildEmptyFailure();
        } finally {
            closeConnection(conn, stmt);
        }
        return DatasourceResultHolder.buildSuccessWithData(column);
    }

}
