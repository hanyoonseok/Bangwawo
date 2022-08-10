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
      <div class="guide">별명, 부모님 이메일을 알려줘~</div>
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
  props: ["isUser", "ageRange", "kakaoId", "nickname"],
  setup(props) {
    let emailValue = ref("");
    const registObj = ref({ ...props });
    const router = useRouter();
    const store = useStore();

    registObj.value.character = [
      { part: "bag", color: "FFD89B" },
      { part: "body", color: "f1f1f1" },
      { part: "clothes", color: "ff9696" },
      { part: "glasses", color: "FFD89B" },
      { part: "foot", color: "ff9696" },
      { part: "hat", color: "FFD89B" },
    ];

    const submitRegister = () => {
      registObj.value.pemail = emailValue.value;
      registObj.value.userType = "student";
      store
        .dispatch("root/loginUser", registObj.value)
        .then((res) => {
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
