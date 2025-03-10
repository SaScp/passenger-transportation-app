<template>
  <div class="app-container">
    <div class="filter-section">
      <label for="locationFilter">
        Фильтры
      </label>
      <input
          id="locationFilter"
          v-model="from"
          placeholder="Введите откуда поедите"
      />
      <input
          id="locationFilter"
          v-model="to"
          placeholder="Введите куда поедете"
      />
      <select v-model="type" class="type-group">
        <option class="inner-type-group" v-for="typeEl in types" v-bind:value="typeEl.id">
          {{ typeEl.typeName }}
        </option>
      </select>
      <input type="date" id="dateInput">
      <input type="time" id="timeInput">
      <button @click="fetchRoutesAndEdges">Применить фильтр</button>
    </div>
    <div class="info">
      <NetworkGraph :graph="graph" ref="networkGraph" />
      <div class="routes">
        <RouteItem
            :is-finder="true"
            v-for="route in routeData"
            :key="route.id"
            :route="route"
            @highlight-route="highlightRoute"
        />
        <div class="swiper">
          <button @click="prev" v-if="is_find===true">Предыдущая</button>
          <a>{{ page_num + 1}}</a>
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
      to: "",
      from: "",
      time: null,
      type: null,
      types: [],
      routeData: [],
      graph: {
        nodes: [],
        edges: []
      },
      page_num: ref(0),
      page_size: ref(5),
      is_find: false,
      is_zero: false
    };
  },
  mounted() {
    this.searchType();
  },
  methods: {
    async fetchRoutesAndEdges() {
      try {
        this.time = getFormattedTime();
        console.log(this.time)
        const params = {};
        if (this.type) params.type = this.type
        if (this.from) params.from = this.from
        if (this.to) params.to = this.to
        if (this.time) params.time = this.time
        if (this.page_num) params.page_num = this.page_num
        if (this.page_size) params.page_size = this.page_size
        const routeResponse = await axios.get(
            "http://localhost:9000/dev/api/v1/booking/find", {
              params: params
            }
        );
        this.is_find = true
        if (routeResponse.data.length <= 0) {
          this.page_num--;
        } else {
          this.routeData = routeResponse.data;
        }
        console.log(routeResponse.request)
        console.log(routeResponse.data)
        //this.routeData = routeResponse.data || [];

        console.log(this.routeData)
        const routeIds = this.routeData.map(route => route.id).join(",");
        console.log(`http://localhost:9000/dev/api/v1/booking/find-by-ids?id=${routeIds}`)
        const edgesResponse = await axios.get(
            `http://localhost:9000/dev/api/v1/booking/find-by-ids?id=${routeIds}`
        );
        console.log(edgesResponse)
        this.graph = {
          nodes: edgesResponse.data.nodes || [],
          edges: edgesResponse.data.edges || []
        };
      } catch (error) {
        console.error("Ошибка при получении данных:", error);
      }
    },
    async next() {
      if (this.routeData.length !== 0) {
        this.page_num++;
        await this.fetchRoutesAndEdges()
      }
    },
    async prev() {
      if (this.page_num > 0) {
        this.page_num--;
        this.is_zero = false;
        await this.fetchRoutesAndEdges()
      }
    },
    async searchType() {
      try {
        const response = await axios.get(
            `http://localhost:9000/dev/api/v1/booking/find-types`
        )
        this.types = response.data;
        this.types.push({id: null, typeName: "микс"})
      } catch (err) {
        this.error = err.response ? err.response.data.detail : 'Ошибка при поиске маршрутов';
        this.routes = [];
      }
    },
    highlightRoute(routeEdgeIds) {
      // Передаём событие выделения маршрута в компонент графа
      if (this.$refs.networkGraph) {
        this.$refs.networkGraph.highlightRoute(routeEdgeIds);
      }
    },

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
  console.log(formattedDate);
  return formattedDate;
}

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
}

button {
  margin-left: 10px;
  padding: 5px 10px;
  cursor: pointer;
}
</style>
