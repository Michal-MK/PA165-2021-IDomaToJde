<template>

  <br/>
  <br/>

  <div class="container-fluid">
    <div class="row justify-content-center">
      <div class="col-1">
        <!--  -->

      </div>
      <div class="col">

        <!-- Search engine -->
        <div class="align-middle">
          <h1 class="text-center">Search for course</h1>
          <br/>
          <div class="pr-5 pl-5 pt-2 pb-2">
          <input
              class="form-control text-center mr-sm-2 w-100 input-lg"
              type="search"
              placeholder="Search"
              aria-label="Search"
              v-model="userFilter"
              v-on:input="filterOffers">
          </div>
        </div>

        <!-- Categories -->
        <br/>
        <Categories @categoryChanged="filterByCategory"/>

        <br/>

        <div class="container-fluid">
          <div class="row">
            <div class="col m-1" v-for="offer in showOffers" :key="offer.id">
              <OfferCard
                :offer="offer"
              />
            </div>

          </div>
        </div>


      </div>
      <div class="col-1">
        <!--  -->

      </div>
    </div>
  </div>

</template>

<script>
import Categories from "@/components/Categories";
import OfferCard from "@/components/OfferCard";


export default {
  name: 'Search',
  components: {
    OfferCard,
    Categories
  },


  data() {
    return {
      offers: [],
      showOffers: [],
      checkedCategories: [],
      userFilter: ''
    }
  },
  mounted() {
    fetch("api/offers/all")
        .then((response) => response.json())
        .then((data) => {
          this.offers = data;
          this.showOffers = data;
        });

    fetch("api/categories/")
        .then((response) => response.json())
        .then((data) => {
          this.checkedCategories = data.map(c => c.name);
        });
  },

  methods: {
    filterOffers() {
      this.filterAll();
    },

    filterByCategory(categories) {
      this.checkedCategories = categories;
      this.filterAll();
    },

    filterAll(){
      let byCategory = this.offers.filter(d => this.checkedCategories.includes(d.category.name));
      this.showOffers = byCategory.filter(d => d.title.toLowerCase().includes(this.userFilter.toLowerCase()))
    }
  }

}
</script>

<style scoped>

</style>