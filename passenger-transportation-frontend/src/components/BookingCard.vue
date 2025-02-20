<template>
  <div class="booking-card" :class="{hovered: isHovered}">
    <div class="booking-details">
      <div class="inner-booking-details" :class="{hovered: isHovered}">
        <p>Время брони: {{ booking.bookingTime }}</p>
        <p>Статус: {{ booking.status }}</p>
      </div>
      <div class="load-route-info" :class="{ hovered: isHovered }"
           @mousedown="onHover">
        <svg width="28" height="26" viewBox="0 0 48 26" fill="none" xmlns="http://www.w3.org/2000/svg">
          <line x1="0.872521" y1="24.9204" x2="24.4203" y2="1.37255" stroke="black"/>
          <line x1="23.8536" y1="1.24642" x2="47.4014" y2="24.7942" stroke="black"/>
        </svg>
      </div>
    </div>
    <div v-if="isHovered">
     <RouteCard :route="route"/>
    </div>
  </div>
</template>

<script>
import RouteCard from "@/components/RouteCard.vue";
export default {
  components: {RouteCard},
  props: {
    booking: Object,
    route: Object
  },
  data() {
    return {
      isHovered: false,
    };
  },
  methods: {
    onHover() {
      if (this.isHovered) {
        this.isHovered = false;
        return;
      } else {
        this.isHovered = true;
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
.booking-card {
  display: flex;
  flex-flow: column;
  justify-content: space-between;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin: 5px 0;
}
.inner-booking-details {
  display: flex;
  flex-flow: column;
  margin: 0;
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
  padding-bottom: 100px;
  border: 1px solid #ccc;

  .booking-details {
    background-color: #f2f2f2;
  }
}
</style>