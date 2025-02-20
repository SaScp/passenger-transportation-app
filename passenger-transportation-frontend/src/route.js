import { createRouter, createWebHistory } from 'vue-router';
import CancelBookingPage from "@/pages/CancelBookingPage.vue";
import SearchRoutePage from "@/pages/SearchRoutePage.vue";
import BookingFromPage from "@/pages/BookingFromPage.vue";
import SearchBooking from "@/components/SearchBooking.vue";
import FindAllRoutesPage from "@/pages/FindAllRoutesPage.vue";

const routes = [
    { path: '/cancel-booking', component: CancelBookingPage },
    { path: '/search-route', component: SearchRoutePage },
    { path: '/create-booking', component: BookingFromPage },
    { path: '/search-booking', component: SearchBooking },
    { path: '/find-all-routes', component: FindAllRoutesPage }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
