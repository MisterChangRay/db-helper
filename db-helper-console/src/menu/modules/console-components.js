export default {
  path: '/console',
  title: '数据传输',
  icon: 'puzzle-piece',
  children: [
    { path: '/console/index', title: '首页', icon: 'home' },
    { path: '/console/tasks_variable/index', title: '环境变量', icon: 'hdd-o' },
    { path: '/console/dblist/index', title: '数据库列表', icon: 'tasks' },
    { path: '/console/tasks/index', title: '任务列表', icon: 'hdd-o' },
    { path: '/console/tasksgroup/index', title: '任务分组', icon: 'hdd-o' },
    { path: '/console/taskslog/index', title: '任务日志', icon: 'hdd-o' },
  ]
}
