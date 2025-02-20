<template>
  <section>
    <form @submit.prevent="findAll" class="form">
      <button type="submit" class="btn btn-primary">Найти все маршруты</button>
    </form>
    <RouteCard v-for="route in routes" :key="route.id" :route="route"/>
    <form @submit.prevent="next(routes)" class="form" v-if="is_find === true">
      <button type="submit">next</button>
    </form>

    <form @submit.prevent="prev" class="form" v-if="is_find === true">
      <button type="submit">pref</button>
    </form>
  </section>
</template>

<script>
import axios from "axios";
import RouteCard from "@/components/RouteCard.vue";
import {ref} from "vue";
export default {
  components: {RouteCard},
  data() {
    return {
      routes: ref([]),
      err: '',
      page_num: ref(0),
      page_size: ref(20),
      is_find: false
    };
  },
  methods: {
    async findAll() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/booking/find-all', {
          params:{
            page_num: this.page_num,
            page_size: this.page_size
          }
        });
        this.is_find = true
        this.routes = response.data;
      } catch (err) {
        err = err.response ? err.response.data.detail : 'Ошибка при поиске маршрутов';
      }
    },
    async next() {
      if (this.routes.length !== 0){
        this.page_num++;
        await this.findAll()
      }
    },
    async prev() {
      if (this.page_num > 0){
        this.page_num--;
        await this.findAll()
      }
    }
  }
}
</script>

<style>

</style>