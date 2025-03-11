
<template>

  <div class="graph-el">

    <a>{{route.edgeId.fromLocationId.label}}</a>
    <a>{{route.edgeId.toLocationId.type}}</a>

  </div>
</template>
<script>
import Modal from "@/components/Modal.vue";
import {createBooking} from "@/api.js";

export default {
  components: {Modal},
  props: {
    route: Object,
    isFinder: Boolean,
    isFind: Boolean
  }
};

</script>

<style>
.graph-el {
  display: flex;
  flex-flow: row;
  justify-content: center;
  align-items: center;
}
</style>