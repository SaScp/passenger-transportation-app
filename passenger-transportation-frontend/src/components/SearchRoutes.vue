<template>
  <div class="app-container">
    <div class="filter-section">
      <input
          class="locationFilter"
          id="locationFilter"
          v-model="from"
          placeholder="Введите откуда поедите"
      />
      <input
          class="locationFilter"
          id="locationFilter"
          v-model="to"
          placeholder="Введите куда поедете"
      />
      <select v-model="type" class="type-group locationFilter">
        <option
            class="inner-type-group locationFilter"
            v-for="typeEl in types"
            :key="typeEl.id"
            :value="typeEl.typeName">
          {{ typeEl.typeName }}
        </option>
      </select>
      <input type="date" id="dateInput" class="locationFilter">
      <input type="time" id="timeInput" class="locationFilter">
      <button @click="fetchRoutesAndEdges">Применить фильтр</button>
    </div>
    <div class="not_found" v-if="isFind === false">
      <span>Похоже ничего не найдено ;(</span>
    </div>
    <div class="info" v-if="isFind===true">
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
          <button @click="prev" :disabled="page_num === 0">Предыдущая</button>
          <span>Страница {{ page_num + 1 }}</span>
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
import {findById, findRoutesByParams, getAllTypes} from "@/api.js";

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
      isFind: false,
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
          this.isFind = false
        } else {
          this.isFind = true
        }
        if (data.length === 0 && this.page_num > 0) {

          this.page_num--;
          this.hasMore = false;
        } else {
          this.is_find = data.length > 0;
          this.routeData = data;
          this.hasMore = data.length === this.page_size;

        }

        console.log(this.isFind)
        // Обновление графа
        const routeIds = this.routeData.map(route => route.id).join(",");
        const edgesResponse = await findById(routeIds)
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
        this.types.push({id: -1, typeName: "микс"});
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
    dateValue = today.toISOString().split('T')[0];
  }

  const dateTimeString = `${dateValue}T${timeValue}`;
  const date = new Date(dateTimeString);

  const formattedDate = `${String(date.getMonth() + 1).padStart(2, '0')}/` +
      `${String(date.getDate()).padStart(2, '0')}/` +
      `${date.getFullYear()}-` +
      `${String(date.getHours()).padStart(2, '0')}:` +
      `${String(date.getMinutes()).padStart(2, '0')}:` +
      `${String(date.getSeconds()).padStart(2, '0')}`;
  return formattedDate;
}
</script>

<style scoped>
.locationFilter {
  border-radius: 15px;
}

.app-container {
  display: flex;
  justify-content: center;
  flex-direction: column;
  padding: 20px;
}

.type-group {
  padding: 10px;
  margin: 10px 0;
  border-radius: 4px;
  border: 1px solid #ccc;
}
.not_found {
  display: flex;
  justify-content: center;
}
.filter-section {
  display: flex;
  flex-flow: row;
  justify-content: center;
  margin-bottom: 20px;
  border-radius: 15px;
}

input {
  margin-left: 10px;
  margin-right: 10px;
  padding: 5px;
}

.info {
  display: flex;
  justify-content: center;
  flex-flow: row;
}

button {
  margin-left: 10px;
  padding: 10px 10px;
  cursor: pointer;
}

.pagination-buttons {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 15px;
}

.pagination-buttons button {
  margin: 0 10px;
}
</style>
