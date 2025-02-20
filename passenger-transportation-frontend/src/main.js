import { createApp } from 'vue'
import App from './App.vue'
import router from './route.js';

const app = createApp(App).use(router)
app.mount('#app')