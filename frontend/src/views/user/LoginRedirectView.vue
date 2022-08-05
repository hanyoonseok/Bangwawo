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

    const getLoginInfo = async () => {
      const response = await axios.get(
        `${process.env.VUE_APP_API_URL}/api/kakao/${code}`,
      );

      console.log(response);
      //로컬스토리지랑 vuex에 저장하고 메인으로 리다이렉트
      route.push("/class/list");
    };

    getLoginInfo();
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
