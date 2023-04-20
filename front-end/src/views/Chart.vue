<template>
  <div>
    <div class="top-element">
      <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
      <el-checkbox-group class="checkbox" v-model="checkedTypes" @change="handleCheckedTypesChange">
        <el-checkbox v-for="item in chartTypeList" :label="item.type">{{ item.name }}</el-checkbox>
      </el-checkbox-group>
    </div>

    <el-row class="chart-row" type="flex" justify="center" :gutter="50" v-loading="chartListLoad">
      <el-card class="card-create" shadow="hover">
        <el-button class="button-create" @click="handleCreate" circle>+</el-button>
      </el-card>
      <el-card class="card-chart" :body-style="{ padding: '10px' }" shadow="always"
               v-for="(chart, index) in chartList" :key="index">
        <div id="chart-container">
          <div class="chart-buttons">
            <el-tooltip content="详情" placement="top">
              <el-button icon="el-icon-view" circle @click="viewChart(index)"></el-button>
            </el-tooltip>
            <el-tooltip content="编辑" placement="top">
              <el-button icon="el-icon-edit" circle @click="editChart(index)"></el-button>
            </el-tooltip>
            <el-tooltip content="下载" placement="top">
              <el-button icon="el-icon-download" circle @click="downloadChart(index, chart.name)"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button icon="el-icon-delete" circle @click="handleRemove(chart.id)"></el-button>
            </el-tooltip>
          </div>
          <div class="chart-main" :id="`chart${index}`"></div>
        </div>
        <div class="chart-title">{{ chart.name }}</div>
      </el-card>
    </el-row>

    <el-dialog title="新建图表" width="35%" :visible.sync="createDialogVisible">
      <el-steps :space="5000" align-center :active="step">
        <el-step title="步骤 1"></el-step>
        <el-step title="步骤 2"></el-step>
        <el-step title="步骤 3"></el-step>
      </el-steps>

      <el-form class="create-dialog-form-1" label-width="100px" v-loading="createDialogLoad"
               ref="chartForm" :model="chartForm" :rules="chartFormRule">
        <el-form-item label="图表类型" prop="type" v-if="step===0">
          <el-select v-model="chartForm.type" placeholder="请选择图表类型">
            <el-option v-for="item in chartTypeList" :label="item.name" :value="item.type"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据源" prop="datasourceId" v-if="step===0">
          <el-select v-model="chartForm.datasourceId" placeholder="请选择数据源">
            <el-option v-for="item in datasourceList" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据表" prop="tableName" v-if="step===1">
          <el-select v-model="chartForm.tableName" placeholder="请选择数据表">
            <el-option v-for="name in tableList" :label="name" :value="name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图表名称" prop="name" v-if="step===2">
          <el-input style="width: 90%" v-model="chartForm.name" placeholder="请输入图表名称（20字符以内）"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description" v-if="step===2">
          <el-input style="width: 90%" v-model="chartForm.description" type="textarea"></el-input>
        </el-form-item>
      </el-form>

      <!-- 柱状图、折线图、散点图 -->
      <el-form class="create-dialog-form-2" label-width="100px"
               ref="barLineScatterForm" :model="barLineScatterForm" :rules="barLineScatterFormRule"
               v-if="step===2 && (chartForm.type==='bar' || chartForm.type==='line' || chartForm.type==='scatter')">
        <el-form-item label="横轴列" prop="axisX">
          <el-select v-model="barLineScatterForm.axisX" placeholder="请选择数据列">
            <el-option v-for="column in columnList" :label="column" :value="column"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="纵轴列" prop="columns">
          <el-select v-model="barLineScatterForm.columns" multiple placeholder="请选择一个或多个数据列">
            <el-option v-for="column in columnList" :label="column" :value="column"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图例" prop="needLegend">
          <el-radio v-model="barLineScatterForm.needLegend" label=true>开启</el-radio>
          <el-radio v-model="barLineScatterForm.needLegend" label=false>关闭</el-radio>
        </el-form-item>
      </el-form>

      <!-- 饼图、漏斗图 -->
      <el-form class="create-dialog-form-2" label-width="100px"
               ref="pieFunnelForm" :model="pieFunnelForm" :rules="pieFunnelFormRule"
               v-if="step===2 && (chartForm.type==='pie' || chartForm.type==='funnel')">
        <el-form-item label="类别列" prop="typeColumn">
          <el-select v-model="pieFunnelForm.typeColumn" placeholder="请选择类别列">
            <el-option v-for="column in columnList" :label="column" :value="column"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数值列" prop="valueColumn">
          <el-select v-model="pieFunnelForm.valueColumn" placeholder="请选择数值列">
            <el-option v-for="column in columnList" :label="column" :value="column"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图例" prop="needLegend">
          <el-radio v-model="pieFunnelForm.needLegend" label=true>开启</el-radio>
          <el-radio v-model="pieFunnelForm.needLegend" label=false>关闭</el-radio>
        </el-form-item>
      </el-form>

      <el-card class="card-create-preview" shadow="always" :body-style="{ padding: '0px' }"
               v-if="previewChartVisible && step===2">
        <div id="chart-preview-container">
          <div class="chart-preview" id="chart-preview"></div>
        </div>
      </el-card>

      <el-row class="create-dialog-button">
        <el-button @click="handlePrev" v-if="step===1 || step===2">上一步</el-button>
        <el-button @click="handleNextOne" type="primary" :loading="buttonLoad" v-if="step===0">下一步</el-button>
        <el-button @click="handleNextTwo" type="primary" :loading="buttonLoad" v-if="step===1">下一步</el-button>
        <el-button @click="handlePreview" type="primary" plain icon="el-icon-zoom-in" v-if="step===2">预览</el-button>
        <el-button @click="handleCreateSubmit" type="primary" v-if="step===2">创建</el-button>
      </el-row>
    </el-dialog>

    <el-dialog title="图表详情" width="40%" :visible.sync="detailDialogVisible">
      <el-descriptions size="small" :column="1" border>
        <el-descriptions-item label="ID">{{ chartList[detailIndex].id }}</el-descriptions-item>
        <el-descriptions-item label="名称">{{ chartList[detailIndex].name }}</el-descriptions-item>
        <el-descriptions-item label="描述">{{ chartList[detailIndex].description }}</el-descriptions-item>
        <el-descriptions-item label="数据源类型">{{ chartList[detailIndex].datasourceType}}</el-descriptions-item>
        <el-descriptions-item label="数据源名">{{ chartList[detailIndex].datasourceName }}</el-descriptions-item>
        <el-descriptions-item label="数据表名">{{ chartList[detailIndex].tableName }}</el-descriptions-item>
        <el-descriptions-item label="图表配置">{{ chartList[detailIndex].option }}</el-descriptions-item>
        <el-descriptions-item label="创建者">{{ chartList[detailIndex].creator }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ chartList[detailIndex].createTime }}</el-descriptions-item>
        <el-descriptions-item label="修改时间">{{ chartList[detailIndex].updateTime }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>

    <el-dialog title="编辑图表" width="35%" :visible.sync="modifyDialogVisible">
      
    </el-dialog>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import {listChartType, listChartView, previewChart, createChart, removeChart} from '@/api/chart'
import {listDs, listDsTable, listDsColumn} from '@/api/datasource'

export default {
  name: "Chart",
  data() {
    return {
      checkAll: true,
      checkedTypes: [],
      isIndeterminate: true,

      step: 0,
      buttonLoad: false,
      createDialogVisible: false,
      createDialogLoad: true,
      previewChartVisible: false,
      modifyDialogVisible: false,
      detailDialogVisible: false,
      detailIndex: 0,
      chartListLoad: true,

      chartForm: {
        name: "",
        type: "",
        description: "",
        datasourceId: "",
        tableName: "",
        config: {}
      },
      chartFormRule: {
        name: [
          {required: true, message: '请输入图表名称', trigger: 'blur'},
          {max: 20, message: '长度在20个字符以内', trigger: 'blur'}
        ],
        type: [{required: true, message: '请选择图表类型', trigger: 'change'}],
        datasourceId: [{required: true, message: '请选择数据源', trigger: 'change'}],
        tableName: [{required: true, message: '请选择数据表', trigger: 'change'}],
        description: [{max: 50, message: '描述长度应不超过50', trigger: 'blur'}],
      },
      barLineScatterForm: {
        axisX: "",
        columns: [],
        needLegend: false
      },
      pieFunnelForm: {
        typeColumn: "",
        valueColumn: "",
        needLegend: false
      },
      barLineScatterFormRule: {
        axisX: [{required: true, message: '请选择横轴', trigger: 'change'}],
        columns: [{required: true, message: '请至少选择一个作为纵轴', trigger: 'change'}],
        needLegend: [{required: true, message: '请选择是否需要图例', trigger: 'blur'}]
      },
      pieFunnelFormRule: {
        typeColumn: [{required: true, message: '请选择类别列', trigger: 'change'}],
        valueColumn: [{required: true, message: '请选择数值列', trigger: 'change'}],
        needLegend: [{required: true, message: '请选择是否需要图例', trigger: 'blur'}]
      },

      chartPreview: {},
      chartList: [
        {
          id: "",
          name: "",
          description: "",
          datasourceName: "",
          datasourceType: "",
          tableName: "",
          option: {},
          creator: "",
          createTime: "",
          updateTime: ""
        }
      ],
      chartTypeList: [],
      datasourceList: [],
      tableList: [],
      columnList: []
    }
  },
  created() {
    this.loadChartAll()
  },
  mounted() {
    window.onresize = () => {
      this.resizeChart()
      this.resizeChartPreview()
    }
  },
  methods: {
    loadChartAll() {
      this.chartListLoad = true
      listChartType().then(res => {
        if (res.success) {
          this.chartTypeList = []
          res.data.forEach(item => {
            this.chartTypeList.push({type: item.type, name: item.name})
          })
          this.checkedTypes = this.chartTypeList.map(item => item.type)
          this.loadChartList()
        } else {
          this.$message.error("图表类型获取失败：" + res.errMessage)
        }
      }).catch((err) => {
        this.$message.error("系统繁忙！")
        console.error(err)
      })
    },
    loadChartList() {
      this.chartListLoad = true
      listChartView(this.checkedTypes).then(res => {
        if (res.success) {
          this.clearChart()
          this.chartList = res.data
          if (this.chartList.length > 0) {
            this.$nextTick(() => {
              const chartContainerStyle = window.getComputedStyle(document.getElementById("chart-container"))
              this.chartList.forEach((val, index) => {
                const chartDom = document.getElementById(`chart${index}`)
                chartDom.style.width = chartContainerStyle.width
                chartDom.style.height = chartContainerStyle.height
                const myChart = echarts.init(chartDom)
                if (this.chartList[index].option) {
                  myChart.setOption(this.chartList[index].option)
                }
              })
            })
          }
          this.chartListLoad = false
        } else {
          this.$message.error("查询失败：" + res.errMessage)
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
    resizeChartPreview() {
      const chartPreviewContainer = document.getElementById("chart-preview-container")
      if (chartPreviewContainer) {
        const chartPreviewContainerStyle = window.getComputedStyle(chartPreviewContainer)
        const chartPreviewDom = document.getElementById("chart-preview")
        chartPreviewDom.style.width = chartPreviewContainerStyle.width
        chartPreviewDom.style.height = chartPreviewContainerStyle.height
        const chartPreviewInstance = echarts.getInstanceByDom(chartPreviewDom)
        if (chartPreviewInstance) {
          chartPreviewInstance.resize()
        }
      }
    },
    clearChart() {
      this.chartList.forEach((val, index) => {
        const chartDom = document.getElementById(`chart${index}`)
        if (chartDom) {
          const chartInstance = echarts.getInstanceByDom(chartDom)
          if (chartInstance) {
            chartInstance.dispose()
          }
        }
      })
    },

    // CheckBox
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

    // Button
    viewChart(index) {
      this.detailIndex = index
      this.detailDialogVisible = true
    },
    editChart(index) {
      this.modifyDialogVisible = true
    },
    downloadChart(index, name) {
      const chart = echarts.getInstanceByDom(document.getElementById(`chart${index}`))
      const baseImage = chart.getDataURL({
        type: 'png',
        pixelRatio: 2,
        backgroundColor: '#fff'
      });
      const a = document.createElement('a');
      a.download = name;
      a.style.display = 'none';
      a.href = baseImage;
      document.body.appendChild(a);
      a.click();
      URL.revokeObjectURL(baseImage);
      document.body.removeChild(a)
    },
    handleRemove(id) {
      this.$confirm('此操作将永久删除该图表，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(id)
        removeChart(id).then(res => {
          if (res.success) {
            this.$message.success("删除成功！")
            this.loadChartAll()
          } else {
            this.$message.error("删除失败：" + res.errMessage)
          }
        }).catch((err) => {
          this.$message.error("系统繁忙！")
          console.error(err)
        })
      }).catch(() => {
        this.$message({type: 'info', message: '已取消删除'})
      })
    },

    // Dialog
    handleCreate() {
      this.clearPreview()
      this.step = 0
      this.chartForm = {}
      this.createDialogLoad = true
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
      if (this.step === 1) {
        this.chartForm.tableName = ""
      }
      if (this.step === 2) {
        this.clearPreview()
      }
      if (this.step !== 0) {
        this.step--
      }
    },
    handleNextOne() {
      this.$refs['chartForm'].validate((valid) => {
        if (valid) {
          this.buttonLoad = true
          listDsTable(this.chartForm.datasourceId).then(res => {
            if (res.success) {
              this.tableList = res.data
              this.step = 1
            } else {
              this.$message.error(res.errMessage)
            }
          }).catch((err) => {
            this.$message.error("系统繁忙！")
            console.error(err)
          })
          this.buttonLoad = false
        }
      })
    },
    handleNextTwo() {
      this.clearForm()
      this.clearPreview()
      this.$refs['chartForm'].validate((valid) => {
        if (valid) {
          this.buttonLoad = true
          listDsColumn({id: this.chartForm.datasourceId, tableName: this.chartForm.tableName})
              .then(res => {
                if (res.success) {
                  this.columnList = res.data
                  this.step = 2
                } else {
                  this.$message.error(res.errMessage)
                }
              }).catch((err) => {
            this.$message.error("系统繁忙！")
            console.error(err)
          })
          this.buttonLoad = false
        }
      })
    },
    handlePreview() {
      this.clearPreview()
      this.loadAndValidateConfig((isValid) => {
        if (isValid) {
          previewChart(this.chartForm).then(res => {
            if (res.success) {
              this.previewChartVisible = true
              this.$nextTick(() => {
                echarts.init(document.getElementById("chart-preview")).setOption(res.data.option)
                this.resizeChartPreview()
              })
            } else {
              this.$message.error(res.errMessage)
            }
          }).catch((err) => {
            this.$message.error("系统繁忙！")
            console.error(err)
          })
        }
      })
    },
    handleCreateSubmit() {
      this.$refs['chartForm'].validate((valid) => {
        if (valid) {
          this.buttonLoad = true
          this.loadAndValidateConfig((isValid) => {
            if (isValid) {
              createChart(this.chartForm).then(res => {
                if (res.success) {
                  this.createDialogVisible = false
                  this.$message.success("保存成功！")
                  this.loadChartAll()
                } else {
                  this.$message.error(res.errMessage)
                }
              }).catch((err) => {
                this.$message.error("系统繁忙！")
                console.error(err)
              })
            }
          })
          this.chartForm.config = {}
          this.buttonLoad = false
        }
      })
    },
    clearForm() {
      if (this.chartForm.type === "bar" || this.chartForm.type === "line" || this.chartForm.type === "scatter") {
        this.barLineScatterForm = {}
      }
      if (this.chartForm.type === "pie" || this.chartForm.type === "funnel") {
        this.pieFunnelForm = {}
      }
    },
    clearPreview() {
      this.previewChartVisible = false
      const chartDom = document.getElementById("chart-preview")
      if (chartDom) {
        const chartInstance = echarts.getInstanceByDom(chartDom)
        if (chartInstance) {
          chartInstance.dispose()
        }
      }
    },
    loadAndValidateConfig(callback) {
      if (this.chartForm.type === "bar" || this.chartForm.type === "line" || this.chartForm.type === "scatter") {
        this.chartForm.config = this.barLineScatterForm
        this.$refs['barLineScatterForm'].validate((valid) => {
          callback(valid.valueOf())
        })
      } else if (this.chartForm.type === 'pie' || this.chartForm.type === 'funnel') {
        this.chartForm.config = this.pieFunnelForm
        this.$refs['pieFunnelForm'].validate((valid) => {
          callback(valid.valueOf())
        })
      }
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
  position: relative;
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
}

.chart-main {
  width: 300px;
  height: 300px;
  margin: 20px auto 0
}

.chart-buttons {
  position: absolute;
  top: 10px;
  right: 10px
}

.card-create-preview {
  width: 95%;
  height: 50%;
  margin: 0 auto 10% auto
}

#chart-preview-container {
  width: 100%;
  height: 100%;
  margin: 0 auto
}

.chart-preview {
  width: 300px;
  height: 300px;
  margin: 0 auto
}

.chart-title {
  position: relative;
  top: -100%;
  transform: translateY(-50%);
  text-align: center
}

.create-dialog-form-1 {
  margin-top: 30px
}

.create-dialog-form-2 {
  margin-top: 20px;
  margin-bottom: 40px;
}

.create-dialog-button {
  position: absolute;
  right: 20px;
  bottom: 20px
}
</style>