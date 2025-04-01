<template>
  <div class="network-wrapper">
    <div v-if="isLoading" class="loading-container">
      <div class="spinner"></div>
      <p>Загрузка графа...</p>
    </div>
    <div  class="network">
      <div v-show="!isLoading" ref="networkContainer" class="network-container"></div>
      <!-- Легенда -->
      <div class="legend">
        <h3>Легенда</h3>
        <ul class="legend-container">
          <li><span class="legend-box standard"></span> Стандартный узел/ребро</li>
          <li><span class="legend-box highlighted"></span> Выделенный маршрут</li>
          <li><span class="legend-box airplane"></span> Самолет</li>
          <li><span class="legend-box train"></span> Поезд</li>
          <li><span class="legend-box bus"></span> Автобус</li>
          <li><span class="legend-box taxi"></span> Такси</li>
          <li><span class="legend-box marshrutka"></span> Маршрутка</li>
        </ul>
      </div>
      <div class="rows">
        <span>Для приближения используйте колесико мыши &#11014;</span>
        <span>Для уменьшения используйте колесико мыши &#11015;</span>
      </div>
    </div>
  </div>
</template>

<script>
import { Network, DataSet } from "vis-network/standalone";
export default {
  name: "NetworkGraph",
  props: {
    graph: {
      type: Object,
      default: () => ({ nodes: [], edges: [] })
    }
  },
  data() {
    return {
      network: null,
      nodes: new DataSet([]),
      edges: new DataSet([]),
      isHighlighting: false,       // Флаг, что сейчас выполняется highlightRoute
      pendingGraph: null,          // Если пришёл новый граф во время выделения
      isLoading: true,             // Флаг загрузки графа
      options: {
        layout: { randomSeed: 1 },
        interaction: { hover: true, selectConnectedEdges: true },
        physics: {
          barnesHut: {
            springLength: 550,
            gravitationalConstant: -1050000,
            centralGravity: 0.00001,
            springConstant: 0.00001,
            damping: 0.1
          },
          stabilization: true
        },
        nodes: {
          shape: "dot",
          size: 350,
          font: { size: 355 },
          borderWidth: 2
        },
        edges: {
          smooth: { enabled: true, type: "curvedCW", roundness: 0.2 },
          width: 15,
          arrows: { to: { enabled: true, scaleFactor: 1.2 } }
        },

      },

    }
  },
  watch: {
    graph: {
      handler(newGraph) {
        // Если идёт выделение маршрута, сохраняем новый граф в очередь
        if (this.isHighlighting) {
          this.pendingGraph = newGraph;
        } else {
          this.updateGraph(newGraph);
        }
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    // Убеждаемся, что контейнер существует
    this.$nextTick(() => {
      const container = this.$refs.networkContainer;
      if (!container) {
        console.error("Network container not found!");
        return;
      }

      this.network = new Network(
          container,
          { nodes: this.nodes, edges: this.edges },
          this.options
      );


      this.network.on("click", (params) => {
        if (params.nodes.length > 0) {
          this.$emit("create-new-route", params.nodes[0]);
        }
      });

      this.updateGraph(this.graph);

    });
  },
  methods: {
    updateGraph(newGraph) {
      if (this.isHighlighting) return;
      this.nodes.clear();
      this.edges.clear();

      if (newGraph.nodes && newGraph.nodes.length) {
        const formattedNodes = newGraph.nodes.map((node) => ({
          ...node,
          label: node.label,
          title: node.label,
          color: this.getNodeColor(node.type)
        }));
        this.nodes.add(formattedNodes);
      }
      if (newGraph.edges && newGraph.edges.length) {
        const formattedEdges = newGraph.edges.map((edge) => ({
          ...edge,
          title: `Тип: ${edge.type}`,
          label: edge.type,
          color: { color: this.getEdgeColor(edge.type) }
        }));
        this.edges.add(formattedEdges);
      }
      if (this.network) {
        this.network.redraw();
        this.network.fit({  animation: {             // animation object, can also be Boolean
            duration: 10,                 // animation duration in milliseconds (Number)
            easingFunction: "easeInOutQuad" // Animation easing function, available are:
          }    });
        this.network.moveTo({scale: 0.005})
      }

      setTimeout(function (scope) {
        scope.isLoading = false;
      }, 3000, this);
    },

    getNodeColor(type) {
      switch (type) {
        case "Самолет": return { background: "#e67e22", border: "#d35400" };
        case "Поезд": return { background: "#8e44ad", border: "#71368a" };
        case "Автобус": return { background: "#2B7CE9", border: "#1f5aa4" };
        case "Такси": return { background: "#95a5a6", border: "#7f8c8d" };
        case "Маршрутка": return { background: "#27ae60", border: "#1e8449" };
        default: return { background: "#97c2fc", border: "#2B7CE9" };
      }
    },
    getEdgeColor(type) {
      switch (type) {
        case "Самолет": return "#e67e22";
        case "Поезд": return "#8e44ad";
        case "Автобус": return "#2B7CE9";
        case "Такси": return "#95a5a6";
        case "Маршрутка": return "#27ae60";
        default: return "#2B7CE9";
      }
    },
    highlightRoute(routeEdgeIds) {
      // Устанавливаем флаг, чтобы заблокировать обновление графа
      this.isHighlighting = true;

      // Сброс цветов для всех узлов и ребер
      this.edges.forEach((edge) => {
        this.edges.update({ id: edge.id, color: { color: this.getEdgeColor(edge.type) } });
      });
      this.nodes.forEach((node) => {
        const baseColor = this.getNodeColor(node.type);
        this.nodes.update({ id: node.id, color: { background: baseColor.background } });
      });

      if (!routeEdgeIds || !routeEdgeIds.length) {
        this.isHighlighting = false;
        if (this.pendingGraph) {
          this.updateGraph(this.pendingGraph);
          this.pendingGraph = null;
        }
        return;
      }

      const firstNodeId = routeEdgeIds[0].fromLocationId.id;
      const lastNodeId = routeEdgeIds[routeEdgeIds.length - 1].toLocationId.id;

      routeEdgeIds.forEach((r) => {
        const foundEdge = this.edges.get({
          filter: (e) => e.from === r.fromLocationId.id && e.to === r.toLocationId.id
        })[0];
        if (foundEdge) {
          this.edges.update({ id: foundEdge.id, color: { color: "green" } });
        }
        this.nodes.update({ id: r.fromLocationId.id, color: { background: "green" } });
        this.nodes.update({ id: r.toLocationId.id, color: { background: "green" } });
      });

      this.nodes.update({ id: firstNodeId, color: { background: "red" } });
      this.nodes.update({ id: lastNodeId, color: { background: "red" } });

      if (this.network) {
        this.network.redraw();
      }
      // Снимаем блокировку по завершению выделения
      this.isHighlighting = false;
      if (this.pendingGraph) {
        this.updateGraph(this.pendingGraph);
        this.pendingGraph = null;
      }
    }
  }
};
</script>

<style scoped>
.network-wrapper {
  position: relative;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 700px; /* можно настроить в зависимости от размера графа */
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.spinner {
  width: 50px;
  height: 50px;
  border: 6px solid #f3f3f3;
  border-top: 6px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 15px;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.network {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}

.network-container {
  width: 1000px;
  height: 700px;
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid #ddd;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.rows {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-top: 10px;
  font-size: 14px;
  color: #555;
}

.legend {
  margin: 20px auto 10px;
  background: #fff;
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 10px 15px;
  max-width: 1000px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.legend h3 {
  margin: 0 0 10px;
  font-size: 16px;
  color: #333;
}

.legend ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.legend li {
  display: flex;
  align-items: center;
  margin: 5px;
  font-size: 14px;
  color: #555;
}

.legend-box {
  width: 16px;
  height: 16px;
  margin-right: 8px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

.legend-container {
  display: flex;
  flex-wrap: wrap;
}

.legend-box.standard {
  background: #97c2fc;
}

.legend-box.highlighted {
  background: green;
}

.legend-box.airplane {
  background: #e67e22;
}

.legend-box.train {
  background: #8e44ad;
}

.legend-box.bus {
  background: #2B7CE9;
}

.legend-box.taxi {
  background: #95a5a6;
}

.legend-box.marshrutka {
  background: #27ae60;
}

@media screen and (max-width: 1200px) {
  .network-container {
    width: 100%;
    height: 500px;
  }
}
</style>
