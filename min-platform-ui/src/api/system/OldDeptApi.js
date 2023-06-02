import request from '@/utils/request'

// 查询部门列表
export function viewDataList(query) {
  return request({
    url: '/oldDept/viewDataList',
    method: 'get',
    params: query
  })
}

// 查询部门列表
export function pageDept(query) {
  return request({
    url: '/oldDept/page',
    method: 'get',
    params: query
  })
}
// 查询部门列表
export function listDept(query) {
  return request({
    url: '/oldDept/select',
    method: 'get',
    params: query
  })
}

// 查询部门列表（排除节点）
export function listDeptExcludeChild(deptId) {
  return request({
    url: '/oldDept/list/exclude/' + deptId,
    method: 'get'
  })
}

// 查询部门详细
export function getDept(deptId) {
  return request({
    url: '/oldDept/getById?id=' + deptId,
    method: 'get'
  })
}

// 新增部门
export function addDept(data) {
  return request({
    url: '/oldDept/insertDept',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateDept(data) {
  return request({
    url: '/oldDept/updateById',
    method: 'post',
    data: data
  })
}

// 删除部门
export function delDept(deptId) {
  return request({
    url: '/oldDept/removeById?id=' + deptId,
    method: 'post'
  })
}



// 查询 用户表 列表
export function deptTreeSelect(query) {
  return request({
    url: '/oldDept/deptTree',
    method: 'get',
    params: query
  })
}

