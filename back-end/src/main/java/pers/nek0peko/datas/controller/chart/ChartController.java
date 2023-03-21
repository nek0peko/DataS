package pers.nek0peko.datas.controller.chart;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.nek0peko.datas.dto.data.chart.ChartViewDTO;
import pers.nek0peko.datas.dto.data.chart.option.BarOptionDTO;
import pers.nek0peko.datas.dto.response.SingleResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据可视化
 *
 * @author nek0peko
 * @date 2023/03/21
 */
@Api(tags = "数据可视化")
@ApiSort(1)
@RestController
@RequestMapping("chart")
@Validated
public class ChartController {

    @ApiOperationSupport(author = "nek0peko", order = 1)
    @ApiOperation(value = "测试")
    @PostMapping(value = "/list")
    public SingleResponse<List<ChartViewDTO>> list() {
        List<ChartViewDTO> chartViewDtos = new ArrayList<>();
        chartViewDtos.add(ChartViewDTO.builder()
                .id(Long.parseLong("11111111111"))
                .name("TestTestTest")
                .option(BarOptionDTO.builder()
                        .axisX(BarOptionDTO.AxisX.builder()
                                .data(Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"))
                                .build())
                        .axisY(BarOptionDTO.AxisY.builder().build())
                        .series(BarOptionDTO.Series.builder()
                                .type("bar")
                                .data(Arrays.asList(23, 24, 18, 25, 27, 28, 25))
                                .build())
                        .build())
                .build());
        return SingleResponse.of(chartViewDtos);
    }

}
