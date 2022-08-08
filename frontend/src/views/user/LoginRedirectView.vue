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
        localStorage.setItem("user", JSON.stringify(decode_jwt.user));
        router.push("/class/list");
      } else {
        data.ageRange < 20
          ? router.push({
              name: "signupStudent",
              params: data,
            })
          : router.push({ name: "signupVolunteer", params: data });
      }
      //response.data.isUser에 따라서 회원가입으로 보낼지 로그인시키고 메인으로 보낼지 분기처리
      //로컬스토리지랑 vuex에 저장하고 메인으로 리다이렉트
    };

    getLoginInfo();
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
