import { find, map, random } from 'lodash'
import faker from 'faker/locale/zh_CN'
import { service, serviceForMock, request, requestForMock, mock } from '@/api/_service.js'
import * as tools from '@/api/_tools.js'


/**
 * 
 * 数据库 保存
 * @description 
 */
export function DB_SAVE (param) {
  return request({
    url: '/dbtransfer/dbServers/save',
    method: 'post',
    data: param
  })
}


/**
 * 数据库列表
 * @description 列表
 */
export function DB_LIST (param) {
  return request({
    url: '/dbtransfer/dbServers/list',
    method: 'get',
    params: param
  })
}





/**
 * 
 * 任务分组 新增
 * @description 
 */
 export function TASKGROUP_SAVE (param) {
  return request({
    url: '/dbtransfer/tasksgroup/save',
    method: 'post',
    data: param
  })
}


/**
 * 任务分组
 * @description 列表
 */
export function TASKGROUP_LIST (param) {
  return request({
    url: '/dbtransfer/tasksgroup/list',
    method: 'get',
    params: param
  })
}




/**
 * 任务删除
 * @description 
 */
 export function TASKGROUP_DEL (param) {
  return request({
    url: '/dbtransfer/tasksgroup/del',
    method: 'get',
    params: param
  })
}


//////////////////////////////////////////////////

/**
 * 
 * 变量管理 新增
 * @description 
 */
 export function TASK_VARIABLE_SAVE (param) {
  return request({
    url: '/dbtransfer/task_variable/save',
    method: 'post',
    data: param
  })
}


/**
 * 变量管理
 * @description 列表
 */
export function TASK_VARIABLE_LIST (param) {
  return request({
    url: '/dbtransfer/task_variable/list',
    method: 'get',
    params: param
  })
}




/**
 * 变量管理
 * @description 删除
 */
 export function TASK_VARIABLE_DEL (param) {
  return request({
    url: '/dbtransfer/task_variable/del',
    method: 'get',
    params: param
  })
}




//////////////////////////////////////////////////

/**
 * 
 * 任务管理 新增
 * @description 
 */
 export function TASK_SAVE (param) {
  return request({
    url: '/dbtransfer/task_variable/save',
    method: 'post',
    data: param
  })
}


/**
 * 任务管理
 * @description 列表
 */
export function TASK_LIST (param) {
  return request({
    url: '/dbtransfer/task_variable/list',
    method: 'get',
    params: param
  })
}




/**
 * 任务管理
 * @description 删除
 */
 export function TASK_DEL (param) {
  return request({
    url: '/dbtransfer/task_variable/del',
    method: 'get',
    params: param
  })
}

