<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
    <div>
      <p>{{ clientData }}</p>
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo";

import { computed, reactive } from "vue";
export default {
  name: "UserVideo",
  components: {
    OvVideo,
  },

  props: ["streamManager"],

  setup(props) {
    const state = reactive({
      clientData: computed(() => {
        const { clientData } = getConnectionData();
        return clientData;
      }),
    });
    const getConnectionData = () => {
      const { connection } = props.streamManager.stream;
      console.log(connection.data);
      return JSON.parse(connection.data);
    };
    return { state, getConnectionData };
  },
};
</script>
