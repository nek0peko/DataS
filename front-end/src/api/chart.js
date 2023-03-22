import request from '@/utils/request'

export function listChartType(data) {
    return request({
        url: '/chart/list-type',
        method: 'post',
        data
    })
}

export function listChartView(data) {
    return request({
        url: '/chart/list-view',
        method: 'post',
        data
    })
}