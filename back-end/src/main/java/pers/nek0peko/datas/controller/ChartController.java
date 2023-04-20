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
import pers.nek0peko.datas.dto.command.ChartCreateCmd;
import pers.nek0peko.datas.dto.command.ChartModifyCmd;
import pers.nek0peko.datas.dto.command.ChartPreviewCmd;
import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.service.ChartServiceI;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 数据可视化
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@Api(tags = "数据可视化")
@ApiSort(1)
@RestController
@RequestMapping("chart")
@Validated
public class ChartController {

    @Resource
    private transient ChartServiceI chartService;

    @ApiOperationSupport(author = "nek0peko", order = 1)
    @ApiOperation(value = "查询图表类型列表")
    @PostMapping(value = "/list-type")
    public SingleResponse<List<Map<String, String>>> listType() {
        return chartService.listType();
    }

    @ApiOperationSupport(author = "nek0peko", order = 2)
    @ApiOperation(value = "获取图表绘制信息")
    @PostMapping(value = "/list-view")
    public SingleResponse<List<ChartViewDTO>> listView(@Valid @RequestBody List<String> types) {
        return chartService.listView(types);
    }

    @ApiOperationSupport(author = "nek0peko", order = 3)
    @ApiOperation(value = "预览图表")
    @PostMapping(value = "/preview")
    public SingleResponse<ChartViewDTO> preview(@Valid @NotNull @RequestBody ChartPreviewCmd cmd) {
        return chartService.preview(cmd);
    }

    @ApiOperationSupport(author = "nek0peko", order = 4)
    @ApiOperation(value = "新增图表")
    @PostMapping(value = "/create")
    public Response create(@Valid @NotNull @RequestBody ChartCreateCmd cmd) {
        return chartService.create(cmd);
    }

    @ApiOperationSupport(author = "nek0peko", order = 5)
    @ApiOperation(value = "修改图表")
    @PostMapping(value = "/modify")
    public Response modify(@Valid @NotNull @RequestBody ChartModifyCmd cmd) {
        return chartService.modify(cmd);
    }

    @ApiOperationSupport(author = "nek0peko", order = 6)
    @ApiOperation(value = "删除图表")
    @ApiImplicitParam(name = "id", value = "图表ID", required = true, dataType = "Long", dataTypeClass = Long.class)
    @PostMapping(value = "/remove")
    public Response remove(@Valid @NotNull @RequestBody Long id) {
        return chartService.remove(id);
    }

}
