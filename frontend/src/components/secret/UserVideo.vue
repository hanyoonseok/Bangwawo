<template>
  <div v-if="streamManager" style="position: relative">
    <ov-video :stream-manager="streamManager" />
  </div>
</template>

<script>
import OvVideo from "@/components/secret/OvVideo";
import { computed, reactive } from "vue";
export default {
  name: "UserVideo",
  components: {
    OvVideo,
  },
  props: {
    streamManager: Object,
  },
  setup(props) {
    const state = reactive({
      clientData: computed(() => {
        const { clientData } = getConnectionData();
        return clientData;
      }),
    });
    const getConnectionData = () => {
      console.log(props.streamManager.stream);
      const { connection } = props.streamManager.stream;
      return JSON.parse(connection.data);
    };
    return { state, getConnectionData };
  },
};
</script>

<style scoped>
div > p {
  margin: 0;
}
.nameTag {
  border-radius: 10px;
  padding: 4px 7px;
  background-color: rgba(255, 255, 255, 0.8);
  font-family: "Jua";
  font-size: 1vw;
  position: absolute;
  top: 7%;
  left: 4%;
}
</style>
