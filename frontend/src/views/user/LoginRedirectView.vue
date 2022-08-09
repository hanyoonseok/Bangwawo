<template>
  <div class="login-form"></div>
</template>

<script>
import { useRoute, useRouter } from "vue-router";
import jwt_decode from "jwt-decode";

import axios from "axios";
export default {
  name: "LoginRedirectView",
  setup() {
    const route = useRoute();
    const router = useRouter();
    const { code } = route.query;

    const getLoginInfo = async () => {
      const { data } = await axios.get(
        `${process.env.VUE_APP_API_URL}/kakao/${code}`,
      );
      console.log(data);
      if (data.isUser) {
        //data 복호화 하고 로컬스토리지에 담기
        const decode_jwt = jwt_decode(data.JWT);
        decode_jwt.user.userType = decode_jwt.userType.toLowerCase();
        localStorage.setItem("user", JSON.stringify(decode_jwt.user));
        router.push("/class/list");
      } else {
        data.ageRange = 10;
        data.ageRange < 20
          ? router.push({
              name: "signupStudent",
              params: data,
            })
          : router.push({ name: "signupVolunteer", params: data });
      }
    };

    getLoginInfo();
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
