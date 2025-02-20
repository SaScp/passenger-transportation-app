<template>
  <section class="search-booking-section">
      <h2>Поиск поездок</h2>
      <form @submit.prevent="searchBooking" class="form">
        <div class="form-phone">
          <input v-model="phone" type="text" placeholder="Номер телефона" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-primary">Найти поездки</button>
      </form>
      <div v-if="bookings.length" class="booking-list">
        <BookingCard v-for="booking in bookings" :key="booking.id" :booking="booking"/>
      </div>
      <div v-if="err" class="error-message">{{ err }}</div>
  </section>
</template>

<script>
import BookingCard from "@/components/BookingCard.vue";
import axios from "axios";
import RouteCard from "@/components/RouteCard.vue";
export default {
  components: {RouteCard, BookingCard},
  data() {
    return {
      phone: '',
      bookings: [],
      err: ''
    };
  },
  methods:{
    async searchBooking() {
      try {
        const response = await axios.get("http://localhost:8080/api/v1/booking/find-by-phone", {
          params: {
            phone: this.phone
          }
        });
        this.bookings = response.data;
      } catch (err) {
        this.err = err.response ? err.response.data.detail : 'Ошибка при поиске поездок';
      }

    }
  }
}
</script>

<style scoped>

</style>