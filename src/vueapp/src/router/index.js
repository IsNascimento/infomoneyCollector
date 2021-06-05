import Vue from 'vue'
import VueRouter from 'vue-router'
import NewsPage from '../views/NewsPage.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'NewsPage',
    component: NewsPage
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
