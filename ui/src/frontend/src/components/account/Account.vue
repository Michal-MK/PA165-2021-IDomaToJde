<template>

  <div id="mySidebarRight" class="sidebar">
    <a href="javascript:void(0)" class="closebtn" v-on:click="closeNav">×</a>

    <div v-if="isAuth">
      <UserDetail :user="getUser" @onSignOut="signOut"/>
    </div>
    <div v-else>
      <Login @onLogged="AuthUser"/>
      <br/>
      <br/>
      OR
      <br/>
      <br/>
      <RegisterModal/>
    </div>

  </div>

  <div id="main">
    <button class="openbtn" v-on:click="openNav">

      <div v-if="isAuth">
        <div class="d-flex flex-column align-items-center text-center">
          <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Admin" class="rounded-circle" width="70">
          <div class="mt-3">
            <h4>{{ user.username }}</h4>
          </div>
        </div>
      </div>
      <div v-else>
        <span class="fa fa-user-circle-o" style="font-size:70px; color: black"/>
      </div>
      
    </button>
  </div>


</template>

<script>
import RegisterModal from "@/components/modals/RegisterModal";
import Login from "@/components/account/Login";
import UserDetail from "@/components/account/UserDetail";

export default {
  name: "Account",
  components: {UserDetail, Login, RegisterModal},
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
      this.$cookies.remove("token");
      this.user = '';
      this.isAuthenticated = false;
      this.closeNav();
    },

    openNav() {
      document.getElementById("mySidebarRight").style.width = "100%";
      document.getElementById("main").style.marginRight = "100%";
    },

    closeNav() {
      document.getElementById("mySidebarRight").style.width = "0";
      document.getElementById("main").style.marginRight = "0";
    }
  }
}

</script>

<style scoped>
.sidebar {
  height: 100%;
  width: 0;
  position: fixed;
  z-index: 1;
  top: 0;
  right: 0;
  background-color: rgba(17, 17, 17, 0.93);
  overflow-x: hidden;
  transition: 0.5s;
  padding-top: 60px;
}

.sidebar a {
  padding: 8px 8px 8px 32px;
  text-decoration: none;
  font-size: 25px;
  color: #818181;
  display: block;
  transition: 0.3s;
}

.sidebar a:hover {
  color: #f1f1f1;
}

.sidebar .closebtn {
  position: absolute;
  top: 0;
  right: 25px;
  font-size: 36px;
  margin-left: 50px;
}

.openbtn {
  font-size: 20px;
  cursor: pointer;
  background-color: #fff;

  color: white;
  padding: 10px 15px;
  border: none;
}

.openbtn:hover {
  color: black;
}

#main {
  transition: .5s;
  padding: 16px;
  text-align: right;
}

/* On smaller screens, where height is less than 450px, change the style of the sidenav (less padding and a smaller font size) */
@media screen and (max-height: 450px) {
  .sidebar {
    padding-top: 15px;
  }

  .sidebar a {
    font-size: 18px;
  }
}
</style>