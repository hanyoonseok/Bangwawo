<template>
  <form class="signup-volunteer" @submit.prevent="submitRegister">
    <div class="guide-volunteer">
      <img src="@/assets/guide-volunteer.png" />
      <div class="guide">
        안녕하세요 {{ nickname }}님. <br />자기소개를 입력해주세요.
      </div>
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
  props: ["isUser", "ageRange", "kakaoId", "nickname", "accessToken"],
  setup(props) {
    let textareaValue = ref("");
    const registObj = ref({ ...props });
    const router = useRouter();
    const store = useStore();
    console.log(props);

    if (!props.nickname || !props.kakaoId) {
      alert("원활한 서비스 이용을 위해 정보 수집을 모두 동의해주세요...!");
      location.href = "/user/login";
    }

    registObj.value.character = [
      { part: "body", color: "fffffb" },
      { part: "foot", color: "ff9696" },
      { part: "glasses", color: "ff9696" },
      { part: "clothes", color: "FFAEAE" },
      { part: "bag", color: "FFD89B" },
      { part: "hat", color: "FFD89B" },
    ];

    const submitRegister = () => {
      registObj.value.introduce = textareaValue.value;
      registObj.value.userType = "volunteer";
      store
        .dispatch("root/loginUser", registObj.value)
        .then((res) => {
          res.data.accessToken = props.accessToken;
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
