<template>
  <div class="card" style="width: 18rem;">
<!--    <img class="card-img-top" src="..." alt="Card image cap">-->
    <div class="card-body">
      <div class="card-title">
      <h5 >{{offer.title}}</h5>

      <p><i>from: {{offer.owner.name}} {{offer.owner.surname}}</i></p>
      </div>
      <p class="card-text">{{offer.description}}</p>

      <div class="float-right">At price: {{offer.price}}</div>
      <a href="#" class="btn btn-primary">Go somewhere</a>
    </div>
  </div>
</template>

<script>
export default {
  name: "OfferCard",

  props: {
    offer: JSON
  }
}
</script>

<style scoped>

</style>