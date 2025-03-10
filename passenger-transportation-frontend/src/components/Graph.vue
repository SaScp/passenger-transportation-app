<template>
  <div class="network">
    <div
        ref="networkContainer"
        style="height: 600px; border: 1px solid lightgray;"
    ></div>
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
        layout: { randomSeed: 3 },
        interaction: {
          hover: true,
          selectConnectedEdges: false
        },
        physics: {
          barnesHut: {
            springLength: 90,
            gravitationalConstant: -10000,
            centralGravity: 0.021,
            springConstant: 0.04,
            damping: 0.19
          },
          stabilization: true
        },
        edges: {
          smooth: {
            enabled: true,
            type: 'curvedCW',
            roundness: 0.2
          },
          width: 5,
          color: { color: '#2B7CE9' }
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
      }
    },
    highlightRoute(routeEdgeIds) {
      // Сброс цвета для всех ребер
      this.edges.forEach(edge => {
        this.edges.update({id: edge.id, color: {color: undefined}});
      });
      // routeEdgeIds ожидается как массив объектов { from, to }
      this.edges.forEach(edge => {
        if (routeEdgeIds.some(r => r.fromLocationId.id === edge.from && r.toLocationId.id === edge.to)) {
          this.edges.update({id: edge.id, color: {color: "green"}});
        }
      });
      if (this.network) {
        this.network.redraw();
      }
    },


  }
};
</script>

<style scoped>
.network {
  display: flex;
  flex-direction: row;
}
</style>
