<template>
  <div>
    <div class="top-element">
      <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
      <el-checkbox-group class="checkbox" v-model="checkedTypes" @change="handleCheckedTypesChange">
        <el-checkbox v-for="item in chartTypeList" :label="item.type">{{ item.name }}</el-checkbox>
      </el-checkbox-group>
    </div>

    <el-row class="chart-row" type="flex" justify="center" :gutter="50" v-loading="chartLoad">
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

    <el-dialog title="新建图表" width="40%" :visible.sync="createDialogVisible">

      <el-steps space="50%" align-center :active="createDialogActive">
        <el-step title="步骤 1"></el-step>
        <el-step title="步骤 2"></el-step>
        <el-step title="步骤 3"></el-step>
      </el-steps>

      <el-form class="create-dialog-form" label-width="100px" v-loading="createDialogLoad"
               ref="createForm" :model="createForm" :rules="createDialogRule">

        <el-form-item label="图表类型" prop="type" v-if="createDialogActive===0">
          <el-select v-model="createForm.type" placeholder="请选择图表类型">
            <el-option v-for="item in chartTypeList" :label="item.name" :value="item.type">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据源" prop="datasourceId" v-if="createDialogActive===0">
          <el-select v-model="createForm.datasourceId" placeholder="请选择数据源">
            <el-option v-for="item in datasourceList" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="数据表" prop="tableName" v-if="createDialogActive===1">
          <el-select v-model="createForm.tableName" placeholder="请选择数据表">
            <el-option v-for="name in tableList" :label="name" :value="name">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="横轴列" prop="xAxis"
                      v-if="createDialogActive===2 && (createForm.type==='bar' || createForm.type==='line')">
          <el-select v-model="barLineForm.xAxis" placeholder="请选择数据列">
            <el-option v-for="column in columnList" :label="column" :value="column">
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>

      <el-row class="create-dialog-button">
        <el-button @click="handlePrev" v-if="createDialogActive===1 || createDialogActive===2">上一步</el-button>
        <el-button @click="handleNextOne" type="primary" v-if="createDialogActive===0">下一步</el-button>
        <el-button @click="handleNextTwo" type="primary" v-if="createDialogActive===1">下一步</el-button>
        <el-button @click="handleCreateSubmit" type="primary" v-if="createDialogActive===2">提交</el-button>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import {listChartType, listChartView} from '@/api/chart'
import {listDs, listDsTable} from '@/api/datasource'

