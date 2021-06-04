<template>
  <h1 class="text-center pt-5" style="font-size: 120px">You can do it home</h1>
</template>

<script>
export default {
  name: "navbar",

  async mounted() {

    let user = await this.$store.getters.getAuthUser;
    console.log("Ubla bla " + user);

  },

  methods: {
  }
}
</script>

<style scoped>

</style>