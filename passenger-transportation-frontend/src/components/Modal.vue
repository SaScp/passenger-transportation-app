<template>
  <div v-if="isOpen" class="modal-overlay" @click="close">
    <div class="modal-content" :class="modalClass" @click.stop>
      <a>{{message}}</a>
      <button class="close-btn" @click="close">✖</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Modal",
  props: {
    isOpen: {
      type: Boolean,
      required: true,
    },
    statusCode: {
      type: Number,
      default: null,
    },
    message: ''
  },
  computed: {
    modalClass() {
      if (this.statusCode >= 200 && this.statusCode < 300) {
        return "success";
      } else if (this.statusCode >= 400 && this.statusCode < 600) {
        return "error";
      } else {
        return "default";
      }
    },
  },
  methods: {
    close() {
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: white;
  padding: 20px;
  border-radius: 8px;
  position: relative;
  min-width: 300px;
  transition: background 0.3s;
}

/* Цветовые стили */
.success {
  background: #d4edda;
  border: 2px solid #28a745;
}

.error {
  background: #f8d7da;
  border: 2px solid #dc3545;
}

.default {
  background: white;
}

.close-btn {

  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
}
</style>
