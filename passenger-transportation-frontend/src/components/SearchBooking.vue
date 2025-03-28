<template>
  <section class="search-booking-section">
    <div class="form-booking">
      <h2>Поиск поездок</h2>
      <form @submit.prevent="searchBooking" class="inner-form-booking">
        <div class="form-phone">
          <input v-model="phone" type="text" placeholder="Номер телефона" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-primary filter-btn">Найти поездки</button>
      </form>
    </div>
    <hr class="line-search">
    <div v-if="bookings.length" class="booking-list">
      <BookingCard v-for="booking in bookings" :key="booking.id" :booking="booking"/>
    </div>
    <Modal :isOpen="isModalOpen" :message="message" :statusCode="statusCode" @close="isModalOpen = false" id="modal">
    </Modal>
  </section>
</template>

<script>
import BookingCard from "@/components/BookingCard.vue";
import RouteCard from "@/components/RouteCard.vue";
import {getBookingsByPhone} from "@/api.js";
import Modal from "@/components/Modal.vue";
export default {
  components: {Modal, RouteCard, BookingCard},
  data() {
    return {
      phone: '',
      bookings: [],
      err: '',
      isModalOpen: false,
      statusCode : null,
      message: ''
    };
  },
  methods: {
    async searchBooking() {
      await getBookingsByPhone(this.phone)
          .then(response => {
            this.bookings = response.data;
            console.log(response.data)
          })
          .catch(err => {
                this.err = err.response.data.detail
                console.log(err.response.data.detail)
                this.openModal(err.response.status)
              }
          );
    },
    async openModal(code) {
      console.log(this.err)
      if (code >= 400 && code < 600) {
        this.message = this.err;
      }
      console.log(code)
      this.statusCode = code;
      this.isModalOpen = true;
    },
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
.find-booking-params {
  display: flex;
  flex-flow: column;
}
.form-booking {
  display: flex;
  flex-flow: column;
  align-items: center;
  margin: 30px;
  min-width: 300px;
  border-radius: 4px;
  border: 1px solid #ccc;
}
.inner-form-booking {
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