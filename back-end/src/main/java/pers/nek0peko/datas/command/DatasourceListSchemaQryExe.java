package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.command.DatasourceListSchemaQry;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.factory.DatasourceDomainServiceFactory;
import pers.nek0peko.datas.service.domain.DatasourceSchemaDomainServiceI;

import java.util.List;

/**
 * 查询数据源中所有Schema
 *
 * @author nek0peko
 * @date 2023/04/18
 */
@Component
public class DatasourceListSchemaQryExe {


    public SingleResponse<List<String>> execute(DatasourceListSchemaQry qry) {
        final DatasourceSchemaDomainServiceI service = DatasourceDomainServiceFactory.getSchemaService(qry.getType());
        return SingleResponse.of(service.listSchema(qry.getConfig()));
    }

}
