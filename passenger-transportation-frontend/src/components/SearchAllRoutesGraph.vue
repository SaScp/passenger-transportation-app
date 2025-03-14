<template>
  <div class="app-container">
    <div class="info">
      <NetworkGraph
          :graph="graph"
          ref="networkGraph"
          @create-new-route="createNewRoute"
      />
      <div class="routes" v-if="routeData">
        <RouteItem
            v-for="route in routeData"
            :key="route.id"
            :route="route"
            :is-finder="true"
            :isFind="true"
            @highlight-route="highlightRoute"
        />
        <div class="swiper" v-if="is_find">
          <button @click="prev" :disabled="page_num === 0">Предыдущая</button>
          <a>{{ page_num + 1 }}</a>
          <button @click="next" :disabled="!hasMore">Следующая</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import NetworkGraph from "@/components/Graph.vue";
import RouteItem from "@/components/RouteCard.vue";

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
      page_num: 0,
      page_size: 4,
      is_find: false,
      is_zero: false,
      hasMore: true
    };
  },
  async mounted() {
    try {
      const edgesResponse = await axios.get(
          "http://localhost:9000/dev/api/v1/booking/find-all-graph"
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
      if (this.$refs.networkGraph) {
        this.$refs.networkGraph.highlightRoute(routeEdgeIds);
      }
    },
    async createNewRoute(departureId) {
      try {
        const params = {};
        params.id = departureId;
        this.dep_id = departureId;
        params.page_num = this.page_num;
        params.page_size = this.page_size;
        const response = await axios.get(
            "http://localhost:9000/dev/api/v1/booking/find-routes-by-dep-id",
            {params}
        );

        // Если ничего не найдено, сбрасываем данные и флаг is_find
        if (response.data.length <= 0) {
          if (this.page_num > 0) this.page_num--;
          this.routeData = [];
          this.is_find = false;
          this.hasMore = false;
        } else {
          this.is_find = true;
          this.routeData = response.data;
          // Если получено меньше элементов, чем page_size – следующей страницы нет
          this.hasMore = response.data.length === this.page_size;
        }
      } catch (error) {
        console.error("Ошибка при получении данных:", error);
      }
    },
    async next() {
      if (this.hasMore) {
        this.page_num++;
        await this.createNewRoute(this.dep_id);
      }
    },
    async prev() {
      if (this.page_num > 0) {
        this.page_num--;
        this.hasMore = true;
        await this.createNewRoute(this.dep_id);
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
