<template>
  <div>
    <h2>Поиск маршрутов</h2>
    <form @submit.prevent="searchRoutes">
      <input v-model="type" type="text" placeholder="Тип транспорта" />
      <input v-model="from" type="text" placeholder="Откуда" />
      <input v-model="to" type="text" placeholder="Куда" />
      <input v-model="time" type="text" placeholder="Дата и время" />
      <button type="submit">Найти маршруты</button>
    </form>

    <ul v-if="routes.length">
      <li v-for="route in routes" :key="route.id">
        {{ route.departureCity }} - {{ route.arrivalCity }} <br />
        Время: {{ route.departureTime }} - {{ route.arrivalTime }} <br />
        Цена: {{ route.price }} руб
      </li>
    </ul>
    <div v-if="error">{{ error }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      type: null,
      from: null,
      to: null,
      time: null,
      routes: [],
      error: '',
    };
  },
  methods: {
    async searchRoutes() {
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/booking/find`, {
          params: {
            type: this.type,
            from: this.from,
            to: this.to,
            time: this.time,
          },
        });
        this.routes = response.data;
        this.error = '';
      } catch (err) {
        this.error = 'Ошибка при поиске маршрутов';
        this.routes = [];
      }
    },
  },
};
</script>
