import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        component: () => import('../views/Index'),
        children: [
            {
                path: '/datasource',
                name: '数据源管理',
                component: () => import('../views/Datasource')
            },
            {
                path: '/chart',
                name: '数据可视化',
                component: () => import('../views/Chart')
            }
        ]
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
