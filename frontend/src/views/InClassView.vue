<template>
  <section class="background">
    <HostView v-if="state.isHost" />
    <UserView v-else />
  </section>
</template>

<script>
import { reactive } from "vue";
import HostView from "@/components/class/HostView.vue";
import UserView from "@/components/class/UserView.vue";
export default {
  name: "InClassView",
  components: {
    HostView,
    UserView,
  },
  setup() {
    const state = reactive({
      isHost: true,
    });

    return {
      state,
    };
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
