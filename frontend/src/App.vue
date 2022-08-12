<template>
  <div class="layout">
    <transition name="slide-fade" mode="out-in">
      <router-view></router-view>
    </transition>
    >
    <notifications position="bottom right" classes="my-custom-class" />
  </div>
</template>

<script>
import AOS from "aos";
import { notify } from "@kyvg/vue3-notification";

import "aos/dist/aos.css";
import { onMounted } from "vue";
export default {
  name: "App",
  setup() {
    AOS.init();
    onMounted(() => {
      notify({
        title: "1개의 알람이 있습니다.",
      });
    });
    return {};
  },
};
</script>

<style lang="scss">
@import "@/css/global.scss";
</style>
