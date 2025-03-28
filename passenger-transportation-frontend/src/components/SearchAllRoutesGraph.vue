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
            <h2 class="date-header">📅 {{ formatDateHeader(date) }}</h2>
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
          <button class="pagination-button" @click="prev" :disabled="page_num === 0">Предыдущая</button>
          <span class="page-number">{{ page_num + 1 }}</span>
          <button class="pagination-button" @click="next" :disabled="!hasMore">Следующая</button>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import NetworkGraph from "@/components/Graph.vue";
import RouteItem from "@/components/RouteCard.vue";
import { getAllGraphs, getRoutesByDepId } from "@/api.js";

export default {
  name: "SearchAllRoutesGraph",
  components: {
    NetworkGraph,
    RouteItem
  },
  data() {
    return {
      dep_id: "",
      routeData: [],
      graph: {
        nodes: [],
        edges: []
      },
      page_num: 0,
      page_size: 4,
      is_find: false,
      hasMore: true
    };
  },
  async mounted() {
    try {
      const response = await getAllGraphs();
      this.graph = {
        nodes: response.data.nodes || [],
        edges: response.data.edges || []
      };
    } catch (error) {
      console.error("Ошибка при получении данных графа:", error);
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
      this.dep_id = departureId;
      await this.fetchRoutes();
    },
    async fetchRoutes() {
      try {
        const params = {
          id: this.dep_id,
          page_num: this.page_num,
          page_size: this.page_size
        };
        const response = await getRoutesByDepId(params);
        if (response.data.length === 0) {
          if (this.page_num > 0) this.page_num--;
          this.routeData = [];
          this.is_find = false;
          this.hasMore = false;
        } else {
          this.routeData = response.data;
          this.is_find = true;
          this.hasMore = response.data.length === this.page_size;
        }
      } catch (error) {
        console.error("Ошибка при получении маршрутов:", error);
      }
    },
    async next() {
      if (this.hasMore) {
        this.page_num++;
        await this.fetchRoutes();
      }
    },
    async prev() {
      if (this.page_num > 0) {
        this.page_num--;
        this.hasMore = true;
        await this.fetchRoutes();
      }
    },
    formatDateHeader(dateStr) {
      const [year, month, day] = dateStr.split("-");
      return `${day}.${month}.${year}`;
    }
  },
  computed: {
    groupedRoutes() {
      const groups = {};
      this.routeData.forEach(route => {
        const date = route.departureTime.split("T")[0];
        if (!groups[date]) {
          groups[date] = [];
        }
        groups[date].push(route);
      });
      return groups;
    }
  }
};
</script>

<style scoped>
/* Общие стили приложения */
.app-container {
  padding: 30px;
  background: #f5f7fa;
  min-height: 100vh;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.info {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 30px;
}

/* Стили для блока маршрутов */
.find-all-routes {
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  max-width: 500px;
  flex: 1;
}

.routes {
  display: grid;
  grid-template-columns: 1fr;
  gap: 20px;
}

.route-date {
  border-bottom: 1px solid #e0e0e0;
  padding-bottom: 10px;
  margin-bottom: 10px;
}

.date-header {
  font-size: 18px;
  margin-bottom: 10px;
  color: #333;
}

/* Стили для пагинации */
.swiper {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  margin-top: 15px;
}

.pagination-button {
  background: #2b7ce9;
  color: #fff;
  border: none;
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s;
}

.pagination-button:disabled {
  background: #b0c4de;
  cursor: not-allowed;
}

.pagination-button:hover:not(:disabled) {
  background: #1f5aa4;
}

.page-number {
  font-weight: bold;
  font-size: 16px;
}
</style>
