<template>
  <section class="main-section">
    <section class="top-section">
      <article
        :class="{
          'top-left': true,
          host: true,
          expand: !state.isTopOpen && !state.isChatOpen,
        }"
      >
        <div class="idx-btn-wrapper next" @click="nextClick">
          <button class="idx-btn next"></button>
        </div>
        <div class="idx-btn-wrapper prev" @click="prevClick">
          <button class="idx-btn prev"></button>
        </div>
        <div
          class="user-card-wrapper"
          v-for="student in currentStudents"
          :key="student.id"
        >
          <video autoplay />
          <div class="hover-wrapper">{{ student.name }}</div>
          <div class="user-card"></div>
        </div>
      </article>

      <article class="top-right" v-if="state.isTopOpen || state.isChatOpen">
        <ParticipantsList
          :state="state"
          :toggleParticipants="toggleParticipants"
        />
        <OXForm :state="state" :toggleOX="toggleOX" />
        <OXResult :state="state" :toggleOX="toggleOX" />
        <ChatForm :state="state" :toggleChat="toggleChat" />
      </article>
    </section>

    <section class="bot-section">
      <article class="bot-left">
        <button class="option-btn red">
          <i class="fa-solid fa-microphone-slash"></i>
          &nbsp;음소거 해제
        </button>
        <button class="option-btn">
          <i class="fa-solid fa-video"></i>
          &nbsp;비디오 시작
        </button>
        <button class="option-btn">
          <i class="fa-solid fa-arrow-up-from-square"></i>
          &nbsp;화면 공유
        </button>
        <button class="option-btn" @click="toggleOX()">&nbsp;OX 퀴즈</button>

        <router-link :to="{ name: 'feedbackSubmit' }">
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
import { reactive, onMounted } from "vue";
import ParticipantsList from "@/components/class/ParticipantsList.vue";
import ChatForm from "@/components/class/ChatForm.vue";
import OXForm from "@/components/class/OXForm.vue";
import OXResult from "@/components/class/OXResult.vue";

export default {
  name: "HostView",
  props: [
    "dataLen",
    "currentStudents",
    "initCurrentStudents",
    "prevClick",
    "nextClick",
    "streamManager",
  ],
  setup(props) {
    const state = reactive({
      isParticipantsOpen: false,
      isChatOpen: false,
      isOXOpen: false,
      isTopOpen: false,
      isOXResult: false,
    });

    const toggleParticipants = () => {
      if (state.isOXOpen) state.isOXOpen = false;
      state.isParticipantsOpen = !state.isParticipantsOpen;
      if (state.isParticipantsOpen || state.isOXOpen) state.isTopOpen = true;
      else state.isTopOpen = false;
    };

    const toggleOX = () => {
      state.isParticipantsOpen = false;
      state.isChatOpen = false;
      state.isOXOpen = !state.isOXOpen;
      if (state.isParticipantsOpen || state.isOXOpen) state.isTopOpen = true;
      else state.isTopOpen = false;
      console.log(state);
    };

    const toggleChat = () => {
      state.isChatOpen = !state.isChatOpen;
    };

    onMounted(() => {
      props.streamManager.addVideoElement(this.$el);
    }),
      props.initCurrentStudents();
    return {
      state,
      toggleParticipants,
      toggleChat,
      toggleOX,
    };
  },
  components: {
    ParticipantsList,
    ChatForm,
    OXForm,
    OXResult,
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
