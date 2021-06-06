import { createApp } from 'vue'
import App from './App.vue'
import VueCookies from 'vue3-cookies'
import { createStore } from 'vuex'
import apiModule from './modules/apiModule'
import userModule from "@/modules/userModule";


let app = createApp(App);
app.use(VueCookies);


// Create a new store instance.
const store = createStore({
    modules: {
        api: apiModule,
        user: userModule
    }
})


app.use(store);
app.mount('#app');
