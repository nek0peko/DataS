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
              <i class="el-icon-data-analysis"></i><span slot="title">数据可视化</span>
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
            <el-input class="w-300" placeholder="请输入数据源名称" suffix-icon="el-icon-search"
                      v-model="dsListForm.name"></el-input>
            <el-input class="w-300 ml-10" placeholder="请输入数据源类型" suffix-icon="el-icon-s-data"
                      v-model="dsListForm.type"></el-input>
            <el-button class="ml-10" type="primary" @click="loadTable">搜索</el-button>
            <el-button class="ml-10" type="info" @click="resetTable">重置</el-button>
            <el-button class="fl-r" type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            <el-button class="fl-r" type="success" @click="handleCreate">新增 <i
                class="el-icon-circle-plus-outline"></i></el-button>
          </div>

          <!-- 字段 -->
          <el-table :data="tableData" border stripe header-cell-class-name="table-header">
            <el-table-column prop="name" label="名称" width="150"></el-table-column>
            <el-table-column prop="type" label="数据源类型" width="120"></el-table-column>
            <el-table-column prop="creator" label="创建者" width="140"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
            <el-table-column prop="updateTime" label="修改时间" width="180"></el-table-column>
            <el-table-column prop="description" label="描述"></el-table-column>
            <el-table-column fixed="right" label="操作" width="200">
              <!--            <el-table-column prop="address" label="地址" width="140"></el-table-column>-->
              <!--            <el-table-column prop="port" label="端口" width="80"></el-table-column>-->
              <!--            <el-table-column prop="database" label="数据库名称" width="140"></el-table-column>-->
              <!--            <el-table-column prop="status" label="连接状态" width="100"></el-table-column>-->
              <!--            <el-table-column prop="disabled" label="是否禁用" width="100"></el-table-column>-->
              <template v-slot="scope">
                <el-button type="text" size="small">查看</el-button>
                <el-button type="text" size="small" @click="handleModify(scope.row)">编辑</el-button>
                <el-button type="text" size="small">测试</el-button>
                <el-button type="text" size="small">禁用</el-button>
                <el-button type="text" size="small" style="color: #F56C6C">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 分页 -->
          <div class="paging">
            <el-pagination layout="total, sizes, prev, pager, next, jumper"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :page-sizes="[5, 10, 15, 20]"
                           :page-size="dsListForm.pageSize"
                           :current-page="dsListForm.pageIndex"
                           :total="pageTotal">
            </el-pagination>
          </div>

          <!-- 对话框 -->
          <el-dialog title="新建数据源" width="40%" :visible.sync="dialogFormVisible">
            <el-form :model="dsForm" label-width="80px" :rules="dsFormRules">
              <el-form-item label="名称" prop="name">
                <el-input v-model="dsForm.name" placeholder="请输入数据源名称（20字符以内）"
                          autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="类型" prop="type">
                <el-select v-model="dsForm.type" placeholder="请选择数据源类型">
                  <el-option v-for="type in dsTypeList" :label="type.name" :value="type.name"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="是否启用" prop="disabled">
                <el-radio-group v-model="dsForm.disabled">
                  <el-radio :label=false>启用</el-radio>
                  <el-radio :label=true>禁用</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="描述" prop="description">
                <el-input type="textarea" v-model="dsForm.description"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="handleSave">确 定</el-button>
            </div>
          </el-dialog>

        </el-main>
      </el-container>

    </el-container>
  </div>
</template>

<script>
import {createDs, listDs, listDsType, modifyDs} from "@/api/datasource"

export default {
  name: 'Home',
  data() {
    return {
      // Menu
      isCollapsed: false, // 默认菜单栏展开

      // Table
      tableData: [],
      pageTotal: 0,
      dsListForm: {
        pageIndex: 1,
        pageSize: 10,
        name: "",
        type: ""
      },

      // Dialog
      dialogFormVisible: false,
      saveMode: 0,
      dsTypeList: [],
      dsForm: {
        id: "",
        name: "",
        type: "",
        description: "",
        disabled: false
      },
      dsFormRules: {
        name: [
          {required: true, message: '请输入数据源名称', trigger: 'blur'},
          {max: 20, message: '长度在20个字符以内', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.loadTable()
    this.loadType()
  },
  methods: {
    collapse() {
      this.isCollapsed = !this.isCollapsed
    },
    loadTable() {
      listDs(this.dsListForm).then(res => {
        this.pageTotal = res.totalCount
        this.dsListForm.pageSize = res.pageSize

        const data = res.data
        this.tableData = data
        this.tableData.type = data.config.type
      })
    },
    loadType() {
      listDsType().then(res => {
        res.data.forEach(type => {
          this.dsTypeList.push({name: type})
        })
      })
    },

    resetTable() {
      this.dsListForm.name = ""
      this.dsListForm.type = ""
      this.loadTable()
    },
    handleSizeChange(pageSize) {
      this.dsListForm.pageSize = pageSize
      this.loadTable()
    },
    handleCurrentChange(pageIndex) {
      this.dsListForm.pageIndex = pageIndex
      this.loadTable()
    },

    handleCreate() {
      this.dsForm = {disabled: false}
      this.saveMode = 0
      this.dialogFormVisible = true
    },
    handleModify(row) {
      this.dsForm = row
      this.saveMode = 1
      this.dialogFormVisible = true
    },
    handleSave() {
      let method = null
      if (this.saveMode == 0) {
        method = createDs(this.dsForm)
      } else {
        method = modifyDs(this.dsForm)
      }
      method.then(res => {
        if (res.success) {
          this.dialogFormVisible = false
          this.$message.success("保存成功！")
          this.loadTable()
        } else {
          this.$message.error(res.errMessage)
        }
      })
    }
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
  margin: 20px 0
}

.table-header {
  background-color: #eeeeee !important
}
</style>