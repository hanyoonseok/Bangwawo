<template>
  <div class="layout">
    <router-view></router-view>
  </div>
</template>

<script>
import AOS from "aos";
import "aos/dist/aos.css";
export default {
  name: "App",
  setup() {
    AOS.init();
  },
};
</script>

<style lang="scss">
@import "@/css/global.scss";
</style>
