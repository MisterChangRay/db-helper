<template>
  <d2-container>
    <el-card>
      <d2-crud
        ref="d2Crud"
        :columns="columns"
        :data="data"
        :rowHandle="rowHandle"
        add-title="添加数据库服务器"
        :add-rules="addRules"
        :edit-rules="addRules"
        :add-template="addTemplate"
        :form-options="formOptions"
        :pagination="pagination"
        :loading="loading"
        :edit-template="addTemplate"
        @row-remove="handleRowRemove"
        @row-edit="handleRowEdit"
        @pagination-current-change="paginationCurrentChange"
        @dialog-open="handleDialogOpen"
        @form-data-change="formDataChange"
        @row-add="handleRowAdd"
        @custom-emit-detail="showDetail"
        @dialog-cancel="handleDialogCancel">
        <el-button slot="header" style="margin-bottom: 5px" @click="addRow"><i class="fa fa-plus" aria-hidden="true"></i> 新增</el-button>
        <el-input slot="header" style="margin-bottom: 5px" v-model="query.keyword" placeholder="服务器ip" > </el-input>
        <el-select slot="header" v-model="query.group" clearable filterable placeholder="服务器分组">
          <el-option
            v-for="item in groupOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-select slot="header" v-model="query.serverIp" clearable filterable placeholder="服务器">
          <el-option
            v-for="item in SERVER_LIST"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>

        <el-button slot="header" style="margin-bottom: 5px" @click="fetchData"><i class="el-icon-search"></i> 搜索</el-button>
      </d2-crud>
    </el-card>

    <el-dialog
      title="详情"
      :visible.sync="detailDialogVisible"
      width="30%">
      <el-descriptions border :column="2">
        <el-descriptions-item label="IP">{{ detailRow.ip }}</el-descriptions-item>
        <el-descriptions-item label="端口">{{ detailRow.port }}</el-descriptions-item>
        <el-descriptions-item label="账号">{{ detailRow.password }}</el-descriptions-item>
        <el-descriptions-item label="密码">{{ detailRow.password }}</el-descriptions-item>
        <el-descriptions-item label="备注">{{ detailRow.desc }}</el-descriptions-item>

      </el-descriptions>
    </el-dialog>
  </d2-container>
</template>

