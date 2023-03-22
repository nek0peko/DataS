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