<template>
  <form class="signup-container" @submit.prevent="submitRegister">
    <div class="input-box">
      <input
        class="sign-input"
        type="email"
        placeholder="학부모 이메일을 입력해주세요."
        v-model="emailValue"
        required
      />
      <button type="submit" class="signup-submit">로그인</button>
    </div>
    <div class="duck-img">
      <div class="guide">부모님 이메일을 알려줘~</div>
      <img src="@/assets/signupDuck.png" />
    </div>
  </form>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  name: "SignupUpVolunteer",
  props: ["isUser", "ageRange", "kakaoId", "nickname", "accessToken"],
  setup(props) {
    let emailValue = ref("");
    const registObj = ref({ ...props });
    const router = useRouter();
    const store = useStore();

    console.log(props);

    registObj.value.character = [
      { part: "body", color: "fffffb" },
      { part: "foot", color: "ff9696" },
      { part: "glasses", color: "ff9696" },
      { part: "clothes", color: "FFAEAE" },
      { part: "bag", color: "FFD89B" },
      { part: "hat", color: "FFD89B" },
    ];

    const submitRegister = () => {
      registObj.value.pemail = emailValue.value;
      registObj.value.userType = "student";
      store
        .dispatch("root/loginUser", registObj.value)
        .then((res) => {
          res.data.accessToken = props.accessToken;
          store.commit("root/setUserInfo", res.data);
          router.push("/class/list");
        })
        .catch((err) => console.log(err.message));
    };

    return { emailValue, submitRegister };
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
