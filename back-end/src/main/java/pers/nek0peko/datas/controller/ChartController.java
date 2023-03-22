package pers.nek0peko.datas.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;
import pers.nek0peko.datas.service.ChartServiceI;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 数据可视化
 *
 * @author nek0peko
 * @date 2023/03/22
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
    public SingleResponse<List<ChartViewDTO>> listView(@Valid @NotNull @RequestBody List<String> types) {
        return chartService.listView(types);
    }

}
