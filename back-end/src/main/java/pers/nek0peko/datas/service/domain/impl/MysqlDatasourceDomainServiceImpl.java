package pers.nek0peko.datas.service.domain.impl;

import cn.hutool.core.net.url.UrlQuery;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import pers.nek0peko.datas.dto.data.datasource.config.MysqlConfigDTO;
import pers.nek0peko.datas.service.domain.DatasourceDomainServiceI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * MysqlDatasourceDomainServiceImpl
 *
 * @author nek0peko
 * @date 2022/12/16
 */
@Service("MySQL")
public class MysqlDatasourceDomainServiceImpl implements DatasourceDomainServiceI<MysqlConfigDTO> {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    @Override
    public boolean testLink(JSONObject configJson) {
        final MysqlConfigDTO config = configJson.toJavaObject(MysqlConfigDTO.class);

        final String sql = String.format(
                "SELECT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '%s';",
                config.getDatabase());

        String url = String.format("jdbc:mysql://%s:%s/%s?",
                config.getHost(), config.getPort(), config.getDatabase());

        // 由于TCP/IP的结构，Socket没有办法检测到网络错误，因此应用不能检测到与数据库到连接断开了。
        // 如果没有设置 Socket 超时，应用程序会一直等待数据库返回结果。（即死连接）
        url += UrlQuery.of(config.getJdbc(), null)
                .add("connectTimeout", 3000)
                .add("socketTimeout", 5000)
                .toString();

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(url, config.getUsername(), config.getPassword());
            stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery(sql);
            rs.close();
        } catch (Exception e) {
            return false;
        } finally {
            closeConnection(conn, stmt);
        }
        return true;
    }

}
