import request from '@/utils/request'

export function listChart(data) {
    return request({
        url: '/chart/list',
        method: 'post',
        data
    })
}