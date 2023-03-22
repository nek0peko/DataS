import request from '@/utils/request'

export function listChartView(data) {
    return request({
        url: '/chart/list-view',
        method: 'post',
        data
    })
}