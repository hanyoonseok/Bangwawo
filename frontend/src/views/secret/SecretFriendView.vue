<template>
  <div class="background">
    <HeaderNav
      :isMatchingBtnClick="state.isMatchingBtnClick"
      @sendAlarm="sendAlarm"
    />
    <section class="main-section">
      <section classs="sub-section" v-if="!state.isMatching">
        <img src="@/assets/secret_landing.png" class="landing-img loading" />
        <button class="start-btn" @click="startMatch">비밀친구 대화하기</button>

        <h4 style="padding-top: 80px">
          현재 상담 가능한 친구 수 : {{ state.volunteerCnt }} 명
        </h4>
      </section>
      <section class="sub-section-loading" v-else>
        <!-- <img src="@/assets/secret_matching.png" /> -->
        <img
          v-if="!state.startMinigame"
          src="@/assets/loading.gif"
          class="loading-gif"
        />
        <button class="minigame-start" @click="startMinigame">
          미니게임 시작
          <img src="@/assets/guide-volunteer.png" />
          <i class="fa-solid fa-caret-up" v-if="!state.startMinigame"></i>
          <i class="fa-solid fa-caret-down" v-else></i>
        </button>
        <div class="minigame-area" v-if="state.startMinigame">
          <Mini2dGame @closeMinigame="closeMinigame"></Mini2dGame>
        </div>
      </section>
    </section>
  </div>
</template>

<script>
import { onMounted, reactive } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
import { useStore } from "vuex";
// import { useRouter } from "vue-router";
import Mini2dGame from "@/components/secret/Mini2dGame.vue";

export default {
  name: "SecretFriendView",
  components: {
    HeaderNav,
    Mini2dGame,
  },
  setup() {
    const store = useStore();
    const state = reactive({
      isMatching: false,
      volunteerCnt: 0,
      isMatchingBtnClick: false,
      startMinigame: false,
    });

    const startMatch = () => {
      state.isMatching = true;
      state.isMatchingBtnClick = true;
    };

    onMounted(() => {
      getTalkableVolunteer();
    });

    const getTalkableVolunteer = async () => {
      store.dispatch("root/getTalkableVolunteer").then((response) => {
        console.log(response.data.volunteers.length);
        state.volunteerCnt = response.data.volunteers.length;
      });
    };

    const sendAlarm = () => {
      console.log(state.isMatchingBtnClick);
      state.isMatchingBtnClick = false;
    };

    //유저가 미니게임 시작을 눌렀다
    const startMinigame = () => {
      state.startMinigame = !state.startMinigame;
    };
    const closeMinigame = () => {
      state.startMinigame = false;
    };

    return {
      state,
      startMatch,
      closeMinigame,
      sendAlarm,
      startMinigame,
    };
  },
};
</script>

<style scoped src="@/css/secret-friend.scss" lang="scss"></style>
