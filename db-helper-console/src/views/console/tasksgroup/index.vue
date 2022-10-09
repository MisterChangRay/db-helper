<template>
  <d2-container>
    <el-card>
      <d2-crud
        ref="d2Crud"
        :columns="columns"
        :data="data"
        :rowHandle="rowHandle"
        add-title="添加任务分组"
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
        <el-input slot="header" style="margin-bottom: 5px" v-model="query.keyword" placeholder="名称" > </el-input>
  
        <el-button slot="header" style="margin-bottom: 5px" @click="fetchData"><i class="el-icon-search"></i> 搜索</el-button>
      </d2-crud>
    </el-card>

    <el-dialog
      title="详情"
      :visible.sync="detailDialogVisible"
      width="30%">
      <el-descriptions border :column="2">
        <el-descriptions-item label="名称">{{ detailRow.name }}</el-descriptions-item>
        <el-descriptions-item label="名称">{{ detailRow.desc }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ columnDateFormatter(1,1,detailRow.createTime) }}</el-descriptions-item>

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
            title: '名称',
            key: 'name',
            width: '150'
          },
          {
            title: '备注',
            key: 'desc',
            width: '150'
          },
          {
            title: '创建时间',
            key: 'createTime',
            width: '150',
          }
        ],
        data: [],
        addTemplate: {
          name: {
            title: '名称',
            value: '',
            component: {
              span: 15
            }
          },
          desc: {
            title: '备注',
            value: '',
            component: {
              span: 15,
              placeholder: ""
            }
          }
         
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

        apis.TASKGROUP_SAVE(row).then(res => {
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

        apis.TASKGROUP_DEL(row).then(res => {
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

        apis.TASKGROUP_SAVE(row).then(res => {
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
        apis.TASKGROUP_LIST(this.query).then(res => {
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
