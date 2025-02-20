<template>
  <div>
    <h2>Создание бронирования</h2>
    <form @submit.prevent="createBooking">
      <input v-model="phone" type="text" placeholder="Номер телефона" />
      <input v-model="routeId" type="text" placeholder="ID маршрута" />
      <button type="submit">Забронировать</button>
    </form>
    <div v-if="bookingMessage">{{ bookingMessage }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      phone: null,
      routeId: null,
      bookingMessage: null,
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
