package pers.nek0peko.datas.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.nek0peko.datas.dto.command.DatasourceCreateCmd;
import pers.nek0peko.datas.dto.command.DatasourceListColumnQry;
import pers.nek0peko.datas.dto.command.DatasourceListQry;
import pers.nek0peko.datas.dto.command.DatasourceModifyCmd;
import pers.nek0peko.datas.dto.data.datasource.DatasourceDTO;
import pers.nek0peko.datas.dto.response.PageResponse;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.service.DatasourceServiceI;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * 数据源管理
 *
 * @author nek0peko
 * @date 2023/03/22
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

    @ApiOperationSupport(author = "nek0peko", order = 2)
    @ApiOperation(value = "修改数据源")
    @PostMapping(value = "/modify")
    public Response modify(@Valid @NotNull @RequestBody DatasourceModifyCmd cmd) {
        return datasourceService.modify(cmd);
    }

    @ApiOperationSupport(author = "nek0peko", order = 3)
    @ApiOperation(value = "删除数据源")
    @ApiImplicitParam(name = "ids", value = "数据源ID列表", required = true, allowMultiple = true, dataType = "Long", dataTypeClass = Long.class)
    @PostMapping(value = "/remove")
    public Response remove(@Valid @NotEmpty @RequestBody List<Long> ids) {
        return datasourceService.remove(ids);
    }

    @ApiOperationSupport(author = "nek0peko", order = 4)
    @ApiOperation(value = "查询单个数据源")
    @ApiImplicitParam(name = "id", value = "数据源ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    @PostMapping(value = "/view")
    public SingleResponse<DatasourceDTO> view(@Valid @NotNull @RequestBody Long id) {
        return datasourceService.view(id);
    }

    @ApiOperationSupport(author = "nek0peko", order = 5, ignoreParameters = {"qry.orderBy", "qry.orderDirection", "qry.orderDirection", "qry.groupBy"})
    @ApiOperation(value = "条件查询数据源列表")
    @PostMapping(value = "/list")
    public PageResponse<DatasourceDTO> list(@Valid @RequestBody(required = false) DatasourceListQry qry) {
        qry = Optional.ofNullable(qry).orElse(new DatasourceListQry());
        return datasourceService.list(qry);
    }

    @ApiOperationSupport(author = "nek0peko", order = 6)
    @ApiOperation(value = "查询数据源类型列表")
    @PostMapping(value = "/list-type")
    public SingleResponse<List<String>> listType() {
        return datasourceService.listType();
    }

    @ApiOperationSupport(author = "nek0peko", order = 7)
    @ApiOperation(value = "测试数据源连接")
    @ApiImplicitParam(name = "id", value = "数据源ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    @PostMapping(value = "/test-link")
    public SingleResponse<Boolean> testLink(@Valid @NotNull @RequestBody Long id) {
        return datasourceService.testLink(id);
    }

    @ApiOperationSupport(author = "nek0peko", order = 8)
    @ApiOperation(value = "查询数据源中所有表名")
    @PostMapping(value = "/list-table")
    public SingleResponse<List<String>> listTable(@Valid @NotNull @RequestBody Long id) {
        return datasourceService.listTable(id);
    }

    @ApiOperationSupport(author = "nek0peko", order = 9)
    @ApiOperation(value = "查询数据表中所有列名")
    @PostMapping(value = "/list-column")
    public SingleResponse<List<String>> listColumn(@Valid @NotNull @RequestBody DatasourceListColumnQry qry) {
        return datasourceService.listColumn(qry);
    }

}