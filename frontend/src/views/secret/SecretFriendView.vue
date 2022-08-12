<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section">
      <section classs="sub-section" v-if="!state.isMatching">
        <img src="@/assets/secret_landing.png" class="landing-img" />
        <button class="start-btn" @click="startMatch">비밀친구 대화하기</button>
      </section>
      <section class="sub-section" v-else>
        <!-- <img src="@/assets/secret_matching.png" /> -->
        <img src="@/assets/loading.gif" />
      </section>
    </section>
  </div>
</template>

<script>
import { reactive, ref, getCurrentInstance } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
import { useStore } from "vuex";

export default {
  name: "SecretFriendView",
  components: {
    HeaderNav,
  },
  setup() {
    const store = useStore();
    const app = getCurrentInstance();
    const $soketio = app.appContext.config.globalProperties.$soketio;
    const state = reactive({
      isMatching: false,
    });
    const user = ref(store.state.root.user);

    const startMatch = () => {
      state.isMatching = true;
      // 봉사자에게 알림 뿌려주기
      $soketio.emit("matchingStart", user.value.sid);
    };

    //봉사자가 학생의 매칭 요청을 승인했을때 실행되는 함수
    $soketio.on("gotoSecretPage", () => {
      // 대충 학생이 비밀페이지로 간다는 코드
    });

    return {
      state,
      startMatch,
    };
  },
};
</script>

<style scoped src="@/css/secret-friend.scss" lang="scss"></style>
