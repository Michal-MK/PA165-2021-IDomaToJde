
<script>
export default {
  name: "ApiController",

  methods: {
    fetchOffers() {
      fetch("api/offers/all")
          .then((response) => response.json())
          .then((data) => {
            return data;
          });
    },

    fetchCategories() {
      fetch("api/categories/")
          .then((response) => response.json())
          .then((data) => {
            return data;
          });
    }
  }

}
</script>

<style scoped>

</style>