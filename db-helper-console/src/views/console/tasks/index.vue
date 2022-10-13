<template>
  <d2-container>
    <el-card>
      <d2-crud ref="d2Crud" :columns="columns" :data="data" :rowHandle="rowHandle" add-title="添加任务"
        :add-rules="addRules" :edit-rules="addRules" :form-options="formOptions" :pagination="pagination"
        :loading="loading" :add-template="addTemplate" :edit-template="addTemplate" @row-remove="handleRowRemove"
        @row-edit="handleRowEdit" @pagination-current-change="paginationCurrentChange" @dialog-open="handleDialogOpen"
        @form-data-change="formDataChange" @row-add="handleRowAdd" @custom-emit-detail="showDetail"
        @dialog-cancel="handleDialogCancel">
        <el-button slot="header" style="margin-bottom: 5px" @click="transferdb"><i class="fa fa-plus"
            aria-hidden="true">导库向导</i> </el-button>
        <el-button slot="header" style="margin-bottom: 5px" @click="addRow"><i class="fa fa-plus"
            aria-hidden="true">新增任务</i> </el-button>
        <el-input slot="header" style="margin-bottom: 5px;margin-left:5px" v-model="query.keyword" placeholder="名称或描述">
        </el-input>
        <el-select slot="header" v-model="query.group" clearable filterable placeholder="所属分组">
          <el-option v-for="item in groupOptions" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>

        <el-button slot="header" style="margin-bottom: 5px" @click="fetchData"><i class="el-icon-search"></i> 搜索
        </el-button>
      </d2-crud>
    </el-card>

    <el-dialog title="详情" :visible.sync="detailDialogVisible" width="30%">
      <el-descriptions border :column="2">
        <el-descriptions-item label="IP">{{ detailRow.ip }}</el-descriptions-item>
        <el-descriptions-item label="端口">{{ detailRow.port }}</el-descriptions-item>
        <el-descriptions-item label="账号">{{ detailRow.password }}</el-descriptions-item>
        <el-descriptions-item label="密码">{{ detailRow.password }}</el-descriptions-item>
        <el-descriptions-item label="备注">{{ detailRow.desc }}</el-descriptions-item>

      </el-descriptions>
    </el-dialog>


    <el-dialog title="数据迁移向导" :visible.sync="dbTransferDialogVisible" width="80%">
      <el-tabs v-model="activeName" >
        <el-tab-pane label="数据库配置" name="first">
          <el-form ref="transferFormRef" :model="transferForm" label-width="150px">
            <el-form-item label="名称" prop="name">
              <el-col :span="14">
                <el-input v-model="transferForm.name"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="描述" prop="desc">
              <el-col :span="14">
                <el-input v-model="transferForm.desc"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="所属分组" prop="desc">
              <el-col :span="5">
                <el-select v-model="transferForm.group" placeholder="请选择">
                  <el-option v-for="item in TASKGROUP_LIST" :key="item.value" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="9">
                <el-form-item label="执行表达式(cron)" prop="desc">
                  <el-autocomplete class="inline-input" v-model="transferForm.cron"
                    :fetch-suggestions="queryCronSuggest" placeholder="请输入内容" @select="handleSelect"></el-autocomplete>
                </el-form-item>
              </el-col>
            </el-form-item>
            <el-form-item label="同步配置(从源库):">
              <el-col :span="5">
                <el-form-item prop="source">
                  <el-cascader :props="loadDbs" v-model="transferForm.source" :show-all-levels="true"></el-cascader>
                </el-form-item>
              </el-col>
              <el-col :span="9">
                <el-form-item label="到目标库" prop="target">
                  <el-cascader :props="loadDbs" v-model="transferForm.target" :show-all-levels="true"></el-cascader>
                </el-form-item>
              </el-col>
            </el-form-item>
            <el-form-item label="需同步表(默认全部)" prop="tables">
              <el-col :span="14">

                <el-cascader :options="allTables" v-model="transferForm.tabels" :filterable="true"
                  :props="{ multiple: true,checkStrictly :true, label:'name', value:'name' }" :show-all-levels="true"></el-cascader>
              </el-col>

            </el-form-item>
            <el-form-item label="sql附加" prop="appendSql">
              <el-col :span="14">
                <el-input v-model="appendSql" placeholder="用于统一在sql后附加自定义sql,可以使用环境变量"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item>
              <el-col :span="14">
                <el-button type="primary" @click="createTransferNext">下一步</el-button>
              </el-col>

            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="表及主键配置" name="second">
            <el-col :span="14">
              <el-form ref="transferFormRef2" :model="transferForm" label-width="150px">
                <div v-for="(tmp, i) in allTables">
                  <el-divider content-position="left">表({{tmp.name}})</el-divider>
                  <el-tooltip class="item" effect="dark" content="通过此语句进行数据查询并迁移到其他库,将会对此语句自动增加分页;这里可以使用全局变量" placement="right-start">
                    <el-form-item label="查询语句" prop="sql">
                        <el-input v-model="allTables[i].sql"></el-input>
                    </el-form-item>
                  </el-tooltip>

                  <el-tooltip class="item" effect="dark" content="通过此字段来确保数据不会重复迁移,最好为主键或唯一字段" placement="right-start">
                    <el-form-item label="主键" prop="desc">
                        <el-input v-model="allTables[i].pk"></el-input>
                    </el-form-item>
                  </el-tooltip>
                </div>

                <el-button type="primary" @click="createTransferTask">提交</el-button>
              </el-form>
          </el-col>
            
        </el-tab-pane>
      </el-tabs>

    </el-dialog>
  </d2-container>
