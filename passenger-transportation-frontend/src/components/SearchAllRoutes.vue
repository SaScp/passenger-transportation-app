<template>
  <section>
    <form @submit.prevent="findAll" class="form">
      <button type="submit" class="btn btn-primary">Найти все маршруты</button>
    </form>
    <RouteCard v-for="route in routes" :key="route.id" :route="route" />
  </section>
</template>

<script>
import axios from "axios";
import RouteCard from "@/components/RouteCard.vue";
export default {
  components: {RouteCard},
  data() {
    return {
      routes: [],
      err: ''
    };
  },
  methods: {
    async findAll() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/booking/find-all');
        this.routes = response.data;
      } catch (err) {
        err = err.response ? err.response.data.detail : 'Ошибка при поиске маршрутов';
      }


    }
  }
}
</script>

<style>

</style>