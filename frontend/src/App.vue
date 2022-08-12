<template>
  <div class="layout">
    <transition name="slide-fade" mode="out-in">
      <router-view></router-view>
    </transition>
    >
    <!--헤더로옮길거임..-->
    <!-- <notifications position="bottom right" classes="my-custom-class" /> -->
  </div>
</template>

<script>
import AOS from "aos";

// import { notify } from "@kyvg/vue3-notification";

import "aos/dist/aos.css";
// import { onMounted } from "vue";
export default {
  name: "App",
  setup() {
    AOS.init();
    // onMounted(() => {
    //   notify({
    //     title: "4개의 알림이 있습니다.",
    //   });
    // });
    return {};
  },
};
</script>

<style lang="scss">
@import "@/css/global.scss";
</style>
