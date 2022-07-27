<template>
  <div class="user-background">
    <router-view></router-view>
  </div>
</template>

<script>
// import { reactive } from "vue";
import LoginView from "@/views/LoginView.vue";
export default {
  name: "SignupView",
  setup() {},
  components: {
    LoginView,
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
