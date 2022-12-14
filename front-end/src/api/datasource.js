import request from '@/utils/request'

export function createDs(data) {
    return request({
        url: '/datasource/create',
        method: 'post',
        data
    })
}

export function modifyDs(data) {
    return request({
        url: '/datasource/modify',
        method: 'post',
        data
    })
}

export function viewDs(id) {
    return request({
        url: '/datasource/view',
        method: 'post',
        data: id
    })
}

export function listDsType() {
    return request({
        url: '/datasource/list-type',
        method: 'post'
    })
}