<template>
  <section class="booking-section">
    <h2>Создание бронирования</h2>
    <form @submit.prevent="createBooking" class="form">
      <div class="form-group">
        <input v-model="phone" type="text" placeholder="Номер телефона" class="form-control" />
      </div>
      <div class="form-group">
        <input v-model="routeId" type="text" placeholder="ID маршрута" class="form-control" />
      </div>
      <button type="submit" class="btn btn-success">Забронировать</button>
    </form>

    <div v-if="bookingMessage" class="success-message">{{ bookingMessage }}</div>
  </section>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      phone: '',
      routeId: '',
      bookingMessage: '',
    };
  },
  methods: {
    async createBooking() {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/booking/create', {
          numberPhone: this.phone,
          routeId: this.routeId,
        });
        if (response.status === 201) {
          this.bookingMessage = 'Бронирование успешно создано!';
        }
      } catch (err) {
        this.bookingMessage = 'Ошибка при создании бронирования';
      }
    },
  },
};
</script>

<style scoped>

</style>
