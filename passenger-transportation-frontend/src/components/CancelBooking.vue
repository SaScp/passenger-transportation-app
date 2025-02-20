<template>
  <div>
    <h2>Отмена бронирования</h2>
    <form @submit.prevent="cancelBooking">
      <input v-model="bookingId" type="text" placeholder="ID бронирования" />
      <button type="submit">Отменить бронирование</button>
    </form>
    <div v-if="cancelMessage">{{ cancelMessage }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      bookingId: '',
      cancelMessage: '',
    };
  },
  methods: {
    async cancelBooking() {
      try {
        const response = await axios.delete(`http://localhost:8080/api/v1/booking/revoke?booking_id=${this.bookingId}`);
        if (response.status === 200) {
          this.cancelMessage = 'Бронирование успешно отменено';
        }
      } catch (err) {
        this.cancelMessage = 'Ошибка при отмене бронирования';
      }
    },
  },
};
</script>
