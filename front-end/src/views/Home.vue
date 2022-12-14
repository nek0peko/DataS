<template>
  <div style="height: 100%">
    <el-container class="home-container">

      <el-aside class="sidebar" :width="isCollapsed?'64px':'220px'">
        <el-menu class="sidebar-menu" :default-openeds="['1', '3']" background-color="rgb(48, 65, 86)" text-color="#fff"
                 active-text-color="#0099ff" :collapse="isCollapsed" :collapse-transition="false">

          <!-- 侧边栏头部Logo和名称 -->
          <div class="sidebar-header">
            <img class="sidebar-logo" src="../assets/logo.png">
            <b class="sidebar-title" v-show="!isCollapsed">DataS</b>
          </div>

          <!-- 侧边栏菜单 -->
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i><span slot="title">数据可视化</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-setting"></i><span slot="title">数据源管理</span>
            </template>
          </el-submenu>
          <!--          <el-submenu index="3">-->
          <!--            <template slot="title">-->
          <!--              <i class="el-icon-menu"></i><span slot="title">导航三</span>-->
          <!--            </template>-->
          <!--          </el-submenu>-->

        </el-menu>
      </el-aside>

      <el-container>
        <el-header class="main-header">
          <!-- 折叠按钮图标 -->
          <div class="collapse-button">
            <span :class="isCollapsed?'el-icon-s-unfold':'el-icon-s-fold'" @click="collapse"></span>
          </div>
          <!-- 用户下拉菜单 -->
          <el-dropdown class="user-dropdown">
            <i class="el-icon-setting user-dropdown-icon"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>修改密码</el-dropdown-item>
              <el-dropdown-item>退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <!-- 用户名 -->
          <span class="user-name">Admin</span>
        </el-header>

        <el-main>
          <!-- 搜索 -->
          <div class="search">
            <el-input class="w-200" placeholder="请输入名称" suffix-icon="el-icon-search"></el-input>
            <el-input class="w-200 ml-10" placeholder="请输入邮箱" suffix-icon="el-icon-message"></el-input>
            <el-input class="w-200 ml-10" placeholder="请输入地址" suffix-icon="el-icon-position"></el-input>
            <el-button class="ml-10" type="primary">搜索</el-button>
          </div>

          <!-- 修改表格数据 -->
          <div class="table-modify">
            <el-button type="primary">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>
          </div>

          <!-- 字段 -->
          <el-table :data="tableData" border stripe header-cell-class-name="table-header">
            <el-table-column prop="name" label="名称" width="150"></el-table-column>
            <el-table-column prop="type" label="数据源类型" width="120"></el-table-column>
            <el-table-column prop="address" label="地址" width="140"></el-table-column>
            <el-table-column prop="port" label="端口" width="80"></el-table-column>
            <el-table-column prop="database" label="数据库名称" width="140"></el-table-column>
            <el-table-column prop="status" label="连接状态" width="100"></el-table-column>
            <el-table-column prop="disable" label="是否禁用" width="100"></el-table-column>
            <el-table-column prop="creator" label="创建者" width="140"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="140"></el-table-column>
            <el-table-column prop="modifyTime" label="修改时间" width="140"></el-table-column>
            <el-table-column prop="description" label="描述" width="350"></el-table-column>
            <el-table-column fixed="right" label="操作" width="200">
              <template slot-scope="scope">
                <el-button @click="handleClick()" type="text" size="small">查看</el-button>
                <el-button type="text" size="small">编辑</el-button>
                <el-button type="text" size="small">测试</el-button>
                <el-button type="text" size="small">禁用</el-button>
                <el-button type="text" size="small" style="color: #F56C6C">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 分页 -->
          <div class="paging">
            <el-pagination
                :page-sizes="[5, 10, 15, 20]"
                :page-size="10"
                layout="total, sizes, prev, pager, next, jumper"
                :total="400">
            </el-pagination>
          </div>
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>

<script>
import {listDsType, viewDs} from "@/api/datasource"

export default {
  name: 'Home',
  data() {
    const item = {
      name: 'test-db',
      type: 'MySQL',
      address: '192.168.12.34',
      port: '3306',
      database: 'testDB',
      status: '可用',
      disable: '否',
      creator: 'admin',
      createTime: '2022-12-12',
      modifyTime: '2022-12-12',
      description: '这是一个数据源的描述这是一个数据源的描述'
    };
    return {
      isCollapsed: false, // 默认菜单栏展开
      tableData: Array(10).fill(item)
    }
  },
  methods: {
    collapse() {
      this.isCollapsed = !this.isCollapsed
    },
    handleClick() {
      listDsType().then(res => {
        console.log(res)
      })
      const id = BigInt("1602283332641640448")
      viewDs(id).then(res => {
        console.log(res)
      })
    },
  }
}
</script>

<style>
.home-container, .sidebar, .sidebar-menu {
  height: 100%;
}

.sidebar {
  background-color: rgb(238, 241, 246);
  box-shadow: 2px 0 6px rgb(0 21 42 / 35%)
}

.sidebar-menu {
  overflow-x: hidden
}

.sidebar-header {
  height: 60px;
  line-height: 60px;
  text-align: center
}

.sidebar-logo {
  width: 20px;
  position: relative;
  top: 4px
}

.sidebar-title {
  color: white;
  font-size: 18px;
  margin-left: 6px
}

.main-header {
  font-size: 12px;
  border-bottom: 1px solid #ccc;
  line-height: 60px;
  display: flex
}

.collapse-button {
  flex: 1;
  font-size: 20px;
  cursor: pointer
}

.user-dropdown {
  cursor: pointer;
  width: 28px;
  margin-top: 2px
}

.user-dropdown-icon {
  font-size: 20px
}

.user-name {
  font-size: 13px
}

.search, .paging {
  margin: 10px 0
}

.table-modify {
  margin-top: 30px;
  margin-bottom: 10px
}

.table-header {
  background-color: #eeeeee !important
}
</style>