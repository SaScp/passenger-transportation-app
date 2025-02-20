<template>
  <div id="app">
    <SearchRoutes />
    <BookingForm />
    <CancelBooking />
  </div>
</template>

<script>
import SearchRoutes from './components/SearchRoutes.vue';
import BookingForm from './components/BookingForm.vue';
import CancelBooking from './components/CancelBooking.vue';

export default {
  components: {
    SearchRoutes,
    BookingForm,
    CancelBooking,
  },
};
</script>
