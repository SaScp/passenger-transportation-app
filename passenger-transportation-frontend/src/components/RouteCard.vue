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

<style>
.route-card {
  display: flex;
  flex-flow: row;
  justify-content: space-between;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin: 5px 0;
}
.route-details {
  display: flex;
  flex-flow: column;
  margin: 0;
}
</style>
