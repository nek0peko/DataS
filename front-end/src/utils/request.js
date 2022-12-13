import axios from 'axios'

const request = axios.create({
    baseURL: process.env.VUE_APP_BASE_URL,
    timeout: 10000
})

// request interceptor
request.interceptors.request.use(
    config => {
        config.headers['Content-Type'] = 'application/json; charset=utf-8'
        return config
    }, error => {
        console.log('err: ' + error)
        return Promise.reject(error)
    });

// response interceptor
request.interceptors.response.use(
    response => {
        let res = response.data;
        if (response.config.responseType === 'blob') {
            return res
        }
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        return Promise.reject(error)
    }
)

export default request