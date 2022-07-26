<template>
  <section class="main-section">
    <section classs="sub-section" v-if="!state.isMatching">
      <img src="@/assets/secret_landing.png" class="landing-img" />
      <button class="start-btn" @click="startMatch()">비밀친구 대화하기</button>
    </section>
    <section classs="sub-section" v-else>
      <img src="@/assets/secret_matching.png" />
    </section>
  </section>
</template>

<script>
import { reactive } from "vue";
export default {
  name: "SecretFriendLanding",
  setup() {
    const state = reactive({
      isMatching: false,
    });

    const startMatch = () => {
      state.isMatching = true;
    };

    return {
      state,
      startMatch,
    };
  },
};
</script>

<style scoped src="@/css/secretFriend.scss" lang="scss"></style>
