import { createRouter, createWebHistory } from 'vue-router';
import CancelBookingPage from "@/pages/CancelBookingPage.vue";
import SearchRoutePage from "@/pages/SearchRoutePage.vue";;
import SearchBooking from "@/components/SearchBooking.vue";
import FindAllRoutesPage from "@/pages/FindAllRoutesPage.vue";
import MainPage from "@/pages/MainPage.vue";

const routes = [
    { path: '/cancel-booking', component: CancelBookingPage },
    { path: '/search-route', component: SearchRoutePage },
    { path: '/search-booking', component: SearchBooking },
    { path: '/find-all-routes', component: FindAllRoutesPage },
    { path: '/', component: MainPage }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