export default {
  name: "Chart",
  data() {
    return {
      checkAll: true,
      checkedTypes: [],
      isIndeterminate: true,

      createDialogVisible: false,
      createDialogActive: 0,
      createDialogLoad: true,
      createForm: {
        name: "",
        type: "",
        description: "",
        datasourceId: "",
        tableName: "",
        config: {}
      },
      createDialogRule: {
        name: [
          {required: true, message: '请输入图表名称', trigger: 'blur'},
          {max: 20, message: '长度在20个字符以内', trigger: 'blur'}
        ],
        type: [{required: true, message: '请选择图表类型', trigger: 'change'}],
        datasourceId: [{required: true, message: '请选择数据源', trigger: 'change'}],
        tableName: [{required: true, message: '请选择数据表', trigger: 'change'}],
        description: [{max: 50, message: '描述长度应不超过50', trigger: 'blur'}],
      },
      barLineForm: {
        axisX: "",
        columns: []
      },

      chartLoad: true,
      chartList: [],
      chartTypeList: [],
      datasourceList: [],
      tableList: [],
      columnList: []
    }
  },
  created() {
    this.loadChartType()
  },
  mounted() {
    window.onresize = () => {
      this.resizeChart()
    }
  },
  methods: {
    loadChartList() {
      this.chartLoad = true
      listChartView(this.checkedTypes).then(res => {
        if (res.success) {
          this.chartList = res.data
          // this.forTest()
          if (this.chartList.length > 0) {
            this.$nextTick(() => {
              this.chartList.forEach((val, index) => {
                const myChart = echarts.init(document.getElementById(`chart${index}`))
                if (this.chartList[index].option) {
                  myChart.setOption(this.chartList[index].option)
                }
              })
              this.resizeChart()
            })
          }
          this.chartLoad = false
        } else {
          this.$message.error("查询失败：" + res.errMessage)
        }
      }).catch((err) => {
        this.$message.error("系统繁忙！")
        console.error(err)
      })
    },
    loadChartType() {
      this.chartLoad = true
      listChartType().then(res => {
        if (res.success) {
          this.chartTypeList = []
          res.data.forEach(item => {
            this.chartTypeList.push({
              type: item.type,
              name: item.name
            })
          })
          this.checkedTypes = this.chartTypeList.map(item => item.type)
          this.loadChartList()
        } else {
          this.$message.error("图表类型加载失败：" + res.errMessage)
        }
      }).catch((err) => {
        this.$message.error("系统繁忙！")
        console.error(err)
      })
    },
    resizeChart() {
      // 获取Chart容器自适应后的宽高，为Chart的宽高赋值，实现图表随浏览器窗口大小自适应
      const chartContainer = document.getElementById("chart-container")
      if (chartContainer) {
        const chartContainerStyle = window.getComputedStyle(chartContainer)
        this.chartList.forEach((val, index) => {
          const chartDom = document.getElementById(`chart${index}`)
          chartDom.style.width = chartContainerStyle.width
          chartDom.style.height = chartContainerStyle.height
          // 修改样式后，需重新绘制图表
          const chartInstance = echarts.getInstanceByDom(chartDom)
          if (chartInstance) {
            chartInstance.resize()
          }
        })
      }
    },

    handleCheckAllChange(val) {
      this.checkedTypes = val ? this.chartTypeList.map(item => item.type) : []
      this.isIndeterminate = false
      this.loadChartList()
    },
    handleCheckedTypesChange() {
      let checkedCount = this.checkedTypes.length
      this.checkAll = checkedCount === this.chartTypeList.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.chartTypeList.length
      this.loadChartList()
    },

    handleCreate() {
      this.createDialogLoad = true
      this.createDialogActive = 0
      this.createForm = {}
      this.createDialogVisible = true

      this.datasourceList = []
      this.tableList = []
      listDs().then(res => {
        if (res.success) {
          res.data.forEach(item => {
            this.datasourceList.push({
              id: item.id,
              name: item.name
            })
          })
          this.createDialogLoad = false
        } else {
          this.$message.error("查询失败：" + res.errMessage)
        }
      }).catch((err) => {
        this.$message.error("系统繁忙！")
        console.error(err)
      })
    },
    handlePrev() {
      if (this.createDialogActive === 1) {
        this.createForm.tableName = ""
      }
      if (this.createDialogActive !== 0) {
        this.createDialogActive--
      }
    },
    handleNextOne() {
      this.$refs['createForm'].validate((valid) => {
        if (valid) {
          this.createDialogLoad = true
          listDsTable(this.createForm.datasourceId).then(res => {
            if (res.success) {
              this.tableList = res.data
              this.createDialogActive = 1
            } else {
              this.$message.error(res.errMessage)
            }
          }).catch((err) => {
            this.$message.error("系统繁忙！")
            console.error(err)
          })
          this.createDialogLoad = false
        }
      })
    },
    handleNextTwo() {
      this.$refs['createForm'].validate((valid) => {
        if (valid) {
          this.createDialogActive = 2
        }
      })
    },
    handleCreateSubmit() {
      this.createDialogVisible = false
      // TODO: submit
    },

    forTest() {
      this.chartList = [
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
  }
}
</script>

<style>
.top-element {
  margin-top: 5px;
  margin-bottom: 15px;
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
  margin: 15px 20px;
  display: flex;
  justify-content: center;
  align-items: center
}

.card-chart {
  width: 500px;
  height: 350px;
  margin: 15px 20px
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

.create-dialog-form {
  margin-top: 40px;
  margin-bottom: 40px
}

.create-dialog-button {
  position: absolute;
  right: 20px;
  bottom: 20px
}
</style>