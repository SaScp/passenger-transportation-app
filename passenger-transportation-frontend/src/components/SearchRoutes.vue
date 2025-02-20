<template>
  <section class="search-section">

    <aside>
      <form @submit.prevent="searchRoutes" class="form">
        <h2>Поиск маршрутов</h2>
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
      <hr class="line-search">
    </aside>
    <div v-if="routes.length" class="routes-list">
      <RouteCard v-for="route in routes" :key="route.id" :route="route" :is-finder="true" />
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
      type: null,
      from: null,
      to: null,
      time: null,
      routes: [],
      error: '',
    };
  },
  mounted() {
    this.searchRoutes()
  },
  methods: {
    async searchRoutes() {
      try {
       /* const response = await axios.get('http://localhost:8080/api/v1/booking/find', {
          params: {
            type: this.type,
            from: this.from,
            to: this.to,
            time: this.time,
          },
        });
        this.routes = response.data;
        this.type = null;
        this.from = null;
        this.to = null;
        this.time =  null
        this.error = '';*/
        const params = {};
        if (this.type) params.type = this.type
        if (this.from) params.from = this.from
        if (this.to) params.to = this.to
        if (this.time) params.time = this.time
        const response = await axios.get('http://localhost:8080/api/v1/booking/find',{params})

        this.routes = response.data;
        this.error = '';
        if (Object.keys(params).length) {
          this.type = null;
          this.from = null;
          this.to = null;
          this.time =  null;
        }
      } catch (err) {
        this.error = err.response ? err.response.data.detail : 'Ошибка при поиске маршрутов';
        this.routes = [];
      }
    },
  },
};
</script>
<style>
.form {
  display: flex;
  flex-flow: column;
  align-items: center;
  margin: 45px;
  min-width: 300px;
}

.search-section {
  display: flex;
  justify-content: center;
  height: 100%;
}
.routes-list {
  display: flex;
  flex-wrap: wrap;
  margin: 30px;

}

aside {
  position: relative;
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
</style>
