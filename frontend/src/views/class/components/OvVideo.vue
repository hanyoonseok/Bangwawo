<template>
  <video autoplay style="width: 98%; max-width: 540px" />
</template>

<script>
import { onMounted } from "vue";

export default {
  name: "OvVideo",
  props: ["streamManager"],
  setup(props) {
    const video = document.querySelector("video");
    onMounted(() => {
      props.streamManager.addVideoElement(video);
    });
    return {
      video,
    };
  },
};
</script>
<style scoped>
.died-user {
  -webkit-filter: grayscale(100%);
  filter: #515151;
}
</style>
