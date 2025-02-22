<template>
  <section class="search-booking-section">
    <aside>
      <h2>Поиск поездок</h2>
      <form @submit.prevent="searchBooking" class="form-booking">
        <div class="form-phone">
          <input v-model="phone" type="text" placeholder="Номер телефона" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-primary">Найти поездки</button>
      </form>
    <hr class="line-search">
      </aside>
    <div v-if="bookings.length" class="booking-list">
      <BookingCard v-for="booking in bookings" :key="booking.id" :booking="booking"/>
    </div>
    <div class="err-message" v-if="err">{{err}}</div>
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
        this.err = err.response.status === 404 ? 'Не найдено' : 'Ошибка при поиске поездок';
      }

    }
  }
}
</script>

<style scoped>
aside {
  position: relative;
}
.err-message {
  margin: 120px;
}

.search-booking-section {
  display: flex;
  justify-content: center;
  flex-flow: row;
  height: 100%;
}
.booking-list {
  display: flex;
  flex-wrap: wrap;
  margin: 30px;

}
.form-booking {
  display: flex;
  flex-flow: column;
  align-items: center;
  margin: 30px;
  min-width: 300px;
}
.line-search {
  width: 2px; /* Ширина линии */
  background-color: #000; /* Цвет линии */
  border: none; /* Убираем стандартную границу */
  height: 100vh;
  position: absolute;
  top: 0;
  right: 0;
}

.booking-list {
  display: flex;
  flex-flow: column;
  flex-wrap: wrap;
  margin: 30px;

}
</style>