<template>
  <div>
    <el-row class="chart-row" type="flex" justify="center" :gutter="50">
      <el-col class="chart-col" v-for="chart in chartList">
        <el-card class="chart-card" :body-style="{ padding: '0px' }" shadow="hover">
          <div>
            <div>
              <el-image :src="chart.img" class="chart"></el-image>
            </div>
            <div>
              {{ chart.name }}
            </div>
          </div>
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
          "img": "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
          "name": "老八秘制小汉堡"
        },
        {
          "img": "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
          "name": "老八秘制大汉堡"
        },
        {
          "img": "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
          "name": "老八秘制大汉堡"
        },
        {
          "img": "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
          "name": "老八秘制大汉堡"
        },
        {
          "img": "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
          "name": "老八秘制大汉堡"
        },
        {
          "img": "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
          "name": "老八秘制大汉堡"
        },
        {
          "img": "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
          "name": "老八秘制大汉堡"
        },
        {
          "img": "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
          "name": "老八秘制大汉堡"
        },
        {
          "img": "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
          "name": "老八秘制大汉堡"
        },
        {
          "img": "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png",
          "name": "老八秘制大汉堡"
        }
      ]
    }
  },
  created() {
  },
  methods: {}
}
</script>

<style>
.chart-row {
  /* 自动换行 */
  flex-wrap: wrap
}

.chart-col {
  margin-bottom: 20px;
  width: 30%
}

.chart-card {
  /* 防止Card中内容不一致导致高度不同 */
  height: 100%
}
</style>