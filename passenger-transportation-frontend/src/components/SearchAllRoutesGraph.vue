<template>
  <div class="app-container">
    <div class="info">
      <NetworkGraph
          :graph="graph"
          ref="networkGraph"
          @create-new-route="createNewRoute"
      />
      <section class="find-all-routes">
        <div class="routes" id="routes-graph">
          <div class="route-date" v-for="(routes, date) in groupedRoutes" :key="date">
            <h2>📅 {{ date }}</h2>
            <RouteItem
                v-for="route in routes"
                :key="route.id"
                :route="route"
                :is-finder="true"
                :is-find="true"
                @highlight-route="highlightRoute"
            />
          </div>
        </div>

        <div class="swiper" v-if="is_find">
          <button @click="prev" :disabled="page_num === 0">Предыдущая</button>
          <a>{{ page_num + 1 }}</a>
          <button @click="next" :disabled="!hasMore">Следующая</button>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import NetworkGraph from "@/components/Graph.vue";
import RouteItem from "@/components/RouteCard.vue";
import {getAllGraphs, getRoutesByDepId} from "@/api.js";

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
      const edgesResponse = await getAllGraphs();
      console.log(edgesResponse)
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
      this.page_num = 0;
      console.log(departureId);
      await this.find(departureId);
    },
    async find(departureId) {
      try {
        const params = {};
        params.id = departureId;
        this.dep_id = departureId;
        params.page_num = this.page_num;
        params.page_size = this.page_size;
        const response = await getRoutesByDepId(params);
        console.log(response.data)
        if (response.data.length <= 0) {
          if (this.page_num > 0) this.page_num--;
          this.routeData = [];
          this.is_find = false;
          this.hasMore = false;
        } else {
          this.is_find = true;
          this.routeData = response.data;

          this.hasMore = response.data.length === this.page_size;
        }
      } catch (error) {
        console.error("Ошибка при получении данных:", error);
      }
    },
    async next() {
      if (this.hasMore) {
        this.page_num++;
        await this.find(this.dep_id);
      }
    },
    async createParams(departureId) {
      return {
        id: departureId,
        page_num: this.page_num,
        page_size: this.page_size
      };
    },
    async prev() {
      if (this.page_num > 0) {
        this.page_num--;
        this.hasMore = true;
        await this.find(this.dep_id);
      }
    }
  },
  computed : {
    groupedRoutes() {
      const grouped = {};
      this.routeData.forEach(route => {
        const date = route.departureTime.split("T")[0];
        if (!grouped[date]) {
          grouped[date] = [];
        }
        grouped[date].push(route);
      });
      return grouped;
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
#routes-graph {
  display: grid;
  grid-template-columns: auto auto;
}
@media screen and (max-width: 1720px) {
  .info {
    display: flex;
    flex-flow: column;
    justify-content: center;
  }
  .network-container {
    height: 700px;
    width: 700px;
    border: 1px solid lightgray;
  }
}
</style>
