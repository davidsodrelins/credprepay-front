import Vue from 'vue'
import vueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.css'
import App from './App.vue'

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
