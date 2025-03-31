<template>
  <div class="app-container">
    <div class="filter-section">
      <input
          class="locationFilter"
          v-model="from"
          placeholder="Введите откуда поедите"
      />
      <input
          class="locationFilter"
          v-model="to"
          placeholder="Введите куда поедете"
      />
      <select v-model="type" class="type-group locationFilter">
        <option
            v-for="typeEl in types"
            :key="typeEl.id"
            :value="typeEl.typeName"
        >
          {{ typeEl.typeName }}
        </option>
      </select>
      <input type="date" id="dateInput" class="locationFilter" />
      <input type="time" id="timeInput" class="locationFilter" />
      <button class="filter-btn" @click="fetchRoutesAndEdges">
        Применить фильтр
      </button>
    </div>
    <div class="not_found" v-if="isFind === false">
      <span>Похоже ничего не найдено ;(</span>
    </div>
    <div class="info" v-if="isFind === true">
      <NetworkGraph :graph="graph" ref="networkGraph" />
      <div class="routes">
        <RouteItem
            v-for="route in routeData"
            :key="route.id"
            :route="route"
            :is-finder="true"
            :is-find="true"
            @highlight-route="highlightRoute"
        />
        <div class="pagination-buttons" v-if="is_find">
          <button class="pagination-btn" @click="prev" :disabled="page_num === 0">
            Предыдущая
          </button>
          <span class="page-number">Страница {{ page_num + 1 }}</span>
          <button class="pagination-btn" @click="next" :disabled="!hasMore">
            Следующая
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NetworkGraph from "@/components/Graph.vue";
import RouteItem from "@/components/RouteCard.vue";
import { findById, findRoutesByParams, getAllTypes } from "@/api.js";

export default {
  name: "App",
  components: {
    NetworkGraph,
    RouteItem
  },
  data() {
    return {
      to: "",
      from: "",
      time: null,
      type: null,
      types: [],
      isFind: null,
      routeData: [],
      hasMore: true,
      graph: {
        nodes: [],
        edges: []
      },
      page_num: 0,
      page_size: 3,
      is_find: false
    };
  },
  mounted() {
    this.searchType();
  },
  methods: {
    async fetchRoutesAndEdges() {
      this.page_num = 0;
      await this.fetchData();
    },
    async fetchData() {
      try {
        this.time = getFormattedTime();
        const params = {};
        if (this.type) params.type = this.type;
        if (this.from) params.from = this.from;
        if (this.to) params.to = this.to;
        if (this.time) params.time = this.time;
        // Передаем номер и размер страницы
        params.page_num = this.page_num;
        params.page_size = this.page_size;

        const routeResponse = await findRoutesByParams(params);
        const data = routeResponse.data || [];
        if (this.page_num === 0 && data.length === 0) {
          this.isFind = false;
        } else {
          this.isFind = true;
        }
        if (data.length === 0 && this.page_num > 0) {
          this.page_num--;
          this.hasMore = false;
        } else {
          this.is_find = data.length > 0;
          this.routeData = data;
          this.hasMore = data.length === this.page_size;
        }
        // Обновление графа на основе найденных маршрутов
        const routeIds = this.routeData.map(route => route.id).join(",");
        const edgesResponse = await findById(routeIds);
        this.graph = {
          nodes: edgesResponse.data.nodes || [],
          edges: edgesResponse.data.edges || []
        };
      } catch (error) {
        console.error("Ошибка при получении данных:", error);
      }
    },
    next() {
      if (this.hasMore) {
        this.page_num++;
        this.fetchData();
      }
    },
    prev() {
      if (this.page_num > 0) {
        this.page_num--;
        this.hasMore = true;
        this.fetchData();
      }
    },
    async searchType() {
      try {
        const response = await getAllTypes();
        this.types = response.data;
        this.types.push({ id: -1, typeName: "микс" });
      } catch (err) {
        console.error("Ошибка при поиске маршрутов:", err);
      }
    },
    highlightRoute(routeEdgeIds) {
      if (this.$refs.networkGraph) {
        this.$refs.networkGraph.highlightRoute(routeEdgeIds);
      }
    }
  }
};

function getFormattedTime() {
  let dateValue = document.getElementById("dateInput").value;
  let timeValue = document.getElementById("timeInput").value;

  if (!timeValue) {
    timeValue = "08:00";
  }
  if (!dateValue) {
    const today = new Date();
    dateValue = today.toISOString().split("T")[0];
  }
  const dateTimeString = `${dateValue}T${timeValue}`;
  const date = new Date(dateTimeString);
  const formattedDate = `${String(date.getMonth() + 1).padStart(2, "0")}/` +
      `${String(date.getDate()).padStart(2, "0")}/` +
      `${date.getFullYear()}-` +
      `${String(date.getHours()).padStart(2, "0")}:` +
      `${String(date.getMinutes()).padStart(2, "0")}:` +
      `${String(date.getSeconds()).padStart(2, "0")}`;
  return formattedDate;
}
</script>

<style scoped>
/* Общий стиль контейнера */
.app-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30px;
  background: #f5f7fa;
  min-height: 100vh;
}

/* Стили для фильтра */
.filter-section {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 15px;
  padding: 20px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
  max-width: 800px;
  width: 100%;
}

.locationFilter {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 25px;
  outline: none;
  width: 180px;
  transition: border 0.3s;
}

.locationFilter:focus {
  border-color: #2b7ce9;
}

.type-group {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 25px;
  outline: none;
  transition: border 0.3s;
}

.type-group:focus {
  border-color: #2b7ce9;
}

.filter-btn {
  background: #2b7ce9;
  color: #fff;
  border: none;
  padding: 12px 24px;
  border-radius: 25px;
  cursor: pointer;
  transition: background 0.3s;
  font-size: 16px;
}

.filter-btn:hover {
  background: #1f5aa4;
}

/* Стили для блока, если ничего не найдено */
.not_found {
  text-align: center;
  font-size: 18px;
  color: #666;
  margin: 20px 0;
}

/* Стили для основного блока с графом и маршрутами */
.info {
  display: flex;
  flex-direction: row;
  gap: 30px;
  justify-content: center;
  width: 100%;
}

/* Стили для списка маршрутов */
.routes {
  display: flex;
  flex-direction: column;
  gap: 20px;
  max-width: 600px;
  width: 100%;
  max-height: 800px; /* можно настроить по необходимости */
  overflow-y: auto;
}

/* Стили для пагинации */
.pagination-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  margin-top: 20px;
}

.pagination-buttons button {
  background: #2b7ce9;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 25px;
  cursor: pointer;
  transition: background 0.3s;
}

.pagination-buttons button:disabled {
  background: #b0c4de;
  cursor: not-allowed;
}

.pagination-buttons button:hover:not(:disabled) {
  background: #1f5aa4;
}

.page-number {
  font-size: 16px;
  font-weight: bold;
}
</style>
