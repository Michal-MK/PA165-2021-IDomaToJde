<template>

  <div class="text-white pt-5 mt-5" style="text-align: center">
  <h2>  Hello {{user.username}} </h2>
  <div>{{JSON.stringify(user, null, '\n')}}</div>
  </div>





  <div class="container">
    <div class="main-body">
      <div class="row gutters-sm">
        <div class="col-md-4 mb-3">
          <div class="card">
            <div class="card-body">
              <div class="d-flex flex-column align-items-center text-center">
                <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Admin" class="rounded-circle" width="150">
                <div class="mt-3">
                  <h4>{{user.username}}</h4>
                </div>
                <button class="btn btn-primary" v-on:click="$emit('onSignOut')">Sing out</button>
              </div>
            </div>
          </div>
          <div class="card mt-3">
            <ul class="list-group list-group-flush">
              <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                <h6 class="mb-0"><span class="fa fa-credit-card-alt"/><pre style="display: inline"> </pre>Credit</h6>
                <span class="text-secondary">{{getCredits}}</span>
              </li>
              <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                <h6 class="mb-0"><span class="fa fa-star"/><pre style="display: inline">  </pre>Bonus</h6>
                <span class="text-secondary">{{user.bonusCredits}}</span>
              </li>
              <li class="list-group-item d-flex flex-wrap">

                <button class="btn btn-success" v-on:click="addTenCredits">Collect 10 credits</button>
              </li>

            </ul>
          </div>
        </div>
        <div class="col-md-8">
          <div class="card mb-3">
            <div class="card-body">
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Full Name</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  {{user.contactInfo.name}} {{user.contactInfo.surname}}
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Email</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  {{user.contactInfo.email}}
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Phone number</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  {{user.contactInfo.phoneNumber}}
                </div>
              </div>

            </div>
          </div>

          <div class="row gutters-sm">
            <div class="col-sm-6 mb-3">
              <div class="card h-100">
                <div class="card-body">
                  <h6 class="d-flex align-items-center mb-3">Assigned courses</h6>
                  <div v-for="offer in getSubscribedOffers" :key="offer.id">
                    <button class="btn btn-outline-success">{{offer.title}}</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 mb-3">
              <div class="card h-100">
                <div class="card-body">
                  <h6 class="d-flex align-items-center mb-3">Your courses</h6>
                  <div v-for="offer in getOwnedOffers" :key="offer.id">
                    <button class="btn btn-outline-success">{{offer.title}}</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>






</template>

<script>
export default {
  name: "UserDetail",

  props: {
    user : JSON,
  },

  data() {
    return {
      credits: 0,
    }
  },

  computed: {
    getCredits(){
      return this.credits;
    }
  },

  mounted() {
    this.credits = this.user.credits;
  },

  emits: ['onSignOut'],

  methods: {
    async addTenCredits(){
      this.credits = this.credits + 10;
      console.log("Updating credits to: " + this.credits);

      let token = this.$cookies.get("token");

      await fetch("api/users/setCredits/" + this.user.id + "/" + this.credits, {
        method: 'POST',
        headers: {
          "token" : token
        }
      });

    },

    getOwnedOffers(){
      fetch("api/offers/ofUser/" + this.user.id)
          .then((result) => result.json())
          .then((data) => {return data;})
    },

    getSubscribedOffers(){
      fetch("api/offers/withSubscribedUser/" + this.user.id)
          .then((result) => result.json())
          .then((data) => {return data;})
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
  }
}
</script>

<style scoped>
body{
  margin-top:20px;
  color: #1a202c;
  text-align: left;
  background-color: #e2e8f0;
}
.main-body {
  padding: 15px;
}
.card {
  box-shadow: 0 1px 3px 0 rgba(0,0,0,.1), 0 1px 2px 0 rgba(0,0,0,.06);
}

.card {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 0 solid rgba(0,0,0,.125);
  border-radius: .25rem;
}

.card-body {
  flex: 1 1 auto;
  min-height: 1px;
  padding: 1rem;
}

.gutters-sm {
  margin-right: -8px;
  margin-left: -8px;
}

.gutters-sm>.col, .gutters-sm>[class*=col-] {
  padding-right: 8px;
  padding-left: 8px;
}
.mb-3, .my-3 {
  margin-bottom: 1rem!important;
}

.bg-gray-300 {
  background-color: #e2e8f0;
}
.h-100 {
  height: 100%!important;
}
.shadow-none {
  box-shadow: none!important;
}

</style>