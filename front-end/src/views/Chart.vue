<template>
  <div>
    <el-row class="chart-row" type="flex" justify="center" :gutter="50">
      <el-col class="chart-col" v-for="(chart, index) in chartList" :key="index">
        <el-card class="chart-card" :body-style="{ padding: '0px' }" shadow="hover">
          <div id="chart-container">
            <div class="chart-preview" :id="`chart${index}`"></div>
          </div>
          <div class="chart-title">{{ chart.name }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "Chart",
  data() {
    return {
      chartList: [
        {
          "name": "测试",
          "option": {
            "xAxis": {
              "type": "category",
              "data": ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
            },
            "yAxis": {
              "type": "value"
            },
            "series": [
              {
                "data": [150, 230, 224, 218, 135, 147, 260],
                "type": "line"
              }
            ]
          }
        },
        {
          "name": "测试2",
          "option": {
            "xAxis": {
              "type": "category",
              "data": ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
            },
            "yAxis": {
              "type": "value"
            },
            "series": [
              {
                "data": [150, 230, 224, 218, 535, 107, 260],
                "type": "line"
              }
            ]
          }
        },
        {
          "name": "测试3",
          "option": {
            "title": {
              "text": 'World Population'
            },
            "tooltip": {
              "trigger": 'axis',
              "axisPointer": {
                "type": 'shadow'
              }
            },
            "legend": {},
            "grid": {
              "left": '3%',
              "right": '4%',
              "bottom": '3%',
              "containLabel": true
            },
            "xAxis": {
              "type": 'value',
              "boundaryGap": [0, 0.01]
            },
            "yAxis": {
              "type": 'category',
              "data": ['Brazil', 'Indonesia', 'USA', 'India', 'China', 'World']
            },
            "series": [
              {
                "name": '2011',
                "type": 'bar',
                "data": [18203, 23489, 29034, 104970, 131744, 630230]
              },
              {
                "name": '2012',
                "type": 'bar',
                "data": [19325, 23438, 31000, 121594, 134141, 681807]
              }
            ]
          }
        },
        {
          "name": "测试4",
          "option": {
            "tooltip": {
              "trigger": 'item'
            },
            "legend": {
              "top": '5%',
              "left": 'center'
            },
            "series": [
              {
                "name": 'Access From',
                "type": 'pie',
                "radius": ['40%', '70%'],
                "avoidLabelOverlap": false,
                "itemStyle": {
                  "borderRadius": 10,
                  "borderColor": '#fff',
                  "borderWidth": 2
                },
                "label": {
                  "show": false,
                  "position": 'center'
                },
                "emphasis": {
                  "label": {
                    "show": true,
                    "fontSize": 40,
                    "fontWeight": 'bold'
                  }
                },
                "labelLine": {
                  "show": false
                },
                "data": [
                  {"value": 1048, "name": 'Search Engine'},
                  {"value": 735, "name": 'Direct'},
                  {"value": 580, "name": 'Email'},
                  {"value": 484, "name": 'Union Ads'},
                  {"value": 300, "name": 'Video Ads'}
                ]
              }
            ]
          }
        },
        {
          "name": "测试5",
          "option": {
            "xAxis": {},
            "yAxis": {},
            "series": [
              {
                "symbolSize": 20,
                "data": [
                  [10.0, 8.04],
                  [8.07, 6.95],
                  [13.0, 7.58],
                  [9.05, 8.81],
                  [11.0, 8.33],
                  [14.0, 7.66],
                  [13.4, 6.81],
                  [10.0, 6.33],
                  [14.0, 8.96],
                  [12.5, 6.82],
                  [9.15, 7.2],
                  [11.5, 7.2],
                  [3.03, 4.23],
                  [12.2, 7.83],
                  [2.02, 4.47],
                  [1.05, 3.33],
                  [4.05, 4.96],
                  [6.03, 7.24],
                  [12.0, 6.26],
                  [12.0, 8.84],
                  [7.08, 5.82],
                  [5.02, 5.68]
                ],
                "type": 'scatter'
              }
            ]
          }
        },
        {
          "name": "测试6",
          "option": {
            "title": {
              "text": 'Basic Radar Chart'
            },
            "legend": {
              "data": ['Allocated Budget', 'Actual Spending']
            },
            "radar": {
              // shape: 'circle',
              "indicator": [
                { "name": 'Sales', "max": 6500 },
                { "name": 'Administration', "max": 16000 },
                { "name": 'Information Technology', "max": 30000 },
                { "name": 'Customer Support', "max": 38000 },
                { "name": 'Development', "max": 52000 },
                { "name": 'Marketing', "max": 25000 }
              ]
            },
            "series": [
              {
                "name": 'Budget vs spending',
                "type": 'radar',
                "data": [
                  {
                    "value": [4200, 3000, 20000, 35000, 50000, 18000],
                    "name": 'Allocated Budget'
                  },
                  {
                    "value": [5000, 14000, 28000, 26000, 42000, 21000],
                    "name": 'Actual Spending'
                  }
                ]
              }
            ]
          }
        }
      ]
    }
  },
  created() {
    // TODO: Get ChartList
    if (this.chartList.length > 0) {
      this.$nextTick(() => {
        this.initChartList()
        this.resizeChart()
      })
    }
  },
  mounted() {
    window.onresize = () => {
      this.resizeChart()
    }
  },
  methods: {
    initChartList() {
      this.chartList.forEach((val, index) => {
        const myChart = echarts.init(document.getElementById(`chart${index}`))
        myChart.setOption(this.chartList[index].option)
      })
    },
    resizeChart() {
      // 获取Chart容器自适应后的宽高，为Chart的宽高赋值，实现图表随浏览器窗口大小自适应
      const chartContainerStyle = window.getComputedStyle(document.getElementById("chart-container"))
      this.chartList.forEach((val, index) => {
        const chartDom = document.getElementById(`chart${index}`)
        chartDom.style.width = chartContainerStyle.width
        chartDom.style.height = chartContainerStyle.height
        // 修改样式后，需重新绘制图表
        echarts.getInstanceByDom(chartDom).resize()
      })
    }
  }
}
</script>

<style>
.chart-row {
  /* 自动换行 */
  flex-wrap: wrap
}

.chart-col {
  margin-bottom: 30px;
  width: 30%
}

.chart-card {
  /* 防止Card中内容不一致导致高度不同 */
  height: 100%;
  margin: 8px
}

#chart-container {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  /* background-color: #cccccc */
}

.chart-preview {
  width: 300px;
  height: 300px;
  margin: 0 auto
}

.chart-title {
  text-align: center
}
</style>