<template>
  <form class="signup-volunteer" @submit.prevent="submitRegister">
    <div class="guide-volunteer">
      <img src="@/assets/guide-volunteer.png" />
      <div class="guide">안녕하세요 OOO님. <br />자기소개를 입력해주세요.</div>
    </div>
    <textarea
      class="introduction"
      placeholder="자기소개를 입력해주세요."
      v-model="textareaValue"
      required
    ></textarea>
    <button type="submit" class="signup-submit">확인</button>
  </form>
</template>

<script>
import { ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "SignupUpVolunteer",
  props: ["isUser", "ageRange", "kakaoId", "nickname"],
  setup(props) {
    let textareaValue = ref("");
    const registObj = ref({ ...props });
    const router = useRouter();
    const store = useStore();

    registObj.value.character = [
      { part: "bag", color: "FFD89B" },
      { part: "body", color: "f1f1f1" },
      { part: "clothes", color: "FFAEAE" },
      { part: "glasses", color: "ff9696" },
      { part: "foot", color: "ff9696" },
      { part: "hat", color: "FFD89B" },
    ];

    const submitRegister = () => {
      registObj.value.introduce = textareaValue.value;
      registObj.value.userType = "volunteer";
      store
        .dispatch("root/loginUser", registObj.value)
        .then((res) => {
          store.commit("root/setUserInfo", res.data);
          router.push("/class/list");
        })
        .catch((err) => console.log(err.message));
    };

    return { textareaValue, submitRegister };
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
