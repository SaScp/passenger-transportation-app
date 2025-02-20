<template>
  <section class="cancel-section">
    <h2>Отмена бронирования</h2>
    <form @submit.prevent="cancelBooking" class="form">
      <div class="form-group">
        <input v-model="bookingId" type="text" placeholder="ID бронирования" class="form-control" />
      </div>
      <button type="submit" class="btn btn-danger">Отменить бронирование</button>
    </form>
    <div v-if="cancelMessage" class="error-message">{{ cancelMessage }}</div>
  </section>
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

<style scoped>
</style>
