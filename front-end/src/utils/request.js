import axios from 'axios'
import JSONbig from 'json-bigint'

const request = axios.create({
    baseURL: process.env.VUE_APP_BASE_URL,
    timeout: 10000,
    transformResponse: [
        function (data) {
            try {
                return JSON.parse(JSON.stringify(JSONbig.parse(data)));
            } catch (err) {
                return 'Json parse error: ' + err;
            }
        }
    ]
})

// request interceptor
request.interceptors.request.use(
    config => {
        config.headers['Content-Type'] = 'application/json; charset=utf-8'
        return config
    }, error => {
        console.error('err: ' + error)
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