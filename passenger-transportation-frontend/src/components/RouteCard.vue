<template>
  <div class="route-card">
    <div class="route-details">
      <h3>{{ route.departureCity }} → {{ route.arrivalCity }}</h3>
      <p>Время: {{ route.departureTime }} - {{ route.arrivalTime }}</p>
      <p>Цена: {{ route.price }} руб</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    route: Object,
  },
};
</script>

<style scoped>
</style>
