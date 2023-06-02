import request from '@/utils/request'

export function oldPostViewPage(query) {
  return request({
    url: '/oldPost/viewPage',
    method: 'get',
    params: query
  })
}


// 查询 岗位信息表 列表
export function oldPostSelect(query) {

  return request({
    url: '/oldPost/select',
    method: 'get',
    params: query
  })
}

// 查询 岗位信息表 分页
export function oldPostPage( query) {
  return request({
    url: '/oldPost/page',
    method: 'get',
    params: query
  })
}

// 查询 岗位信息表 详细
export function getOldPost(id) {
  return request({
    url: '/oldPost/getById?id=' + id,
    method: 'get'
  })
}

// 新增 岗位信息表
export function saveOldPost(data) {
  return request({
    url: '/oldPost/save',
    method: 'post',
    data: data
  })
}

// 修改 岗位信息表
export function updateOldPostById(data) {
  return request({
    url: '/oldPost/updateById',
    method: 'post',
    data: data
  })
}

// 删除 岗位信息表
export function removeOldPostByIds(ids) {
  return request({
    url: '/oldPost/removeByIds',
    method: 'post',
    data: ids
  })
}
