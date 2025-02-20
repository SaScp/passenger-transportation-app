<template>
  <section class="search-section">
    <h2>Поиск маршрутов</h2>
    <form @submit.prevent="searchRoutes" class="form">
      <div class="form-group">
        <input v-model="type" type="text" placeholder="Тип транспорта" class="form-control" />
      </div>
      <div class="form-group">
        <input v-model="from" type="text" placeholder="Откуда" class="form-control" />
      </div>
      <div class="form-group">
        <input v-model="to" type="text" placeholder="Куда" class="form-control" />
      </div>
      <div class="form-group">
        <input v-model="time" type="text" placeholder="Дата и время (например, 04/10/2025-08:00:00)" class="form-control" />
      </div>
      <button type="submit" class="btn btn-primary">Найти маршруты</button>
    </form>

    <div v-if="routes.length" class="routes-list">
      <RouteCard v-for="route in routes" :key="route.id" :route="route" />
    </div>

    <div v-if="error" class="error-message">{{ error }}</div>
  </section>
</template>

<script>
import axios from 'axios';
import RouteCard from './RouteCard.vue';

export default {
  components: {
    RouteCard,
  },
  data() {
    return {
      type: '',
      from: '',
      to: '',
      time: '',
      routes: [],
      error: '',
    };
  },
  methods: {
    async searchRoutes() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/booking/find', {
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
        this.error = err.response ? err.response.data.detail : 'Ошибка при поиске маршрутов';
        this.routes = [];
      }
    },
  },
};
</script>

<style scoped>
</style>
