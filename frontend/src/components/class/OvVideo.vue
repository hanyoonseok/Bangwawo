<template>
  <video autoplay />
</template>

<script>
export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
  },

  mounted() {
    this.streamManager.addVideoElement(this.$el);
  },
};
</script>
<style scoped>
video {
  object-fit: cover;
  width: 100%;
  height: 100%;
  color: #fff;
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 100%;
  border-radius: 20px;
  background-color: #000;
}
</style>
