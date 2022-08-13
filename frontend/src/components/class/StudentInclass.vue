<template>
  <div class="stu-inclass-container">
    <user-video :stream-manager="publisher" id="volunteerVideo" />
    <div id="container-screens"></div>
  </div>
</template>

<script>
import UserVideo from "@/components/class/UserVideo.vue";
export default {
  name: "StudentOX",
  props: ["publisher", "screen"],
  setup() {},
  components: { UserVideo },
};
</script>

<style scoped src="@/css/class.scss" lang="scss">
#container-screens > video {
  width: 100%;
}
</style>
