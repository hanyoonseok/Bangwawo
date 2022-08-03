<template>
  <div class="login-form"></div>
</template>

<script>
import { useRoute } from "vue-router";
import axios from "axios";
export default {
  name: "LoginRedirectView",
  setup() {
    const route = useRoute();
    const { code } = route.query;
    console.log(code);
    axios
      .post(
        `https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=${process.env.VUE_APP_KAKAO_RESTAPI_KEY}&redirect_uri=${process.env.VUE_APP_REDIRECT_URL}&code=${code}`,
      )
      .then((res) => {
        console.log(res);
        axios
          .get(`${process.env.VUE_APP_API_URL}/users/${res.data.access_token}`)
          .then((result) => console.log(result));
      });
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
