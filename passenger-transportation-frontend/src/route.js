import { createRouter, createWebHistory } from 'vue-router';
import SearchRoutePage from "@/pages/SearchRoutePage.vue";
import SearchBooking from "@/components/SearchBooking.vue";
import FindAllRoutesPage from "@/pages/FindAllRoutesPage.vue";
import MainPage from "@/pages/MainPage.vue";
import SearchAllRoutesGraph from "@/components/SearchAllRoutesGraph.vue";
import SearchAllRoutesList from "@/components/SearchAllRoutesList.vue";
const routes = [
    { path: '/search-route', component: SearchRoutePage },
    { path: '/search-booking', component: SearchBooking },
    { path: '/find-all-routes', component: FindAllRoutesPage },
    { path: '/', component: MainPage },
    { path: '/find-all-routes/graph', component: SearchAllRoutesGraph },
    { path: '/find-all-routes/list', component: SearchAllRoutesList }
];
const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
