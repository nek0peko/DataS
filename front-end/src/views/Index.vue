<template>
  <div style="height: 100%">
    <el-container class="home-container">

      <el-aside class="sidebar" :width="isCollapsed?'64px':'220px'">
        <SideBar :isCollapsed="isCollapsed"/>
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
          <router-view/>
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>

<script>
import SideBar from "@/components/SideBar";

export default {
  name: 'Index',
  data() {
    return {
      isCollapsed: false, // 默认菜单栏展开
    }
  },
  components: {
    SideBar
  },
  methods: {
    collapse() {
      this.isCollapsed = !this.isCollapsed
    }
  }
}
</script>

<style>
.home-container, .sidebar {
  height: 100%
}

.sidebar {
  background-color: rgb(238, 241, 246);
  box-shadow: 2px 0 6px rgb(0 21 42 / 35%)
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
</style>