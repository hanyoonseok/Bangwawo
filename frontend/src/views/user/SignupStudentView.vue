<template>
  <div class="signup-container">
    <div class="input-box">
      <input
        class="sign-input"
        placeholder="학부모 이메일을 입력해주세요."
        v-model="emailValue"
      />
      <button class="signup-submit" @click="submitRegister">로그인</button>
    </div>
    <div class="duck-img">
      <div class="guide">별명, 부모님 이메일을 알려줘~</div>
      <img src="@/assets/signupDuck.png" />
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import axios from "axios";
import jwt_decode from "jwt-decode";
import { useRouter } from "vue-router";

export default {
  name: "SignupUpVolunteer",
  props: ["isUser", "ageRange", "kakaoId", "nickname"],
  setup(props) {
    let emailValue = ref("");
    const registObj = ref({ ...props });
    const router = useRouter();

    registObj.value.character = {
      bag: "FFD89B",
      body: "f1f1f1",
      dress: "FFAEAE",
      glasses: "ff9696",
      legs: "ff9696",
      hat: "FFD89B",
    };

    const submitRegister = async () => {
      registObj.value.pemail = emailValue.value;
      console.log(registObj.value);
      const response = await axios.post(
        `${process.env.VUE_APP_API_URL}/student/`,
        registObj.value,
      );
      const decode_jwt = jwt_decode(response.data.JWT);
      localStorage.setItem("user", JSON.stringify(decode_jwt.user));
      router.push("/class/list");
    };

    return { emailValue, submitRegister };
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
