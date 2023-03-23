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

export function removeDs(data) {
    return request({
        url: '/datasource/remove',
        method: 'post',
        data
    })
}

export function listDs(data) {
    return request({
        url: '/datasource/list',
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

export function testDsLink(id) {
    return request({
        url: '/datasource/test-link',
        method: 'post',
        data: id
    })
}

export function listDsTable(id) {
    return request({
        url: '/datasource/list-table',
        method: 'post',
        data: id
    })
}

export function listDsColumn(data) {
    return request({
        url: '/datasource/list-column',
        method: 'post',
        data
    })
}