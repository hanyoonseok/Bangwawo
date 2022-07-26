<template>
  <div class="layout">
    <HeaderNav />
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderNav from "./components/HeaderNav.vue";
import AOS from "aos";
import "aos/dist/aos.css";
export default {
  name: "App",
  components: {
    HeaderNav,
  },
  setup() {
    AOS.init();
  },
};
</script>

<style lang="scss">
@import "@/css/global.scss";
</style>
