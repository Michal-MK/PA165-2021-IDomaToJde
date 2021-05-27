import { createApp } from 'vue'
import App from './App.vue'
import VueCookies from 'vue3-cookies'
import { createStore } from 'vuex'

let app = createApp(App);

const apiModule = {
    state: {
        tokenName: 'token',
        isAuthenticated: false
    },

    computed: {
      getToken(){
          return this.$cookies.get(this.tokenName);
      }


    },

    getters: {
        isAuth(){
            let token = this.getToken();

            if(this.stringEmpty(token)) {
                this.isAuthenticated = false
                return false;
            }




        },

        async AuthUser(){
            let token = this.getToken();

            if(this.stringEmpty(token)){
                console.log("Unauthenticated user");
                this.isAuthenticated = false;
            }else{
                console.log("Authenticated user");
                if(this.isEmpty(this.user)) {
                    this.user = await this.fetchApiUser(token);

                }

                console.log("Loaded user: " + JSON.stringify(this.user));
                this.isAuthenticated = true;
            }
        },

        async fetchApiUser(token){
            const response = await fetch("api/auth/authenticate" , {
                method: 'POST',
                headers: {
                    "token" : token
                }
            });

            const data = await response.json();
            console.log("Fetched by token: " + JSON.stringify(data));
            return data;
        },


        stringEmpty: () => (string) => {
            return string == null || string.length === 0;
        },


    },


}


const store = createStore({
    modules: {
        api: apiModule,
    }
})

app.use(store);
app.use(VueCookies);


app.mount('#app');
