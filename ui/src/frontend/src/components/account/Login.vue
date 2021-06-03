<template>
  <div class="conteiner">
    <div class="row">
      <div class="col-4"/>

      <div class="col-4">
        <div class="form__group field">
          <input v-model="loginName" type="input" class="form__field" placeholder="Name" name="name" id='name'
                 required/>
          <label for="name" class="form__label">Surname</label>
        </div>

        <div class="form__group field">
          <input v-model="loginPass" type="input" class="form__field" placeholder="Password" name="pass" id='pass'
                 required/>
          <label for="pass" class="form__label">Password</label>
        </div>

        <br/>
        <br/>
        <button class="btn btn-primary btn-lg btn-block" v-on:click="logIn">Login</button>
      </div>

      <div class="col-4"/>
    </div>
  </div>
  <!--  <label>Username:</label>-->
  <!--  <input type="text" id="fname" name="fname">-->
  <!--  <br><br>-->
  <!--  <label>Password:</label>-->
  <!--  <input type="text" id="lname" name="lname">-->
  <!--  <br><br>-->
</template>

<script>
export default {
  name: "Login",

  data() {
    return {
      loginName: '',
      loginPass: ''
    }
  },

  methods: {
    logIn() {
      let name = this.loginName;
      let pass = this.loginPass;

      fetch("api/auth/login", {
        method: 'POST',
        headers: {"Content-Type": "application/json", "username": name, "pass": pass}
      }).then((response) => response.json())
          .then((data) => {

            let success = data.successful;
            if (success === true) {
              this.$cookies.set("token", data.token);
              this.$emit("onLogged");
              console.log("Logged");
            } else {
              console.log("Not logged")
            }

            console.log("Login response: " + success);
          });

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

</style>