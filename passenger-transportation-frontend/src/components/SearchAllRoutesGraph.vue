<template>
  <div class="app-container">
    <div class="info">
      <NetworkGraph :graph="graph" ref="networkGraph" @create-new-route="createNewRoute"/>
      <div class="routes" v-if="routeData">
        <RouteItem
            v-for="route in routeData"
            :key="route.id"
            :route="route"
            :is-finder="true"
            :isFind="true"
            @highlight-route="highlightRoute"
        />
        <div class="swiper">
          <button @click="prev" v-if="is_find===true">Предыдущая</button>
          <a v-if="is_find===true">{{ page_num + 1}}</a>
          <button @click="next" v-if="is_find===true">Следующая</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import NetworkGraph from "@/components/Graph.vue";
import RouteItem from "@/components/RouteCard.vue";
import {ref} from "vue";

export default {
  name: "App",
  components: {
    NetworkGraph,
    RouteItem
  },
  data() {
    return {
      dep_id: "",
      to: "",
      from: "",
      routeData: [],
      graph: {
        nodes: [],
        edges: []
      },
      page_num: ref(0),
      page_size: ref(5),
      is_find: false,
      is_zero: false,
    };
  },
  async mounted() {
    try {
      const edgesResponse = await axios.get(
          `http://localhost:9000/dev/api/v1/booking/find-all-graph`
      );
      this.graph = {
        nodes: edgesResponse.data.nodes || [],
        edges: edgesResponse.data.edges || []
      };
    } catch (error) {
      console.error("Ошибка при получении данных:", error);
    }
  },
  methods: {
    highlightRoute(routeEdgeIds) {

      // Передаём событие выделения маршрута в компонент графа
      if (this.$refs.networkGraph) {
        this.$refs.networkGraph.highlightRoute(routeEdgeIds);
      }
    },
    async createNewRoute(departureId) {
      try {
        const params = {};
        params.id = departureId
        this.dep_id = departureId;
        if (this.page_num) params.page_num = this.page_num
        if (this.page_size) params.page_size = this.page_size
        const response = await axios.get("http://localhost:9000/dev/api/v1/booking/find-routes-by-dep-id", {
          params: params
        });
        this.is_find = true
        if (response.data.length <= 0) {
          if (this.page_num >= 0)
            this.page_num--;
          this.routeData = []
        } else {
          this.routeData = response.data;
        }
      } catch (error) {
        console.error("Ошибка при получении данных:", error);
      }
    },
    async next() {
      if (this.routeData.length !== 0) {
        this.page_num++;
        await this.createNewRoute(this.dep_id)
      }
    },
    async prev() {
      if (this.page_num > 0) {
        this.page_num--;
        this.is_zero = false;
        await this.createNewRoute(this.dep_id)
      }
    }
  }
};
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.filter-section {
  margin-bottom: 20px;
}

input {
  margin-left: 10px;
  padding: 5px;
}

.info {
  display: flex;
  flex-flow: row;
  justify-content: center;

}

button {
  margin-left: 10px;
  padding: 5px 10px;
  cursor: pointer;
}
</style>
