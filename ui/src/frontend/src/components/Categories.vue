<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col" v-for="cat in cats" :key="cat.name">

        <div v-if="checkedCategories.includes(cat.name)">
          <button class="btn my-2 my-sm-0 w-100 btn-success" v-on:click="toggleButton(cat.name)">
            {{ cat.name }}
          </button>
        </div>
        <div v-else>
          <button class="btn my-2 my-sm-0 w-100 btn-outline-success" v-on:click="toggleButton(cat.name)">
            {{ cat.name }}
          </button>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Categories",

  data() {
    return {
      cats: [],
      checkedCategories: [],
      activeClass: 'btn-success',
      inactiveClass: 'btn-outline-success',
      errorClass: 'btn-danger'
    }
  },
  mounted() {
    fetch("api/categories/all")
        .then((response) => response.json())
        .then((data) => {
          this.cats = data;
          this.checkedCategories = this.cats.map(c => c.name);
        });
  },

  methods: {
    toggleButton(categoryName) {
      if (categoryName) {

        if (this.checkedCategories.includes(categoryName)) {
          let index = this.checkedCategories.indexOf(categoryName);
          if (index > -1) {
            this.checkedCategories.splice(index, 1);
          }
        } else {
          this.checkedCategories.push(categoryName);
        }
        this.$emit("categoryChanged", this.checkedCategories);
      }
    }
  }
}


</script>

<style scoped>

</style>