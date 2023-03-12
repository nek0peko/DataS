<template>
  <div>
    <el-button type="success" draggable="true" @dragstart.native="dragStart($event,'test_histogram')">
      柱状图
    </el-button>
    <div @drop.prevent="drop($event)" @dragover.prevent="">
      <div style="margin-top: 20px; height: 300px; width: 400px" id='histogram'></div>
    </div>
  </div>

</template>

<script>
import * as echarts from 'echarts'

export default {
  data() {
    return {}
  },
  created() {
  },
  methods: {
    dragStart(event, type) {
      event.dataTransfer.setData("Type", type);
    },
    drop(event) {
      if (event.dataTransfer.getData("Type") === 'test_histogram') {
        const myChart = echarts.init(document.getElementById('histogram'));
        const option = {
          title: {
            text: 'ECharts 测试'
          },
          legend: {
            data: ['数量']
          },
          xAxis: {
            data: ["a", "b", "c", "d", "e", "f"]
          },
          yAxis: {},
          series: [{
            name: '数量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
          }]
        };
        myChart.setOption(option);
      }
    }
  }
}
</script>

<style>

</style>