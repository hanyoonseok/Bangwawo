<template>
  <div class="layout">
    <transition name="slide-fade" mode="out-in">
      <router-view></router-view>
    </transition>
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
