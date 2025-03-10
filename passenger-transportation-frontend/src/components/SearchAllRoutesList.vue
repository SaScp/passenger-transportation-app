<template>
  <section class="find-all-routes">
    <RouteCard v-for="route in routes" :key="route.id" :route="route"/>
    <div class="swiper">
      <button @click="prev" v-if="is_find===true">Предыдущая</button>
      <a>{{ page_num + 1}}</a>
      <button @click="next" v-if="is_find===true">Следующая</button>
    </div>
  </section>
</template>

<script>
import RouteCard from "@/components/RouteCard.vue";
import {ref} from "vue";
import {getAllRoutes} from "@/api.js";

export default {
  components: {RouteCard},
  data() {
    return {
      routes: ref([]),
      err: '',
      page_num: ref(0),
      page_size: ref(5),
      is_find: false,
      is_zero: false
    };
  },
  mounted() {
    this.findAll()
  },
  methods: {
    async findAll() {
      try {
        const params = {}
        if (this.page_num) params.page_num = this.page_num
        if (this.page_size) params.page_size = this.page_size
        const response = await getAllRoutes(params);
        this.is_find = true
        if (response.data.length <= 0) {
          this.page_num--;
        } else {
          this.routes = response.data;
        }


      } catch (err) {
        err = err.response ? err.response.data.detail : 'Ошибка при поиске маршрутов';
      }
    },
    async next() {
      if (this.routes.length !== 0) {
        this.page_num++;
        await this.findAll()
      }
    },
    async prev() {
      if (this.page_num > 0) {
        this.page_num--;
        this.is_zero = false;
        await this.findAll()
      }
    }
  }
}
</script>

<style>
.swiper {
  display: flex;
  justify-content: center;
  align-items: center;
}
.find-all-routes {
  margin: 20px;
}
</style>