<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section">
      <section classs="sub-section" v-if="!state.isMatching">
        <img src="@/assets/secret_landing.png" class="landing-img" />
        <button class="start-btn" @click="startMatch()">
          비밀친구 대화하기
        </button>
      </section>
      <section classs="sub-section" v-else>
        <img src="@/assets/secret_matching.png" />
        <!-- <img src="@/assets/loading.gif" /> -->
      </section>
    </section>
  </div>
</template>

<script>
import { reactive } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  name: "SecretFriendView",
  components: {
    HeaderNav,
  },
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
