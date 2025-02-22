<template>
  <header class="search-header">
    <div class="header-form-group">
      <input v-model="from" type="text" placeholder="Откуда" class="form-control" />
    </div>
    <div>
      <svg class="arrow-right-4" viewBox="0 0 100 85">
        <polygon points="58.263,0.056 100,41.85 58.263,83.641 30.662,83.641 62.438,51.866 0,51.866 0,31.611 62.213,31.611 30.605,0 58.263,0.056" />
      </svg>
    </div>
    <div class="header-form-group">
      <input v-model="to" type="text" placeholder="Куда" class="form-control" />
    </div>
  </header>
  <section class="search-section">
    <aside>
      <div class="find-el">
        <div class="form-group">
          <select v-model="type" class="type-group">
            <option class="inner-type-group" v-for="typeEl in types" v-bind:value="typeEl.transportType">{{typeEl.transportType}}</option>
          </select>
        </div>
        <button @click="searchRoutes">Найти маршруты</button>
      </div>
      <hr class="line-search">
    </aside>
    <div v-if="routes.length" class="routes-list">
      <RouteCard v-for="route in routes" :key="route.id" :route="route" :is-finder="true" />
    </div>
    <div v-if="routes.length === 0" class="routes-list-not-found">
      <div>
        ничего не найдено
      </div>
    </div>
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
      types: []
    };
  },
  mounted() {
    this.searchRoutes()
    this.seachType()
  },
  methods: {
    async searchRoutes() {
      try {

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
    async seachType() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/booking/find-types')
        this.types = response.data;
      } catch (err) {
        this.error = err.response ? err.response.data.detail : 'Ошибка при поиске маршрутов';
        this.routes = [];
      }
    }
  },
};
</script>
<style>

.type-group {
  display: flex;
  align-content: center;
  padding: 10px 120px 10px 20px;
  margin: 10px 0;
  border-radius: 4px;
  border: 1px solid #ccc;
}

.arrow-right-4 {
  margin: 10px;
  width: 80px;
  height: 20px;
  cursor: pointer;
}
.routes-list-not-found {
  margin: 120px;
}
.search-header {
  display: flex;
  justify-content: center;
  align-items: center;
}
.find-el {
  display: flex;
  flex-flow: column;
  align-items: center;
  margin: 30px;
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
