<template>

  <button class="btn btn-primary" v-on:click="openNav">Register</button>


  <!--  Sidebar  -->
  <div id="RegisterSidebarRight" class="sidebar">
    <a href="javascript:void(0)" class="closebtn" v-on:click="closeNav">×</a>

    <div class="container">
      <div class="row">
        <div class="col-4"/>

        <div class="col-4">
          <form>
            <div class="form__group field">
              <input v-model="name" type="input" class="form__field" placeholder="name" name="name" id='regName'
                     autocomplete="username"
                     required/>
              <label for="regName" class="form__label">First name</label>
            </div>

            <div class="form__group field">
              <input v-model="surname" type="input" class="form__field" placeholder="Surname" name="surname"
                     id='surname'
                     autocomplete="username"
                     required/>
              <label for="surname" class="form__label">Surname</label>
            </div>

            <br/>
            <br/>
            <div class="form__group field">
              <input v-model="email" type="email" class="form__field" placeholder="Email" name="email" id='email'
                     autocomplete="username"
                     required/>
              <label for="email" class="form__label">Email</label>
            </div>


            <div class="form__group field">
              <input v-model="phoneNumber" type="tel" class="form__field" placeholder="Phone number" name="phone"
                     id='phone'
                     autocomplete="username"
                     required/>
              <label for="phone" class="form__label">Phone number</label>
            </div>

            <br/>
            <br/>

            <div class="form__group field">
              <input v-model="username" type="input" class="form__field" placeholder="Username" name="username"
                     id='username'
                     autocomplete="username"
                     required/>
              <label for="username" class="form__label">Username</label>
            </div>
            <div class="form__group field">
              <input v-model="password" type="password" class="form__field" placeholder="Password" name="regPass"
                     id='regPass' autocomplete="current-password"
                     required/>
              <label for="regPass" class="form__label">Password</label>
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
          <div v-if="regResponse">
            <div class="text-danger">{{ regResponse }}</div>
          </div>
        </div>
      </div>

      <!--  Login button  -->
      <div class="row pt-3">
        <div class="col text-center">
          <button class="btn btn-primary btn-lg btn-block" v-on:click="register">Register</button>
        </div>
      </div>

    </div>

  </div>
</template>

<script>
export default {
  name: "Register",

  data() {
    return {
      email: '',
      name: '',
      password: '',
      phoneNumber: '',
      surname: '',
      username: '',
      regResponse: ''
    }
  },

  emits: [
      "onLogged"
  ],

  methods: {

    async register() {
      if (this.$store.getters.stringEmpty(this.name)) {
        this.regResponse = "You have to fill name";
        return;
      }

      if (this.$store.getters.stringEmpty(this.surname)) {
        this.regResponse = "You have to fill surname";
        return;
      }

      if (this.$store.getters.stringEmpty(this.email)) {
        this.regResponse = "You have to fill email";
        return;
      }

      if (!this.validEmail(this.email)) {
        this.regResponse = "Invalid email";
        return;
      }

      if (this.$store.getters.stringEmpty(this.phoneNumber)) {
        this.regResponse = "You have to fill phone number";
        return;
      }

      if (!this.validPhone(this.phoneNumber)) {
        this.regResponse = "Phone number has to be in format +xxx xxx xxx xxx or xxx xxx xxx";
        return;
      }

      if (this.$store.getters.stringEmpty(this.username)) {
        this.regResponse = "You have to fill username";
        return;
      }

      if (this.$store.getters.stringEmpty(this.password)) {
        this.regResponse = "You have to fill password";
        return;
      }

      if(this.password.length < 5) {
        this.regResponse = "Password has to have at least four characters";
        return;
      }

      let regResult = await this.apiRegister();

      if(!regResult){
        this.regResponse = "Username already taken, use different one";
        return;
      }

      let logResult = await this.$store.getters.logIn(this.username, this.password);

      if(!logResult){
        this.regResponse = "Inner error while logging, please try log in login form";
        return;
      }

      this.cleanValues();
      this.closeNav();
      this.$emit("onLogged");
    },

    validEmail(email) {
      return /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)..*$/.test(email);
    },

    validPhone(phone) {
      return /^(\+[0-9]{3})? ?[0-9]{3} ?[0-9]{3} ?[0-9]{3}$/.test(phone);
    },

    cleanValues(){
      this.email= '';
      this.name= '';
      this.password= '';
      this.phoneNumber= '';
      this.surname= '';
      this.username= '';
      this.regResponse= '';
    },

    async apiRegister(){
      try {
        const requestOptions = {
          method: "PUT",
          headers: {"Content-Type": "application/json"},
          body: JSON.stringify({
            "email": this.email,
            "name": this.name,
            "password": this.password,
            "phoneNumber": this.phoneNumber,
            "surname": this.surname,
            "username": this.username
          })
        };

        const response = await fetch("/api/users/signup", requestOptions);
        return response.ok;

      }catch (e){
        return false;
      }
    },


    openNav() {
      document.getElementById("RegisterSidebarRight").style.width = "100%";
    },

    closeNav() {
      document.getElementById("RegisterSidebarRight").style.width = "0";
      this.cleanValues();
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