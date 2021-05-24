<template>
  <h1>{{ msg }}</h1>
  <b>sdfs</b>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: ''
    }
  },
  mounted() {
    fetch("/categories/")
        .then((response) => response.json())
        .then((data) => {
          this.msg = data;
        });
  }
}
</script>