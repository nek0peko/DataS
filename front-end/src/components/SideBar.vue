<template>
  <el-menu class="sidebar-menu" :default-openeds="['1', '3']" background-color="rgb(48, 65, 86)" text-color="#fff"
           active-text-color="#0099ff" :collapse="isCollapsed" :collapse-transition="false" router>
    <!-- 侧边栏头部Logo和名称 -->
    <div class="sidebar-header">
      <img class="sidebar-logo" src="../assets/logo.png" alt="logo">
      <b class="sidebar-title" v-show="!isCollapsed">DataS</b>
    </div>
    <!-- 侧边栏菜单 -->
    <el-menu-item index="/">
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>
    <el-menu-item index="/datasource">
      <i class="el-icon-setting"></i>
      <span slot="title">数据源管理</span>
    </el-menu-item>
    <el-menu-item index="/panel">
      <i class="el-icon-data-analysis"></i>
      <span slot="title">数据可视化</span>
    </el-menu-item>
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-info"></i><span slot="title">分组测试</span>
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
  </el-menu>
</template>

<script>
export default {
  name: "SideBar",
  props: {
    isCollapsed: Boolean
  }
}
</script>

<style scoped>
.sidebar-menu {
  height: 100%;
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
</style>