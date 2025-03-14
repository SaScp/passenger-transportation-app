<template>
  <div class="network">
    <div
        ref="networkContainer"
        class="network-container"
        style="height: 700px; width: 1000px; border: 1px solid lightgray;"
    ></div>
    <div class="rows">
      <span> для приближение колесико мышки &#11014</span>
      <span>для уменьшение колесико мышки &#11015</span>
    </div>

  </div>
</template>

<script>
import { Network, DataSet } from "vis-network/standalone";
import axios from "axios";

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
      // Используем DataSet для узлов и ребер
      nodes: new DataSet([]),
      edges: new DataSet([]),
      options: {
        layout: { randomSeed: 1 },
        interaction: {
          hover: true,
          selectConnectedEdges: true
        },
        physics: {
          barnesHut: {
            springLength: 150,
            gravitationalConstant: -50000,
            centralGravity: 0.00001,
            springConstant: 0.0001,
            damping: 0.1
          },
          stabilization: true
        },
        nodes: {
          size: 100, // Увеличенный размер всех узлов
          font: { size: 60 } // Увеличенный шрифт
        },
        edges: {
          smooth: {
            enabled: true,
            type: 'curvedCW',
            roundness: 0.2
          },
          arrows: {
            to: {
              enabled: true,
              imageHeight: undefined,
              imageWidth: undefined,
              scaleFactor: 2,
              src: undefined,
              type: "arrow"
            }
          },

          width: 6,
          color: { color: '#2B7CE9'}
        }
      }
    };
  },
  watch: {
    // При изменении объекта graph обновляем данные графа
    graph: {
      handler(newGraph) {
        this.updateGraph(newGraph);
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    const container = this.$refs.networkContainer;
    this.network = new Network(
        container,
        { nodes: this.nodes, edges: this.edges },
        this.options
    );

    this.network.on('click', (params) => {

      if (params.nodes.length > 0) {
        const clickedNodeId = params.nodes[0]
        console.log(clickedNodeId)
        this.$emit("create-new-route", clickedNodeId);
      }
    })
    this.updateGraph(this.graph);

  },
  methods: {
    updateGraph(newGraph) {
      // Очищаем текущие данные
      this.nodes.clear();
      this.edges.clear();

      // Добавляем новые узлы и ребра
      if (newGraph.nodes && newGraph.nodes.length > 0) {
        this.nodes.add(newGraph.nodes);
      }
      if (newGraph.edges && newGraph.edges.length > 0) {
        this.edges.add(newGraph.edges);
      }
      if (this.network) {
        this.network.redraw();
        this.network.fit();
      }
    },
    highlightRoute(routeEdgeIds) {
      // Сбрасываем цвета всех рёбер и узлов
      this.edges.forEach(edge => {
        this.edges.update({ id: edge.id, color: { color: undefined } });
      });

      this.nodes.forEach(node => {
        this.nodes.update({ id: node.id, color: { background: "#97c2fc" } });
      });

      // Если маршрут пуст, выходим
      if (!routeEdgeIds.length) return;

      // Определяем первый и последний узел маршрута
      const firstNodeId = routeEdgeIds[0].fromLocationId.id;
      const lastNodeId = routeEdgeIds[routeEdgeIds.length - 1].toLocationId.id;

      // Проходим по маршруту и выделяем рёбра и узлы
      this.edges.forEach(edge => {
        if (routeEdgeIds.some(r => r.fromLocationId.id === edge.from && r.toLocationId.id === edge.to)) {
          this.edges.update({ id: edge.id, color: { color: "green" } });
          this.nodes.update({ id: edge.to, color: { background: "green" } });
        }
      });

      // Красим первый и последний узел маршрута в красный
      this.nodes.update({ id: firstNodeId, color: { background: "red" } });
      this.nodes.update({ id: lastNodeId, color: { background: "red" } });

      // Перерисовываем сеть
      if (this.network) {
        this.network.redraw();
      }
    }

  },
};
</script>

<style scoped>
.rows {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}
.network {
  display: flex;
  flex-direction: column;
}
</style>