<script>
  import * as apis from '@/api/console.js'
  import Vue from "vue";

  export default {
    data () {
      return {
        CLIENT_INFO_LIST : [],
        SERVER_LIST : [],
        detailRow: {},
        detailDialogVisible: false,
        groupOptions: [],
        query: {
          keyword: '',
          group:'',
          serverIp: ''
        },
        addRules: {
          projectName: [{ required: true, message: '必填', trigger: 'blur' }],
          serverIp: [{ required: true, message: '必填', trigger: 'blur' }],
        },
        rowHandle: {
          edit: {
            icon: 'el-icon-edit',
            text: '编辑',
            size: 'small',
            show (index, row) {
              if (row.showEditButton) {
                return true
              }
              return false
            },
            disabled (index, row) {
              if (row.forbidEdit) {
                return true
              }
              return false
            }
          },
          remove: {
            icon: 'el-icon-delete',
            size: 'small',
            fixed: 'right',
            confirm: true,
            show (index, row) {
              if (row.showRemoveButton) {
                return true
              }
              return false
            },
            disabled (index, row) {
              if (row.forbidRemove) {
                return true
              }
              return false
            }
          },
          custom: [
            {
              text: "详情",
              emit: "custom-emit-detail"
            }
          ]
        },
        columns: [
          {
            title: 'ip',
            key: 'ip',
            width: '150'
          },
          {
            title: 'port',
            key: 'port',
            width: '150'
          },
          {
            title: '用户名',
            key: 'username',
            width: '150',
          },
          {
            title: '密码',
            key: 'password',
            width: '150'
          },
          {
            title: '备注',
            key: 'desc',
            width: '150'
          }
        ],
        data: [],
        addTemplate: {
          ip: {
            title: 'ip',
            value: '',
            component: {
              span: 11
            }
          },
          port: {
            title: 'port',
            value: '',
            component: {
              span: 11,
              placeholder: "3306"
            }
          },
          username: {
            title: 'username',
            value: '',
            component: {
              span: 11
            }
          },
          password: {
            title: 'password',
            value: '',
            component: {
              span: 11
            }
          },
          desc: {
            title: '备注',
            value: '',
            component: {
              span: 22
            }
          },
        },
        formOptions: {
          labelWidth: '110px',
          labelPosition: 'left',
          gutter: 30,
          saveLoading: false
        },
        loading: false,
        pagination: {
          currentPage: 1,
          pageSize: 5,
          total: 100
        }
      }
    },
    mounted () {
      this.init();
      this.fetchData()
    },
    methods: {
      statusFormatter (row, column, cellValue, index) {
        if(cellValue == '0') {
          return "未激活"
        }
        if(cellValue == '1') {
            return "在线"
        }
        return "离线"
      },
      columnIPFormatter (row, column, cellValue, index) {
        if(cellValue) {
          for (let i in this.CLIENT_INFO_LIST) {
            if(this.CLIENT_INFO_LIST[i].ip == cellValue) {
              return this.formatSelectLabel(this.CLIENT_INFO_LIST[i])
            }
          }
        }
      },
      // 表单事件联动
      formDataChange({ key, value }) {
        let self = this;
      },
      showDetail({index, row}) {
        this.detailDialogVisible = true;
        this.detailRow = row;
      },
      init() {
       this.fetchData();
      },
      formatSelectLabel(item) {
        if(item.remark) {
          return `${item.ip}(${item.remark})`
        }
        return `${item.ip}`
      },
      isOnline(reportTime) {
        // 上报时间在10分钟内算在线
        var min10 = 10 * 60 * 1000;
        if(reportTime) {
          var diff = new Date().getTime() - new Date(reportTime).getTime()
          return diff > min10 ? false : true;
        }
        return false;
      },
      handleDialogOpen ({ mode }) {

      },
      // 普通的新增
      addRow () {
        this.$refs.d2Crud.showDialog({
          mode: 'add'
        })
      },
      handleRowEdit (row, done) {
        row = row.row;
        this.formOptions.saveLoading = true
        let self = this;

        monitorApi.APP_INFO_EDIT(row).then(res => {
          self.$message({
            message: '修改成功',
            type: 'success'
          });
          self.formOptions.saveLoading = false
          self.fetchData();
          done()
        })


      },
      handleRowRemove ({ index, row }, done) {
        this.formOptions.saveLoading = true
        let self = this;

        monitorApi.APP_INFO_DEL(row).then(res => {
          self.$message({
            message: '删除成功',
            type: 'success'
          });
          self.formOptions.saveLoading = false
          self.fetchData();
          done()
        })
      },
      handleRowAdd (row, done) {
        this.formOptions.saveLoading = true
        let self = this;

        monitorApi.APP_INFO_ADD(row).then(res => {
          self.$message({
            message: '保存成功',
            type: 'success'
          });
          self.formOptions.saveLoading = false
          self.fetchData();
          done()
        })


      },
      handleDialogCancel (done) {
        this.formOptions.saveLoading = false
        this.$message({
          message: '取消保存',
          type: 'warning'
        });
        done()
      },
      paginationCurrentChange (currentPage) {
        this.pagination.currentPage = currentPage
        this.fetchData()
      },
      fetchData () {
        this.loading = true
        var self = this;
        apis.DB_LIST(this.query).then(res => {
          this.data = res.data.list
          this.data.forEach(tmp => {
            tmp.showRemoveButton= true;
            tmp.showEditButton = true;
            // 有通讯则不可删除
            if(tmp.reportTime) {
              tmp.forbidRemove= false;
            }
          })
          self.pagination.total = res.data.total
          self.loading = false
        })

      }
    }
  }
</script>

<style scoped>
  .el-input {
    width: 200px;
    margin-right: 10px;
  }
</style>
