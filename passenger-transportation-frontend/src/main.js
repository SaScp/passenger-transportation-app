import { createApp } from 'vue'

import App from './App.vue'
import Test from "@/components/Test.vue";

const app = createApp(App)
app.component('food-item', Test)
app.mount('#app')