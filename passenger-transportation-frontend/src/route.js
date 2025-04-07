import { createRouter, createWebHistory } from 'vue-router';
import SearchRoutePage from "@/pages/SearchRoutePage.vue";
import SearchBooking from "@/components/SearchBooking.vue";

import SearchAllRoutesGraph from "@/components/SearchAllRoutesGraph.vue";

const routes = [
    { path: '/', component: SearchRoutePage },
    { path: '/search-booking', component: SearchBooking },
    { path: '/find-all-routes', component: SearchAllRoutesGraph },
    { path: '/find-all-routes/graph', component: SearchAllRoutesGraph },
];
const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
