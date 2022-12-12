package pers.nek0peko.datas.controller.datasource;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.service.DatasourceServiceI;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 数据源管理
 *
 * @author nek0peko
 * @date 2022/12/12
 */
@Api(tags = "数据源管理")
@ApiSort(0)
@RestController
@RequestMapping("datasource")
@Validated
public class DatasourceController {

    @Resource
    private transient DatasourceServiceI datasourceService;

    @ApiOperationSupport(author = "nek0peko", order = 1)
    @ApiOperation(value = "新增数据源")
    @PostMapping(value = "/create")
    public Response create(@Valid @NotNull @RequestBody DatasourceCreateCmd cmd) {
        return datasourceService.create(cmd);
    }

}