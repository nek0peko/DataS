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

export function createChart(data) {
    return request({
        url: '/chart/create',
        method: 'post',
        data
    })
}