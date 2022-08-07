<template>
  <div class="stu-inclass-container">
    <user-video
      :stream-manager="publisher"
      @click="updateMainVideoStreamManager(publisher)"
    />
  </div>
</template>

<script>
export default {
  name: "StudentOX",
  props: ["publisher", "joinSession"],
  setup(props) {
    props.joinSession();
  },
  components: {},
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
