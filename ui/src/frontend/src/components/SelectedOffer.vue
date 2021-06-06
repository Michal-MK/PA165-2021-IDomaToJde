<template>
  <div class="container">
    <div class="main-body">
      <div class="row">

        <div v-if="!isEmpty(getError)">
          <div class="alert alert-danger">
            {{ getError }}
          </div>
        </div>
        <div v-else>
          <div class="alert">

          </div>
        </div>

      </div>
      <div class="row gutters-sm">
        <div class="col-md-4 mb-3">
          <div class="card">
            <div class="card-body">
              <div class="d-flex flex-column align-items-center text-center">
                <img
                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT81XbWhkSL6nudDIrPXvc6J5Z4yNcUdzq4dg&usqp=CAU"
                    alt="course" class="rounded-circle" width="150">
                <div class="mt-3">
                  <h4>{{ offer.title }}</h4>
                </div>

                <div class="mt-2">
                  <div>by <i>{{ offer.owner.name }} {{ offer.owner.surname }}</i></div>
                  <br/>
                </div>
                <div>
                  <div v-if="isLogged">
                    <div v-if="isOwner">
                      <button class="btn btn-primary-outline" disabled>Your course</button>
                    </div>
                    <div v-else-if="alreadyAssigned">
                      <button class="btn btn-primary-outline" disabled>Already assigned</button>
                    </div>
                    <div v-else>
                      <button class="btn btn-primary" v-on:click="onAssign">Assign</button>
                    </div>
                  </div>
                  <div v-else>
                    <button class="btn btn-primary-outline" disabled>Log to assign</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="card mt-3">
            <ul class="list-group list-group-flush">
              <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                <h6 class="mb-0"><span class="fa fa-credit-card-alt"/>
                  <pre style="display: inline"/>
                  Price
                </h6>
                <span class="text-secondary">{{ offer.price }}</span>
              </li>
              <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                <h6 class="mb-0"><span class="fa fa-mortar-board"/>
                  <pre style="display: inline"/>
                  Category
                </h6>
                <span class="text-secondary">{{ offer.category.name }}</span>
              </li>
            </ul>
          </div>
        </div>
        <div class="col-md-8">
          <div class="card mb-3">
            <div class="card-body">
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Description</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  {{ offer.description }}
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Capacity</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  <small>{{ offer.capacity - offer.registered }} free spaces</small>
                </div>
              </div>
            </div>
          </div>

          <div v-if="isOwner">
            <div class="row gutters-sm">
              <div class="col mb-3">
                <div class="card h-100">
                  <div class="card-body">
                    <AssignedPeople :offerId="getOfferId"></AssignedPeople>
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
import AssignedPeople from "@/components/offer/AssignedPeople";

export default {
  name: "SelectedOffer",
  components: {AssignedPeople},
  props: {
    offer: JSON
  },

  watch: {
    async offer() {
      console.log("offer changed");
      this.subscribers = await this.$store.getters.getSubscribersOfOffer(this.offer.id);
      let user = await this.$store.getters.getAuthUser;
      this.assigned = this.subscribers.some(s => s.id === user.id);
      this.owner = this.offer.owner.id === user.id;
      this.error = '';
      console.log("Subscribers: " + this.subscribers);
    }
  },

  data() {
    return {
      error: '',
      subscribers: '',
      assigned: false,
      owner: false,
    }
  },


  computed: {
    getError() {
      return this.error;
    },

    getAssigned() {
      return this.getOwnedOffers();
    },

    alreadyAssigned() {
      return this.$store.getters.isAuthenticated && this.assigned;
    },

    getSubscribers() {
      return this.subscribers;
    },
    isOwner() {
      return this.owner;
    },

    getOfferId() {
      return this.offer.id;
    },

    isLogged() {
      let isAuth = this.$store.getters.isAuthenticated;
      console.log("Checking auth: " + isAuth);
      return isAuth;
    },

  },

  methods: {





    getOwnedOffers() {
      fetch("api/offers/ofUser/" + this.offer.owner.id)
          .then((result) => result.json())
          .then((data) => {
            return data;
          })
    },

    isEmpty(s) {
      return (!s || s.length === 0);
    },

    async onAssign() {
      let token = this.$cookies.get("token");
      let user = await this.fetchApiUser(token);

      if (this.isEmpty(user)) {
        this.error = "You have to log first!";
        return;
      }

      if (!this.isEmpty(user.offers) && user.offers.some(o => o.id == this.offer.id)) {
        this.error = "You are already assigned";
        return;
      }

      let userCredits = await this.$store.getters.getCredits(user.id);
      if (this.offer.price > userCredits) {
        this.error = "Not enough credits!";
        return;
      }

      await this.$store.getters.addCredits(user.id, -this.offer.price);

      await this.$store.getters.addSubscriptions(user.id, this.offer.id);
      this.assigned = true;
      this.$store.commit('addSubscribed', this.offer);

      this.$store.commit('setCatchedCredits', userCredits - this.offer.price);
    },

    async fetchApiUser(token) {
      const response = await fetch("api/auth/authenticate", {
        method: 'POST',
        headers: {
          "token": token
        }
      });

      return await response.json();
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

  font-family: "Nunito Sans", sans-serif;
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
</style>