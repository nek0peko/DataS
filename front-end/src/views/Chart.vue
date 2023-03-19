<template>
  <div>
    <div class="top-element">
      <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
      <el-checkbox-group class="checkbox" v-model="checkedTypes" @change="handleCheckedTypesChange">
        <el-checkbox v-for="type in chartTypeList" :label="type" :key="type">{{ type }}</el-checkbox>
      </el-checkbox-group>
    </div>

    <el-row class="chart-row" type="flex" justify="center" :gutter="50">
      <el-card class="card-create" shadow="hover">
        <el-button class="button-create" @click="handleCreate" circle>+</el-button>
      </el-card>
      <el-card class="card-chart" :body-style="{ padding: '10px' }" shadow="always"
               v-for="(chart, index) in chartList" :key="index">
        <div id="chart-container">
          <div class="chart-preview" :id="`chart${index}`"></div>
        </div>
        <div class="chart-title">{{ chart.name }}</div>
      </el-card>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "Chart",
  data() {
    return {
      checkAll: true,
      checkedTypes: [],
      isIndeterminate: true,
      chartList: [],
      chartTypeList: []
    }
  },
  created() {
    this.loadChartList()
    this.loadChartType()
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
    loadChartList() {
      // TODO: Get ChartList 时间倒序排列
      this.chartList = [
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
                {"name": 'Sales', "max": 6500},
                {"name": 'Administration', "max": 16000},
                {"name": 'Information Technology', "max": 30000},
                {"name": 'Customer Support', "max": 38000},
                {"name": 'Development', "max": 52000},
                {"name": 'Marketing', "max": 25000}
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
        },
      ]
    },
    loadChartType() {
      // TODO: Get ChartType
      this.chartTypeList = ["柱状图", "折线图", "饼图", "散点图"]
      this.checkedTypes = this.chartTypeList
    },
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
    },
    handleCheckAllChange(val) {
      this.checkedTypes = val ? this.chartTypeList : []
      this.isIndeterminate = false
    },
    handleCheckedTypesChange(val) {
      let checkedCount = val.length
      this.checkAll = checkedCount === this.chartTypeList.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.chartTypeList.length
      // TODO: reload chart list
    },
    handleCreate() {
      // TODO: 打开dialog
    }
  }
}
</script>

<style>
.top-element {
  margin-top: 10px;
  margin-bottom: 20px;
  text-align: center
}

.checkbox {
  margin-left: 30px;
  display: inline
}

.chart-row {
  /* 自动换行 */
  flex-wrap: wrap
}

.card-create {
  width: 500px;
  height: 350px;
  margin: 20px 20px;
  display: flex;
  justify-content: center;
  align-items: center
}

.card-chart {
  width: 500px;
  height: 350px;
  margin: 20px 20px
}

.button-create {
  width: 300px;
  height: 300px;
  font-size: 200px;
  color: #cccccc;
  border: none;
}

#chart-container {
  width: 100%;
  height: 100%;
  margin: 0 auto
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