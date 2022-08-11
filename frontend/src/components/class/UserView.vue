<template>
  <section class="main-section">
    <section class="top-section">
      <article
        :class="{
          'top-left': true,
          user: true,
          expand: !state.isTopOpen && !state.isChatOpen,
        }"
      >
        <article
          :class="{
            'top-article-left': true,
            top: true,
            'top-side': state.isTopOpen || state.isChatOpen,
            'right-side': !state.isTopOpen && !state.isChatOpen,
          }"
        >
          <div class="idx-btn-wrapper next" @click="nextClick">
            <button class="idx-btn next"></button>
          </div>
          <div class="idx-btn-wrapper prev" @click="prevClick">
            <button class="idx-btn prev"></button>
          </div>
          <div class="user-card-wrapper" id="container-screens">
            <div class="hover-wrapper">나</div>
            <div class="user-card"><OvVideo :stream-manager="me" /></div>
          </div>
          <div
            class="user-card-wrapper"
            v-for="(user, i) in subs"
            :key="user.id"
          >
            <div class="hover-wrapper">이름{{ i }}</div>
            <div class="user-card"><OvVideo :stream-manager="user" /></div>
          </div>
        </article>
        <article class="top-article-left bot">
          <StudentOX v-if="state.isOXOpen" />
          <StudentInclass :publisher="me" :joinSession="joinSession" v-else />
        </article>
      </article>

      <article class="top-right" v-if="state.isTopOpen || state.isChatOpen">
        <ParticipantsList
          :state="state"
          :toggleParticipants="toggleParticipants"
        />
        <ChatForm :state="state" :toggleChat="toggleChat" />
      </article>
    </section>

    <section class="bot-section">
      <article class="bot-left">
        <button class="option-btn" @click="activeMute" v-if="state.audioState">
          <i class="fa-solid fa-microphone-slash"></i>
          &nbsp;음소거
        </button>
        <button class="option-btn" @click="activeMute" v-else>
          <i class="fa-solid fa-microphone-slash"></i>
          &nbsp;음소거
        </button>

        <button class="option-btn" @click="activeVideo" v-if="state.videoState">
          <i class="fa-solid fa-video"></i>
          &nbsp;비디오 중지
        </button>
        <button class="option-btn" @click="activeVideo" v-else>
          <i class="fa-solid fa-video"></i>
          &nbsp;비디오 시작
        </button>
        <router-link :to="{ name: 'mypage' }">
          <i class="fa-solid fa-xmark xmark"></i
        ></router-link>
      </article>

      <article class="bot-right">
        <button class="option-btn" @click="toggleParticipants()">
          <i class="fa-solid fa-user"></i>
          &nbsp;참가자
        </button>
        <button class="option-btn" @click="toggleChat()">
          <i class="fa-solid fa-comments"></i>
          &nbsp;채팅
        </button>
      </article>
    </section>
  </section>
</template>

<script>
import { reactive } from "vue";
import ParticipantsList from "@/components/class/ParticipantsList.vue";
import ChatForm from "@/components/class/ChatForm.vue";
import StudentOX from "@/components/class/StudentOX.vue";
import StudentInclass from "@/components/class/StudentInclass.vue";
import OvVideo from "./OvVideo";

export default {
  name: "UserView",
  props: [
    "dataLen",
    "currentUsers",
    "prevClick",
    "nextClick",
    "leaveSession",
    "me",
    "subs",
  ],
  setup(props, { emit }) {
    const state = reactive({
      isParticipantsOpen: false,
      isChatOpen: false,
      isTopOpen: false,
      isOXOpen: false,
      videoState: true, // 비디오 on,off
      audioState: true, // 소리 on,off
    });

    const toggleParticipants = () => {
      state.isParticipantsOpen = !state.isParticipantsOpen;
      state.isTopOpen = state.isParticipantsOpen;
      // const len = !state.isTopOpen && !state.isChatOpen ? 4 : 5;
      // if (len != props.dataLen.value) {
      //   emit("changeDataLen", len);
      //   props.initCurrentStudents();
      // }
    };

    const toggleChat = () => {
      state.isChatOpen = !state.isChatOpen;
      // const len = !state.isTopOpen && !state.isChatOpen ? 4 : 5;
      // if (len != props.dataLen.value) {
      //   emit("changeDataLen", len);
      //   props.initCurrentStudents();
      // }
    };

    const activeVideo = () => {
      state.videoState = !state.videoState;
      const myVideo = document.getElementById("myVideo");
      const userCard = myVideo.querySelector(".user-card");
      const video = userCard.querySelector("video");
      if (state.videoState === false) {
        userCard.style.backgroundColor = "#000";
        video.style.display = "none";
      } else {
        userCard.style.backgroundColor = "transparent";
        video.style.display = "block";
      }
      emit("activeVideo", state.videoState);
    };

    const activeMute = () => {
      state.audioState = !state.audioState;
      emit("activeMute", state.audioState);
    };

    //props.initCurrentStudents();
    return {
      state,
      toggleParticipants,
      toggleChat,
      activeVideo,
      activeMute,
    };
  },

  components: {
    ParticipantsList,
    ChatForm,
    StudentOX,
    StudentInclass,
    OvVideo,
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
