import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueToast from 'vue-toast-notification';
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

Vue.config.productionTip = false

Vue.use(VueToast, {
    position: 'top',
    duration: 2000
})
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

import 'vue-toast-notification/dist/theme-sugar.css';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

window._vue = new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
