<template>

  <br/>
  <br/>

  <div class="container-fluid">
    <div class="row justify-content-center">
      <div class="col-2">
        <!--  -->


        </div>
        <div id="mySidebar" class="sidebar">
          <a href="javascript:void(0)" class="closebtn" v-on:click="closeNav">×</a>

          <div class="pt-5 mt-5" style="text-align: center">

          <div v-if="selectedOffer">
            <SelectedOffer
                :offer="currentOffer"
            ></SelectedOffer>
          </div>
        </div>

      </div>
      <div class="col">

        <!-- Search engine -->
        <div class="align-middle">
          <div class="pr-5 pl-5 pt-2 pb-2">
          <input
              class="form-control text-center mr-sm-2 w-100 input-lg"
              type="search"
              placeholder="Type to search specific course"
              aria-label="Search"
              v-model="userFilter"
              v-on:input="filterOffers">
          </div>
        </div>

        <br/>
        <br/>
        <br/>


        <!-- Categories -->
        <Categories @categoryChanged="filterByCategory"/>

        <br/>

        <div class="container-fluid">
          <div class="row no-gutters">
            <div class="col" v-for="offer in showOffers" :key="offer.id">
              <OfferCard
                :offer="offer"
                @onSelected="OnSelectedOffer"
              />
            </div>

          </div>
        </div>


      </div>
      <div class="col-2">
        <!--  -->
        <Account></Account>
      </div>
    </div>
  </div>

</template>

<script>
import Categories from "@/components/Categories";
import OfferCard from "@/components/OfferCard";
import SelectedOffer from "@/components/SelectedOffer";
import Account from "@/components/account/Account";


export default {
  name: 'Search',
  components: {
    Account,
    SelectedOffer,
    OfferCard,
    Categories
  },


  data() {
    return {
      selectedOffer: '',
      offers: [],
      showOffers: [],
      checkedCategories: [],
      userFilter: ''
    }
  },

  computed: {
    currentOffer() {
      return this.selectedOffer;
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
    },

    OnSelectedOffer(offer){
      this.closeNav();
      this.selectedOffer = offer;
      console.log("selected " + this.selectedOffer);
      this.openNav();
    },


    openNav() {

      document.getElementById("mySidebar").style.width = "100%";
      document.getElementById("main").style.marginLeft = "100%";
    },

    closeNav() {
        document.getElementById("mySidebar").style.width = "0";
        document.getElementById("main").style.marginLeft= "0";
    }
  }

}
</script>

<style scoped>
.sidebar {
  height: 100%;
  width: 0;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
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
  transition: margin-left .5s;
  padding: 16px;
}

/* On smaller screens, where height is less than 450px, change the style of the sidenav (less padding and a smaller font size) */
@media screen and (max-height: 450px) {
  .sidebar {padding-top: 15px;}
  .sidebar a {font-size: 18px;}
}
</style>