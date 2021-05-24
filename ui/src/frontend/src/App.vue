<template>
  <Navbar/>
  <Search/>
</template>

<script>
import Navbar from "@/components/Navbar";
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