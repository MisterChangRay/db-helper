import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: '/console',
  name: 'console-components-dbtransfer-console',
  meta,
  redirect: { name: 'console-components-dbtransfer-index' },
  component: layoutHeaderAside,
  children: [
    { path: 'index', name: 'console-components-dbtransfer-index', component: _import('console/index.vue'), meta: { ...meta, title: '首页大屏' } },
    
    { path: 'tasks_variable/index', name: 'console-tasks-variable-index', component: _import('console/tasks_variable/index.vue'), meta: { ...meta, title: '环境变量' } },
    { path: 'dblist/index', name: 'console-components-dblist-index', component: _import('console/dblist/index.vue'), meta: { ...meta, title: '应用列表' } },
    { path: 'tasks/index', name: 'console-components-tasks-index', component: _import('console/tasks/index.vue'), meta: { ...meta, title: '任务列表' } },
    { path: 'tasksgroup/index', name: 'console-components-tasks-group-index', component: _import('console/tasksgroup/index.vue'), meta: { ...meta, title: '任务分组列表' } },
    { path: 'taskslog/index', name: 'console-components-taskslog-index', component: _import('console/taskslog/index.vue'), meta: { ...meta, title: '任务日志' } },

  ]
}
