<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section">
      <section classs="sub-section" v-if="!state.isMatching">
        <img src="@/assets/secret_landing.png" class="landing-img loading" />
        <button class="start-btn" @click="startMatch">비밀친구 대화하기</button>

        <h4 style="padding-top: 80px">
          현재 상담 가능한 친구 수 : {{ state.volunteerCnt }} 명
        </h4>
      </section>
      <section class="sub-section" v-else>
        <!-- <img src="@/assets/secret_matching.png" /> -->
        <img src="@/assets/loading.gif" class="loading" />
      </section>
    </section>
  </div>
</template>

<script>
import { onMounted, reactive, ref, getCurrentInstance } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "SecretFriendView",
  components: {
    HeaderNav,
  },
  setup() {
    const router = useRouter();
    const store = useStore();
    const app = getCurrentInstance();
    const $soketio = app.appContext.config.globalProperties.$soketio;
    const state = reactive({
      isMatching: false,
      volunteerCnt: 0,
    });
    const user = ref(store.state.root.user);

    const startMatch = () => {
      state.isMatching = true;
      // 봉사자에게 알림 뿌려주기
      $soketio.emit("matchingStart", user.value.sid);
    };

    onMounted(() => {
      // 현재 상담가능한 봉사자 수 세기
      getTalkableVolunteer();
    });

    $soketio.on("updateVolunteerTalkableCount", () => {
      getTalkableVolunteer();
    });

    const getTalkableVolunteer = async () => {
      store.dispatch("root/getTalkableVolunteer").then((response) => {
        console.log(response.data.volunteers.length);
        state.volunteerCnt = response.data.volunteers.length;
      });
    };

    //봉사자가 학생의 매칭 요청을 승인했을때 실행되는 함수
    $soketio.on("gotoSecretPage", (data) => {
      // 대충 학생이 비밀페이지로 간다는 코드
      router.push({
        name: "secrettalk",
        params: { sid: user.value.sid, vid: data },
      });
    });

    return {
      state,
      startMatch,
    };
  },
};
</script>

<style scoped src="@/css/secret-friend.scss" lang="scss"></style>
