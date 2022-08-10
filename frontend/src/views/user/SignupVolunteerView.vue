<template>
  <div class="signup-volunteer">
    <div class="guide-volunteer">
      <img src="@/assets/guide-volunteer.png" />
      <div class="guide">
        안녕하세요 봉사자님. <br />자기소개를 입력해주세요.
      </div>
    </div>
    <textarea
      class="introduction"
      placeholder="자기소개를 입력해주세요."
      v-model="textareaValue"
    ></textarea>
    <button class="signup-submit" @click="submitRegister">확인</button>
  </div>
</template>

<script>
import axios from "axios";
import { ref } from "vue";
import jwt_decode from "jwt-decode";
import { useRouter } from "vue-router";

export default {
  name: "SignupUpVolunteer",
  props: ["isUser", "ageRange", "kakaoId", "nickname"],
  setup(props) {
    let textareaValue = ref("");
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
      registObj.value.introduce = textareaValue.value;
      console.log(registObj.value);
      const response = await axios.post(
        `${process.env.VUE_APP_API_URL}/volunteer/`,
        registObj.value,
      );

      const decode_jwt = jwt_decode(response.data.JWT);
      console.log(decode_jwt);
      decode_jwt.user.userType = decode_jwt.userType.toLowerCase();
      localStorage.setItem("user", JSON.stringify(decode_jwt.user));
      router.push("/class/list");
    };

    return { textareaValue, submitRegister };
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