</template>

<script>
import * as apis from '@/api/console.js'
import Vue from "vue";

export default {
  data() {
    return {
      appendSql:'',
      activeName:'first',
      cronDemo: [
        { "cron": "*/5 * * * * ?", "value": "每5秒执行" },
        { "cron": "0 */15 * * * ?", "value": "每15分钟执行" },
        { "cron": "0 0 */1 * * ?", "value": "每小时执行" },
        { "cron": "0 0 3 * * ?", "value": "每天凌晨3点执行" },
        { "cron": "0 0 0-4 * * ?", "value": "每天凌晨0-4点执行" },
        { "cron": "0 0 0-6 1-9 * ?", "value": "每月1-9号的每天0-6点执行" },
      ],
      allTables: [],
      transferForm: {
        name: undefined,
        desc: undefined,
        source: [],
        target: [],
        tabels: []
      },
      loadDbs: {
        lazy: true,
        lazyLoad: this.lazyLoadDatabases
      },
      dbTransferDialogVisible: false,
      DB_LIST: [],
      TASKGROUP_LIST: [],
      CLIENT_INFO_LIST: [],
      SERVER_LIST: [],
      detailRow: {},
      detailDialogVisible: false,
      groupOptions: [],
      query: {
        keyword: '',
        group: '',
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
          show(index, row) {
            if (row.showEditButton) {
              return true
            }
            return false
          },
          disabled(index, row) {
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
          show(index, row) {
            if (row.showRemoveButton) {
              return true
            }
            return false
          },
          disabled(index, row) {
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
          title: '描述',
          key: 'desc',
          width: '150'
        },
        {
          title: '最近执行时间',
          key: 'lastRunTime',
          width: '150',
        },
        {
          title: '执行次数',
          key: 'runCounter',
          width: '150'
        },
        {
          title: '进度',
          key: 'progress',
          width: '150'
        },
        {
          title: '状态',
          key: 'status',
          width: '150'
        },
        {
          title: '所属分组',
          key: 'group',
          width: '150'
        }
      ],
      data: [],
      addTemplate: {
        name: {
          title: '名称',
          value: '',
          component: {
            span: 11
          }
        },
        desc: {
          title: '描述',
          value: '',
          component: {
            span: 11,
            placeholder: ""
          }
        },
        group: {
          title: '所属分组',
          value: '',
          component: {
            name: 'el-select',
            options: [],
            span: 11,
            placeholder: "请选择"
          }
        },
        cron: {
          title: '执行表达式',
          value: '',
          component: {
            span: 11
          }
        },
        group: {
          title: '所属分组',
          value: '',
          component: {
            name: 'el-select',
            options: [],
            span: 11,
            placeholder: "请选择"
          }
        },
        sql: {
          title: '执行SQL',
          value: '',
          component: {
            type: 'textarea',
            span: 22,
            props: {
              minRows: 2, maxRows: 6
            }
          }
        },
        script: {
          title: '过滤脚本',
          value: '',
          component: {
            type: 'textarea',
            span: 22,
            props: {
              minRows: 1, maxRows: 6
            }
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
  mounted() {
    this.init();
    this.fetchData()
  },
  watch: {
    "appendSql": {
      handler: function (val) {
        this.rebuildSql();
      },
    
    },
    "transferForm.source": {
      handler: function (val) {
        this.lazyLoadTables();
      },
      deep: true

    }
  },
  methods: {
    createTransferTask() {

      let self =this;
      // 创建数据库迁移任务
      let param = {
        tables: undefined,
        sourceDBId: undefined,
        sourceDatabase: undefined,
        targetDBId: undefined,
        targetDatabase: undefined,
        cron: undefined,
        name: undefined,
        desc: undefined,
        groupId: undefined
      }

      param.tables = this.allTables;
      param.name = this.transferForm.name;
      param.cron = this.transferForm.cron;
      param.desc = this.transferForm.desc;

      param.sourceDBId = this.transferForm.source[0];
      param.sourceDatabase = this.transferForm.source[1];
      param.targetDBId = this.transferForm.target[0];
      param.targetDatabase = this.transferForm.target[1];
      param.groupId = this.transferForm.group;

      
      apis.TASK_CREATE(param).then(res => {
        if(res.code == 0) {
          self.$message({
            message: '创建成功',
            type: 'success'
          });
          self.dbTransferDialogVisible = false;
        } else {
          self.$message({
            message: res.msg,
            type: 'error'
          });
        }
        
      })


    },
    rebuildSql() {
      let self = this;
      this.allTables.forEach(tmp => {
              tmp.sql = `select * from ${tmp.name} ${self.appendSql}`;
      })
    },
    createTransferNext() {
        this.activeName = 'second'
    },
    handleSelect(item) {
      console.log("选中", item)
      this.transferForm.cron = item.cron
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    queryCronSuggest(queryString, cb) {
      var restaurants = this.cronDemo;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    lazyLoadDatabases(node, resolve) {
      const { level } = node;
      console.log("load databases:", node)
      if (level == 0) {
        // 加载根节点,即所有数据库服务器
        var data = [];
        this.DB_LIST.data.list.forEach(tmp => {
          let t2 = {
            label: `${tmp.ip}(${tmp.desc || ''})`,
            value: tmp.id
          };
          data.push(t2)
        })
        resolve(data);
      }

      if (level == 1) {
        // 已选择数, 加载服务器所有数据库
        apis.DB_LIST_LOAD_DBS({ dbId: node.value }).then(res => {
          console.log("aaa", res)
          var data = [];
          res.data.forEach(tmp => {
            let t2 = {
              leaf: true,
              label: tmp,
              value: tmp
            };
            data.push(t2)
          })
          resolve(data);
        })
      }

    },
    lazyLoadTables() {
      console.log("load all tabels", this.transferForm, arguments)
      let self = this;
      if (this.transferForm.source && this.transferForm.source.length == 2) {
        // 加载根节点,即数据库所有表
        apis.DB_LIST_LOAD_TABLES({ dbId: this.transferForm.source[0], dbName: this.transferForm.source[1] }).then(res => {
          
          res.data.forEach(tmp => {
            tmp.leaf = true;
          })
          self.allTables = res.data;
          self.rebuildSql();
          console.log(333333333333333, self.dbTables)
        })

      }
    },
    transferdb() {
      this.dbTransferDialogVisible = true;
      console.log("转移db")

    },
    statusFormatter(row, column, cellValue, index) {
      if (cellValue == '0') {
        return "未激活"
      }
      if (cellValue == '1') {
        return "在线"
      }
      return "离线"
    },
    formatSelectLabel(item) {
      if (item.desc) {
        return `${item.name}(${item.desc})`
      }
      return `${item.name}`
    },
    columnIPFormatter(row, column, cellValue, index) {
      if (cellValue) {
        for (let i in this.CLIENT_INFO_LIST) {
          if (this.CLIENT_INFO_LIST[i].ip == cellValue) {
            return this.formatSelectLabel(this.CLIENT_INFO_LIST[i])
          }
        }
      }
    },
    // 表单事件联动
    formDataChange({ key, value }) {
      let self = this;
    },
    showDetail({ index, row }) {
      this.detailDialogVisible = true;
      this.detailRow = row;
    },
    init() {
      let self = this;
      apis.TASKGROUP_LIST({}).then(res => {

        self.TASKGROUP_LIST = [];
        res.data.list.forEach(tmp => {
          let t2 = {
            label: self.formatSelectLabel(tmp),
            value: tmp.id
          };
          self.TASKGROUP_LIST.push(t2)
        })


      })


      apis.DB_LIST({}).then(res => {
        self.DB_LIST = res;
      })

      this.fetchData();
    },
    isOnline(reportTime) {
      // 上报时间在10分钟内算在线
      var min10 = 10 * 60 * 1000;
      if (reportTime) {
        var diff = new Date().getTime() - new Date(reportTime).getTime()
        return diff > min10 ? false : true;
      }
      return false;
    },
    handleDialogOpen({ mode }) {

    },
    // 普通的新增
    addRow() {
      this.$refs.d2Crud.showDialog({
        mode: 'add'
      })
    },
    handleRowEdit(row, done) {
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
    handleRowRemove({ index, row }, done) {
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
    handleRowAdd(row, done) {
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
    handleDialogCancel(done) {
      this.formOptions.saveLoading = false
      this.$message({
        message: '取消保存',
        type: 'warning'
      });
      done()
    },
    paginationCurrentChange(currentPage) {
      this.pagination.currentPage = currentPage
      this.fetchData()
    },
    fetchData() {
      this.loading = true
      var self = this;
      apis.DB_LIST(this.query).then(res => {
        this.data = res.data.list
        this.data.forEach(tmp => {
          tmp.showRemoveButton = true;
          tmp.showEditButton = true;
          // 有通讯则不可删除
          if (tmp.reportTime) {
            tmp.forbidRemove = false;
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
  margin-right: 10px;
}

.el-cascader--medium {
  font-size: 14px;
  line-height: 36px;
  width: 100%;
}
</style>
