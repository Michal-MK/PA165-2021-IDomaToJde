<template>

  <div class="d-flex flex-column align-items-center text-center" v-on:click="openNav">
    <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="registeredUser" class="rounded-circle"
         width="70">
    <div class="mt-3">
      <h4>{{ user.username }}
      </h4>

      <div v-if="isAdmin">
        admin
      </div>
    </div>
  </div>

  <!--  Sidebar  -->
  <div id="UserDetailSidebarRight" class="sidebar">
    <a href="javascript:void(0)" class="closebtn" v-on:click="closeNav">×</a>


    <div class="container">
      <div class="main-body">
        <div class="row gutters-sm">
          <div class="col-md-4 mb-3">
            <div class="card">
              <div class="card-body">
                <div class="d-flex flex-column align-items-center text-center">
                  <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Admin" class="rounded-circle"
                       width="150">
                  <div class="mt-3">
                    <h4>{{ user.username }}</h4>

                    <div v-if="isAdmin">
                      <span class="text-danger">admin</span>
                    </div>
                  </div>
                  <button class="btn btn-primary mt-2" v-on:click="$emit('onSignOut')">Sing out</button>
                </div>
              </div>
            </div>
            <div class="card mt-3">
              <ul class="list-group list-group-flush">
                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                  <h6 class="mb-0"><span class="fa fa-credit-card-alt"/>
                    <pre style="display: inline"> </pre>
                    Credit
                  </h6>
                  <span class="text-secondary">{{ getCredits }}</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                  <h6 class="mb-0"><span class="fa fa-star"/>
                    <pre style="display: inline"> </pre>
                    Bonus
                  </h6>
                  <span class="text-secondary">{{ user.bonusCredits }}</span>
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
                    {{ user.contactInfo.name }} {{ user.contactInfo.surname }}
                  </div>
                </div>
                <hr>
                <div class="row">
                  <div class="col-sm-3">
                    <h6 class="mb-0">Email</h6>
                  </div>
                  <div class="col-sm-9 text-secondary">
                    {{ user.contactInfo.email }}
                  </div>
                </div>
                <hr>
                <div class="row">
                  <div class="col-sm-3">
                    <h6 class="mb-0">Phone number</h6>
                  </div>
                  <div class="col-sm-9 text-secondary">
                    {{ user.contactInfo.phoneNumber }}
                  </div>
                </div>

                <hr>
                <Calendar/>

              </div>
            </div>


            <div class="row gutters-sm">
              <div class="col-sm-6 mb-3">
                <div class="card h-100">
                  <div class="card-body">
                    <h6 class="d-flex align-items-center mb-3">Assigned courses</h6>
                    <Assigned/>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 mb-3">
                <div class="card h-100">
                  <div class="card-body">
                    <h6 class="d-flex align-items-center mb-3">Your courses</h6>
                    <div v-for="offer in getOfferByUser" :key="offer.id">
                      <button class="btn btn-outline-primary m-1">
                        {{ offer.title }}
                      </button>
                    </div>
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
import Assigned from "@/components/account/Assigned";
import Calendar from "@/components/account/Calendar";

export default {
  name: "UserDetail",
  components: {Calendar, Assigned},
  props: {
    user: JSON,
  },

  data() {
    return {
      offerOfUser: '',
    }
  },

  computed: {

    getOfferByUser() {
      return this.offerOfUser;
    },

    getCredits() {
      return this.$store.getters.getCatchedCredits;
    },

    isAdmin() {
      return this.user.admin === true;
    },
  },

  async mounted() {
    this.offerOfUser = await this.$store.getters.getOwnedOffers(this.user.id);

    let currentCredit = await this.$store.getters.getCredits(this.user.id);
    this.$store.commit('setCatchedCredits', currentCredit);
  },

  emits: ['onSignOut'],

  methods: {




    openNav() {
      this.$forceUpdate();
      document.getElementById("UserDetailSidebarRight").style.width = "100%";
    },

    closeNav() {
      document.getElementById("UserDetailSidebarRight").style.width = "0";
    },

    async addTenCredits() {
      let cr = this.$store.getters.getCatchedCredits + 10;
      this.$store.commit('setCatchedCredits', cr);
      await this.$store.getters.addCredits(this.user.id, 10);
    },

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
  }
}
</script>

<style scoped>
body {
  margin-top: 20px;
  color: #1a202c;
  text-align: left;
  background-color: #e2e8f0;
}

.main-body {
  padding: 15px;
}

.card {
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .1), 0 1px 2px 0 rgba(0, 0, 0, .06);
}

.card {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 0 solid rgba(0, 0, 0, .125);
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

.gutters-sm > .col, .gutters-sm > [class*=col-] {
  padding-right: 8px;
  padding-left: 8px;
}

.mb-3, .my-3 {
  margin-bottom: 1rem !important;
}

.bg-gray-300 {
  background-color: #e2e8f0;
}

.h-100 {
  height: 100% !important;
}

.shadow-none {
  box-shadow: none !important;
}


/*  Sidebar */

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