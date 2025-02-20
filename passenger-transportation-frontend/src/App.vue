<template>
  <div id="app">
    <router-link to="/cancel-booking">cancel-booking</router-link>
    <hr/>
    <router-link to="/search-route">search-route</router-link>
    <hr/>
    <router-link to="/create-booking">create-booking</router-link>
    <hr>
    <router-link to="/search-booking">search-booking</router-link>
    <hr/>
    <router-link to="/find-all-routes">find-all-routes</router-link>
    <router-view/>
  </div>
</template>
<script>

import { defineComponent } from 'vue';

export default defineComponent({
  name: 'App'
});

</script>
<style>

.booking-form {
  display: flex;
  flex-flow: column;

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