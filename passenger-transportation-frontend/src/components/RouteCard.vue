<template>
  <div class="route-card">
    <div class="route-details">
      <h3>{{ route.departureCity }} → {{ route.arrivalCity }}</h3>
      <p>Время Отбытия: {{ route.departureTime }}</p>
      <p>Время Прибытия: {{ route.arrivalTime }}</p>
      <p>Тип: {{ route.transportType }}</p>
      <p>Цена: {{ route.price }} руб</p>
      <div class="booking-button" v-if="isFinder">
        <button v-if="!isFormVisible" class="inner-booking-button" @click="toggleForm">Забронировать</button>
        <div v-if="isFormVisible">
          <form @submit.prevent="submitData(route.id)">
            <div class="form-group">
              <input v-model="phone" type="text" placeholder="Ваш номер телефона" class="form-control" />
              <div v-if="isNull" class="error-message">Введите номер телефона</div>
            </div>
            <button type="submit" class="inner-booking-button">Забронировать</button>
          </form>
          <div v-if="err" class="error-message">{{ err }}</div>
        </div>
      </div>
    </div>

  </div>
</template>
<!--display: flex;
    flex-flow: column;-->
<script>
import {ref} from "vue";
import axios  from "axios";
import route from "@/route.js";
export default {
  props: {
    route: Object,
    isFinder: Boolean,
  },
  data() {
    return {
      isFormVisible: false,
      phone: null,
      err: '',
      isNull : false
    };
  },
  methods : {
    toggleForm() {
      this.isFormVisible = !this.isFormVisible
    },
    async submitData(routeId) {
      try{
        this.isNull = this.phone === null;
        if (this.isNull) {
          return;
        }
        const response = await  axios.post('http://localhost:8080/api/v1/booking/create', {
          numberPhone: this.phone,
          routeId: routeId
        });
        if (response.data.status === 500) {
          err = response.data.detail;
        }
        this.phone = null
        this.isFormVisible = false
      } catch (err) {
        err = "Не удалось забранировать поездку"
      }


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
  p{
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
