import Vue from 'vue'
import router from './router';
import request from '@/utils/request'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css'

Vue.config.productionTip = false

Vue.use(ElementUI);

Vue.prototype.axios = request()

BigInt.prototype.toJSON = function () {
    return this.toString()
}

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
