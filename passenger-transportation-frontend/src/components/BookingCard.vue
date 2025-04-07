<template>
  <div class="booking-card" :class="{hovered: isHovered}">
    <div class="booking-details">
      <div class="inner-booking-details" :class="{hovered: isHovered}">
        <div hidden="hidden" :id="booking.route"/>
        <time>Время брони: {{ booking.bookingTime }}</time>
        <p>Статус: {{ booking.status }}</p>
      </div>
      <div class="inner-booking-button" v-if="booking.status !== 'Отменено' && isActive">
        <button class="filter-btn" @click="revokeBooking(booking)">Отменить бронь</button>
      </div>
      <div class="load-route-info filter-btn" :class="{ hovered: isHovered }"
           @mousedown="onHover" @click="onClick(booking.route)">
        <svg width="28" height="26" viewBox="0 0 48 26" fill="none" xmlns="http://www.w3.org/2000/svg">
          <line x1="0.872521" y1="24.9204" x2="24.4203" y2="1.37255" stroke="black"/>
          <line x1="23.8536" y1="1.24642" x2="47.4014" y2="24.7942" stroke="black"/>
        </svg>
      </div>
    </div>
    <div v-if="isHovered" class="route">
      <div class="routes-list">
        <RouteCard v-for="route in routes" :key="route.id" :route="route" :is-finder="false" />
      </div>
    </div>
  </div>
</template>

<script>
import RouteCard from "@/components/RouteCard.vue";
import {getRouteById, revokeBooking} from "@/api.js";
import axios from "axios";
export default {
  components: {RouteCard},
  props: {
    booking: Object,
  },
  data() {
    return {
      isHovered: false,
      routes: [],
      isActive: true
    };
  },
  methods: {
    onHover() {
      this.isHovered = !this.isHovered;
    },
    async onClick(routeId) {
      console.log(routeId);
      await this.searchRouteById(routeId);
    },
    async searchRouteById(id) {
      const response = await getRouteById(id);
      this.routes = response.data;
    },
    async revokeBooking(booking) {
      try {
        const response = await revokeBooking(booking.id);
        if (response.status === 200) {
          this.isActive = false
          booking.status = 'отменено'
        }
      }catch(err)
      {
      }

    }
  }
};
</script>
<style>
.booking-details {
  display: flex;
  flex-flow: row;
  justify-content: space-between;
}
.inner-booking-button {
  display: flex;
  align-items: center;
}
.booking-card {
  display: flex;
  flex-flow: column;
  justify-content: space-between;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin: 10px 0;
}
.inner-booking-details {
  display: flex;
  flex-flow: column;
  margin: 10px;

}

.load-route-info {
  display: flex;
  align-items: center;
  margin: 0;
}

.load-route-info.hovered {
  svg {
    transform: rotate(-90deg);
    color: red;
  }
}
.booking-card.hovered {
  border: 1px solid #ccc;
  .route {
  }
  .booking-details {
    background-color: #f2f2f2;
  }
}
</style>