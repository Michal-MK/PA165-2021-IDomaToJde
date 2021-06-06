<template>

  <h6 class="d-flex align-items-center mb-3">Assigned people</h6>

  <hr/>
  <div v-for="user in subscribers" :key="user.id">
    <ul class="list-group list-group-flush">
      <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
        <h6 class="mb-0">
          <pre style="display: inline"/>
          Username
        </h6>
        <span class="text-secondary">{{ user.username }}</span>
      </li>

      <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
        <h6 class="mb-0">
          <pre style="display: inline"/>
          Phone
        </h6>
        <span class="text-secondary">{{ user.contactInfo.phoneNumber }}</span>
      </li>

      <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
        <h6 class="mb-0">
          <pre style="display: inline"/>
          Email
        </h6>
        <span class="text-secondary">{{ user.contactInfo.email }}</span>
      </li>
    </ul>
    <hr/>
  </div>
</template>

<script>
export default {
  name: "AssignedPeople",

  props: {
    offerId: Number,
  },

  watch: {
    async offerId(){
      this.subscribers = await this.$store.getters.getSubscribersOfOffer(this.offerId);
    }
  },

  async mounted() {
    this.subscribers = await this.$store.getters.getSubscribersOfOffer(this.offerId);
  },

  data() {
    return {
      subscribers: []
    }
  },

  methods: {
    async getSubscribers(){
      let assigned = await this.$store.getters.getSubscribersOfOffer(this.offerId);
      console.log("AssignedPeople.getSubscribers(): " + JSON.stringify(assigned ));
      return assigned ;
    }
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
</style>