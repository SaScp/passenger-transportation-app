<template>
  <div id="app">
    <header class="main-header">
      <div class="in-header">
        <router-link to="/cancel-booking">Отменить бронь</router-link>
      </div>
      <div class="in-header">
        <router-link to="/search-route">Поиск маршрутов по параметрам</router-link>
      </div>
      <div class="in-header">
        <router-link to="/create-booking">Забронировать</router-link>
      </div>
      <div class="in-header">
        <router-link to="/search-booking">Найти брони по телефону</router-link>
      </div>
      <div class="in-header">
        <router-link to="/find-all-routes">Найти все маршруты</router-link>
      </div>
    </header>

    <router-view/>
  </div>
</template>
<script>

import {defineComponent} from 'vue';

export default defineComponent({
  name: 'App'
});

</script>
<style>
.main-header {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  margin: 0;
  background-color: #2c3e50;
}

.in-header {
  margin: 15px;
  padding: 10px;
  background-color: #f2f2f2;
  border-radius: 15px;
  font-family: Avenir, sans-serif;

  a {
    text-decoration: none;
    color: #333;
  }
}

h2 {
  text-align: center;
}

input {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border-radius: 4px;
  border: 1px solid #ccc;
}


</style>