<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item class="breadcrumb" :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item class="breadcrumb">{{ this.$route.name }}</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 搜索 -->
    <div class="search">
      <el-input class="w-300" placeholder="请输入数据源名称" suffix-icon="el-icon-search"
                v-model="dsListForm.name"></el-input>
      <el-input class="w-300 ml-10" placeholder="请输入数据源类型" suffix-icon="el-icon-s-data"
                v-model="dsListForm.type"></el-input>
      <el-button class="ml-10" type="primary" @click="loadTable">搜索</el-button>
      <el-button class="ml-10" type="info" @click="resetTable">重置</el-button>
      <el-button class="fl-r" type="danger" @click="handleBatchRemove">批量删除 <i
          class="el-icon-remove-outline"></i></el-button>
      <el-button class="fl-r" type="success" @click="handleCreate">新增 <i
          class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <!-- 字段 -->
    <el-table :data="tableData" border stripe v-loading="tableLoad" header-cell-class-name="table-header"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="name" label="名称" width="150"></el-table-column>
      <el-table-column prop="type" label="数据源类型" width="120"></el-table-column>
      <el-table-column prop="creator" label="创建者" width="140"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
      <el-table-column prop="updateTime" label="修改时间" width="180"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column fixed="right" label="操作" width="155">
        <template v-slot="scope">
          <el-button type="text" size="small" @click="handleView">查看</el-button>
          <el-button type="text" size="small" @click="handleModify(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="handleTestLink(scope.row.id)">测试</el-button>
          <el-button type="text" size="small" style="color: #F56C6C" @click="handleSingleRemove(scope.row.id)">删除
          </el-button>
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
      <el-form status-icon label-width="80px" ref="dsForm" :model="dsForm" :rules="dsFormRules">
        <el-form-item label="名称" prop="name">
          <el-input v-model="dsForm.name" placeholder="请输入数据源名称（20字符以内）"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="dsForm.type" placeholder="请选择数据源类型">
            <el-option v-for="type in dsTypeList" :label="type.name" :value="type.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="主机名/IP" prop="host">
          <el-input v-model="dsForm.config.host" placeholder="请输入主机名或IP地址"></el-input>
        </el-form-item>
        <el-form-item label="端口号" prop="port">
          <el-input v-model="dsForm.config.port"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="dsForm.config.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="dsForm.config.password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="JDBC参数" prop="jdbc">
          <el-input v-model="dsForm.config.jdbc" placeholder="额外的JDBC连接字符串"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="dsForm.description" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSave">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {createDs, listDs, listDsType, modifyDs, removeDs, testDsLink} from "@/api/datasource";

export default {
  name: "Datasource",
  data() {
    return {
      // Table
      tableData: [],
      tableLoad: true,
      pageTotal: 0,
      dsListForm: {
        pageIndex: 1,
        pageSize: 10,
        name: "",
        type: ""
      },
      multipleSelection: [],

      // Dialog
      dialogFormVisible: false,
      saveMode: 0,
      dsTypeList: [],
      dsForm: {
        id: "",
        name: "",
        type: "",
        description: "",
        config: {
          host: "",
          port: "",
          username: "",
          password: "",
          database: "",
          jdbc: "characterEncoding=UTF-8&connectTimeout=5000&useSSL=false&allowPublicKeyRetrieval=true"
        }
      },
      dsFormRules: {
        name: [
          {required: true, message: '请输入数据源名称', trigger: 'blur'},
          {max: 20, message: '长度在20个字符以内', trigger: 'blur'}
        ],
        type: [{required: true, message: '请选择数据源类型', trigger: 'change'}],
        description: [{max: 50, message: '描述长度应不超过50', trigger: 'blur'}],
        // host: [
        //   {
        //     required: true,
        //     validator: (rule, value, callback) => {
        //       if (!value) {
        //         return callback(new Error('主机名或IP不能为空'))
        //       }
        //       var reg = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$|^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)+([A-Za-z]|[A-Za-z][A-Za-z0-9\-]*[A-Za-z0-9])$/
        //       if (!reg.test(value)) {
        //         return callback(new Error('主机名或IP格式不正确'))
        //       }
        //       callback()
        //     },
        //     trigger: 'blur'
        //   }
        // ]
      }
    }
  },
  created() {
    this.loadTable()
    this.loadType()
  },
  methods: {
    loadTable() {
      this.tableLoad = true
      listDs(this.dsListForm).then(res => {
        if (res.success) {
          this.tableLoad = false

          this.pageTotal = res.totalCount
          this.dsListForm.pageSize = res.pageSize

          const data = res.data
          this.tableData = data
          this.tableData.type = data.config.type
        } else {
          this.$message.error("查询失败：" + res.errMessage)
        }
      })
    },
    loadType() {
      listDsType().then(res => {
        if (res.success) {
          res.data.forEach(type => {
            this.dsTypeList.push({name: type})
          })
        } else {
          this.$message.error("数据源类型获取失败：" + res.errMessage)
        }
      })
    },

    // Table
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
    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    // CRUD
    handleCreate() {
      this.dsForm = {config: {jdbc: "characterEncoding=UTF-8&connectTimeout=5000&useSSL=false&allowPublicKeyRetrieval=true"}}
      this.saveMode = 0
      this.dialogFormVisible = true
    },
    handleModify(row) {
      this.dsForm = Object.assign({}, row) // 将row拷贝到空对象中，防止没点确定前数据改变
      this.saveMode = 1
      this.dialogFormVisible = true
    },
    handleSave() {
      this.$refs['dsForm'].validate((valid) => {
        if (valid) {
          let method
          if (this.saveMode === 0) {
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
      })
    },
    handleSingleRemove(id) {
      this.$confirm('此操作将永久删除该数据源，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.handleRemove([id])
      }).catch(() => {
        this.$message({type: 'info', message: '已取消删除'})
      })
    },
    handleBatchRemove() {
      if (this.multipleSelection.length === 0) {
        this.$message.info("尚未选中任何数据源！")
      } else {
        this.$confirm('此操作将永久删除这些数据源，是否继续？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = this.multipleSelection.map(row => row.id)
          this.handleRemove(ids)
        }).catch(() => {
          this.$message({type: 'info', message: '已取消删除'})
        })
      }
    },
    handleRemove(ids) {
      removeDs(ids).then(res => {
        if (res.success) {
          this.$message.success("删除成功！")
          this.loadTable()
        } else {
          this.$message.error("删除失败：" + res.errMessage)
        }
      })
    },
    handleView() {
    },

    handleTestLink(id) {
      testDsLink(id).then(res => {
        if (res.success) {
          if (res.data) {
            this.$message.success("连接成功！")
          } else {
            this.$message.error("连接失败！")
          }
        } else {
          this.$message.error("系统繁忙！")
        }
      })
    }
  }
}
</script>

<style scoped>
.search, .paging {
  margin: 20px 0
}

.table-header {
  background-color: #eeeeee !important
}

.breadcrumb {
  font-size: 16px;
  margin-top: 10px;
  margin-bottom: 10px
}
</style>