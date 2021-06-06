<template>
  <div v-if="loading">
    <div class="spinner-border" role="status">
      <span class="sr-only">Loading...</span>
    </div>
  </div>
  <div v-else>
    <tr v-for="row in getSubscribed()" :key="row.id">
      <button class="btn btn-outline-primary m-1">
        <td>{{ row.title }}</td>
      </button>
    </tr>
  </div>
</template>

<script>
export default {
  name: "Assigned",


  data() {
    return {
      loading: false
    }
  },

  mounted() {

  },

  methods: {
    getSubscribed() {
      return this.$store.getters.getSubscribed;
    },
  }
}
</script>

<style scoped>

</style>