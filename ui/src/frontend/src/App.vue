<template>
  <Navbar/>
  <Search/>
</template>

<script>
import Navbar from "@/components/navbar";
import Search from "@/components/Search";

export default {
  name: 'App',
  components: {
    Search,
    Navbar
  }
}
</script>

<style>

</style>