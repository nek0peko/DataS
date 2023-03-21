package pers.nek0peko.datas.command;

import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.data.datasource.DatasourceTypeEnum;
import pers.nek0peko.datas.dto.response.SingleResponse;

import java.util.List;

/**
 * 查询数据源类型列表
 *
 * @author nek0peko
 * @date 2022/12/13
 */
@Component
public class DatasourceListTypeQryExe {

    public SingleResponse<List<String>> execute() {
        return SingleResponse.of(DatasourceTypeEnum.listType());
    }

}
