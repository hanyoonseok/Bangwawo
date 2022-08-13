<template>
  <div class="stu-inclass-container">
    <user-video :stream-manager="publisher" />
  </div>
</template>

<script>
import UserVideo from "@/components/class/UserVideo.vue";
export default {
  name: "StudentOX",
  props: ["publisher"],
  setup() {},
  components: { UserVideo },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
