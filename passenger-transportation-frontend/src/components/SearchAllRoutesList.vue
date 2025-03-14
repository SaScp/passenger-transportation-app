<template>
  <section class="find-all-routes">
    <div class="routes" id="routes">
      <div class="route-date" v-for="(routes, date) in groupedRoutes" :key="date">
        <h2>📅 {{ date }}</h2>
        <RouteCard
            v-for="route in routes"
            :key="route.id"
            :route="route"
            :is-finder="true"
            :is-find="false"
        />
      </div>
    </div>

    <div class="swiper" v-if="is_find">
      <button @click="prev" :disabled="page_num === 0">Предыдущая</button>
      <a>{{ page_num + 1 }}</a>
      <button @click="next" :disabled="!hasMore">Следующая</button>
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
      page_num: 0,
      page_size: 6,
      is_find: false,
      is_zero: false,
      hasMore: true
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

        if (response.data.length <= 0) {
          if (this.page_num > 0) this.page_num--;
          this.routes = [];
          this.is_find = false;
          this.hasMore = false;
        } else {
          this.is_find = true;
          this.routes = response.data;
          this.hasMore = response.data.length === this.page_size;
        }


      } catch (err) {
        err = err.response ? err.response.data.detail : 'Ошибка при поиске маршрутов';
      }
    },
    async next() {

      if (this.hasMore) {
        this.page_num++;
        await this.findAll();
      }
    },
    async prev() {
      if (this.page_num > 0) {
        this.page_num--;
        this.hasMore = true;
        await this.findAll();
      }
    }
  },
  computed : {
    groupedRoutes() {
      const grouped = {};
      this.routes.forEach(route => {
        const date = route.departureTime.split("T")[0]; // Берём только дату (ГГГГ-ММ-ДД)
        if (!grouped[date]) {
          grouped[date] = [];
        }
        grouped[date].push(route);
      });
      return grouped;
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
#app {
  display: flex;
  flex-flow: column;
  justify-content: center;
  align-items: center;
}
#routes {
   display: grid;
   grid-template-columns: auto auto auto;
 }
.find-all-routes {
  display: flex;
  margin: 20px;
  grid-column: auto;
  flex-flow: column;
}

</style>