<template>
  <div class="stu-inclass-container">
    <user-video
      :stream-manager="publisher"
      @click="updateMainVideoStreamManager(publisher)"
    />
  </div>
</template>

<script>
import UserVideo from "@/components/class/UserVideo.vue";
export default {
  name: "StudentOX",
  props: ["publisher", "joinSession"],
  setup(props) {
    props.joinSession();
  },
  components: { UserVideo },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
