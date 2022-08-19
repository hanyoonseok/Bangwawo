<template>
  <div class="stretch">
    <transition name="slide-fade" mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: "ClassView",
  setup() {},
};
</script>

<style scoped src="@/css/classList.scss" lang="scss"></style>
