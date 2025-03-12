<template>

  <div id="app">

    <header class="main-header">
      <nav class="nav-menu">
        <router-link to="/search-route" class="nav-item" active-class="active-link">
          🔍 Поиск маршрутов
        </router-link>
        <router-link to="/find-all-routes" class="nav-item" active-class="active-link">
          📋 Все маршруты
        </router-link>
        <router-link to="/search-booking" class="nav-item" active-class="active-link">
          📞 Поиск брони
        </router-link>
      </nav>
    </header>
    <router-view />
  </div>
</template>
<script>

import {defineComponent} from 'vue';

export default defineComponent({
  name: 'App',
});
</script>
<style>

*{
  font-family: cursive;
  margin: 0;
}
.main-header {
  display: flex;
  justify-content: center;
  padding: 20px 20px;
  border: 2px solid #ccc;
  border-radius: 15px;
  background: #11263A;
}

.nav-menu {
  display: flex;
  gap: 20px;
}

.nav-item {
  text-decoration: none;
  color: white;
  font-size: 16px;
  display: flex;
  align-items: center;
  gap: 5px;
}

.active-link {
  font-weight: bold;
  border-bottom: 2px solid white;
  color: white;
}

h2 {
  text-align: center;
  font-family: cursive;
}

input {
  padding: 5px;
  margin: 10px 0;
  border-radius: 4px;
  font-family: cursive;
  border: 1px solid #ccc;
}
button{
  margin: 15px;
  padding: 10px;
  background-color: #59ABE8;
  border-radius: 15px;
  font-family: cursive;
  max-width: 100%;
  a {
    text-decoration: none;
    color: #333;
  }
}

</style>