<script>
export default {
  name: "ApiController",

  methods: {
    fetchOffers(page, size, query) {
      fetch("api/offers?pageNum=" + page + "&size=" + size + "&nameFilter=" + query)
          .then((response) => response.json())
          .then((data) => data);
    },

    fetchCategories() {
      fetch("api/categories/all")
          .then((response) => response.json())
          .then((data) => data);
    },
  }
}

</script>

<style scoped>

</style>