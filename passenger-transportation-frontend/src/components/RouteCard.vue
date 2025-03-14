<template>
  <div class="route-card">
    <div class="route-details">
      <h3>{{ route.departureCity.label }} → {{ route.arrivalCity.label}}</h3>
      <p>Время Отбытия: {{ route.departureTime }}</p>
      <p>Время Прибытия: {{ route.arrivalTime }}</p>
      <p>Тип: {{ route.type }}</p>
      <p>Цена: {{ route.price }} руб</p>
      <div class="booking-button" v-if="isFinder">
        <button v-if="!isFormVisible" class="inner-booking-button" @click="toggleForm">Забронировать</button>
        <div v-if="isFormVisible">
          <form @submit.prevent="submitData(route.id)">
            <div class="form-group">
              <input v-model="phone" type="text" placeholder="Ваш номер телефона" class="form-control"/>
              <div v-if="isNull" class="error-message">Введите номер телефона</div>
            </div>
            <button type="submit" class="inner-booking-button">Забронировать</button>
          </form>
        </div>
        <div v-if="isFind">
          <button @click="highlightRoute">Выделить маршрут</button>
        </div>

      </div>
    </div>

   <Modal :isOpen="isModalOpen" :message="message" :statusCode="statusCode" @close="isModalOpen = false" id="modal">
    </Modal>
  </div>
</template>
<!--display: flex;
    flex-flow: column;-->

<script>
import {ref} from "vue";
import axios from "axios";
import route from "@/route.js";
import {createBooking} from "@/api.js";
import Modal from "@/components/Modal.vue";



export default {
  components: { Modal},
  props: {
    route: Object,
    isFinder: Boolean,
    isFind: Boolean
  },
  data() {
    return {
      isFormVisible: false,
      phone: null,
      err: '',
      isNull: false,
      isModalOpen: false,
      statusCode: null,
      message: ''
    };
  },
  methods: {
    toggleForm() {
      this.isFormVisible = !this.isFormVisible
    },
    async submitData(routeId) {
      this.isNull = this.phone === null;
      if (this.isNull) {
        return;
      }
      const params = {}
      if (this.phone) params.numberPhone = this.phone
      if (routeId) params.routeId = routeId

      await createBooking(params)
          .then(response => {
                this.phone = null
                this.isFormVisible = false
               console.log(response.status)
                this.openModal(response.status)
              }
          )
          .catch(error => {
                this.err = error.response.data.detail
                this.openModal(error.response.status)
              }
          )
    },
    openModal(code) {
      console.log(200 <= code < 300)
      if (code >= 200 && code < 300) {
        this.message = 'Вы успешно забронировали'
      }
      else if (code >= 400 && code < 600) {
        this.message = this.err;
      }
      console.log(code)
      this.statusCode = code;
      this.isModalOpen = true;
    },
    highlightRoute() {
      this.$emit("highlight-route", this.route.routeSteps.map(step => step.edgeId));
    }
  }
};

</script>

<style>
.route-card {
  display: flex;
  flex-flow: row;
  justify-content: space-between;
  padding: 7px;
  margin: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.error-message {
  color: red;
}

.route-details {
  display: flex;
  flex-flow: column;
  margin-left: 20px;
  margin-right: 20px;

  p {
    margin: 5px;
  }
}

.form-control {
  max-width: 200px;
}

.booking-button {
  display: flex;
  justify-content: center;
  align-items: center;
}

.inner-booking-button {
  border-radius: 15px;
  padding: 10px;

}
</style>
