<template>
  <button class="btn btn-primary" v-on:click="openNav">Log in</button>

  <!--  Sidebar  -->
  <div id="LogSidebarRight" class="sidebar">
    <a href="javascript:void(0)" class="closebtn" v-on:click="closeNav">×</a>

    <div class="conteiner">
      <div class="row">
        <div class="col-4"/>

        <div class="col-4">
          <form>
            <div class="form__group field">
              <input v-model="loginName" type="input" class="form__field" placeholder="Name" name="name" id='name'
                     autocomplete="username"
                     required/>
              <label for="name" class="form__label">Surname</label>
            </div>

            <div class="form__group field">
              <input v-model="loginPass" type="password" class="form__field" placeholder="Password" name="pass"
                     id='pass' autocomplete="current-password"
                     required/>
              <label for="pass" class="form__label">Password</label>
            </div>
          </form>
          <br/>
          <br/>

        </div>

        <div class="col-4"/>
      </div>

      <!--    Error display-->
      <div class="row">
        <div class="col text-center">
          <div v-if="loginResponse">
            <div class="text-danger">{{ loginResponse }}</div>
          </div>
        </div>
      </div>

      <!--  Login button  -->
      <div class="row pt-3">
        <div class="col text-center">
          <button class="btn btn-primary btn-lg btn-block" v-on:click="logIn">Login</button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "Login",

  data() {
    return {
      loginName: '',
      loginPass: '',
      loginResponse: ''
    }
  },

  emits: [
    "onLogged"
  ],

  methods: {
    async logIn() {
      let name = this.loginName;
      let pass = this.loginPass;

      let logged = await this.$store.getters.logIn(name, pass);
      if (logged) {
        let user = await this.$store.getters.getAuthUser;
        let owned = await this.$store.getters.getOwnedOffers(user.id);
        let subs = await this.$store.getters.getSubscribedOffers(user.id);

        this.$store.commit('setUser', user);
        this.$store.commit('setOwned', owned);
        this.$store.commit('setSubscribed', subs);

        this.closeNav();
        this.$emit("onLogged");

      } else {
        this.loginResponse = "Invalid username or password";
        console.log("Not logged")
      }
    },

    openNav() {
      document.getElementById("LogSidebarRight").style.width = "100%";
    }
    ,

    closeNav() {
      document.getElementById("LogSidebarRight").style.width = "0";
    }

  }


}
</script>

<style scoped>
.form__group {
  position: relative;
  padding: 15px 0 0;
  margin-top: 10px;
}

.form__field {
  font-family: inherit;
  width: 100%;
  border: 0;
  border-bottom: 2px solid #9b9b9b;
  outline: 0;
  font-size: 1.3rem;
  color: #fff;
  padding: 7px 0;
  background: transparent;
  transition: border-color 0.2s;
}

.form__field::placeholder {
  color: transparent;
}

.form__field:placeholder-shown ~ .form__label {
  font-size: 1.3rem;
  cursor: text;
  top: 20px;
}

.form__label {
  position: absolute;
  top: 0;
  display: block;
  transition: 0.2s;
  font-size: 1rem;
  color: #9b9b9b;
}

.form__field:focus {
  padding-bottom: 6px;
  font-weight: 700;
  border-width: 3px;
  border-image: linear-gradient(to right, #11998e, #38ef7d);
  border-image-slice: 1;
}

.form__field:focus ~ .form__label {
  position: absolute;
  top: 0;
  display: block;
  transition: 0.2s;
  font-size: 1rem;
  color: #11998e;
  font-weight: 700;
}

/* reset input */
.form__field:required, .form__field:invalid {
  box-shadow: none;
}

/* demo */
body {
  font-family: "Poppins", sans-serif;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  font-size: 1.5rem;
  background-color: #222222;
}


/* Sidebar */

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