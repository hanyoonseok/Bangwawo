<template>
  <div class="login-form"></div>
</template>

<script>
import { useRoute } from "vue-router";
export default {
  name: "LoginRedirectView",
  setup() {
    const route = useRoute();
    const code = route.query;
    console.log(code);
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
