
<template>
  <div class="booking-card">
    <div class="booking-details">
      <p>Время брони: {{ booking.bookingTime }}</p>
      <p>Статус: {{ booking.status }}</p>
    </div>
  </div>

</template>

<script>
export default {
  props: {
    booking: Object,
  },
};
</script>
<style scoped>

</style>