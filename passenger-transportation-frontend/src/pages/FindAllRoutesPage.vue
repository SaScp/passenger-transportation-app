<template>

  <header class="main-header">
    <nav class="nav-menu">
      <router-link to="/find-all-routes/graph" class="nav-item" active-class="active-link">
        📋 Все маршруты на карте
      </router-link>

      <router-link to="/find-all-routes/list" class="nav-item" active-class="active-link">
        📋 Все маршруты списком
      </router-link>
    </nav>
  </header>
  <router-view/>


</template>
<style>

</style>
<script setup lang="ts">

import SearchAllRoutes from "@/components/SearchAllRoutesGraph.vue";
</script>