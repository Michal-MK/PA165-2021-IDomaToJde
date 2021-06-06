<template>
  <div v-if="isAuth">
    <UserDetail :user="getUser" @onSignOut="signOut"/>
  </div>
  <div v-else>
    <div class="container hover-base">
      <span class="fa fa-user-circle-o on-hover-invisible top-right" style="font-size:70px; color: black"/>
      <div class="on-hover-visible top-right">
        <Login @onLogged="AuthUser"/>
      </div>
      <div class="on-hover-visible top-right-second">
        <Register @onLogged="AuthUser"/>
      </div>
    </div>
  </div>
</template>

<script>
import Login from "@/components/account/Login";
import UserDetail from "@/components/account/UserDetail";
import Register from "@/components/account/Register";

export default {
  name: "Account",
  components: {Register, UserDetail, Login},
  data() {
    return {
      user: '',
      isAuthenticated: false,
    }
  },

  computed: {
    isAuth() {
      console.log("Asking me with: " + this.isAuthenticated);
      return this.isAuthenticated;
    },

    getUser() {
      return this.user;
    }


  },

  mounted() {
    this.AuthUser();
  },

  methods: {

    async fetchApiUser(token) {
      const response = await fetch("api/auth/authenticate", {
        method: 'POST',
        headers: {
          "token": token
        }
      });

      const data = await response.json();
      console.log("Fetched by token: " + JSON.stringify(data));
      return data;
    },

    isEmpty(s) {
      console.log("Checking for empty: " + s);
      return (!s || s.length === 0);
    },

    async AuthUser() {
      let token = this.$cookies.get("token");

      if (this.isEmpty(token)) {
        console.log("Unauthenticated user");
        this.user = '';
        this.isAuthenticated = false;
      } else {
        console.log("Authenticated user");
        if (this.isEmpty(this.user)) {
          this.user = await this.fetchApiUser(token);

        }

        console.log("Loaded user: " + JSON.stringify(this.user));
        this.isAuthenticated = true;
      }
    },

    signOut() {
      console.log("Calling sign out");
      this.$store.commit('unsetUser');
      this.$cookies.remove("token");
      this.user = '';
      this.isAuthenticated = false;
    },
  }
}

</script>

<style scoped>

.container {
  position: relative;
  text-align: center;
  color: white;
}

.top-right {
  position: absolute;
  top: 8px;
  right: 16px;
}

.top-right-second {
  position: absolute;
  top: 56px;
  right: 16px;
}


.on-hover-visible {

}

.hover-base .on-hover-invisible {
  visibility: visible;
}

.hover-base .on-hover-visible {
  visibility: hidden;
}

.hover-base:hover .on-hover-invisible {
  visibility: hidden;
}

.hover-base:hover .on-hover-visible {
  visibility: visible;
}
</style>