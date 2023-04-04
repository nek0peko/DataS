import request from '@/utils/request'

export function listChartType() {
    return request({
        url: '/chart/list-type',
        method: 'post',
    })
}

export function listChartView(types) {
    return request({
        url: '/chart/list-view',
        method: 'post',
        data: types
    })
}

export function previewChart(data) {
    return request({
        url: '/chart/preview',
        method: 'post',
        data
    })
}

export function createChart(data) {
    return request({
        url: '/chart/create',
        method: 'post',
        data
    })
}

export function removeChart(id) {
    return request({
        url: '/chart/remove',
        method: 'post',
        data: id
    })
}