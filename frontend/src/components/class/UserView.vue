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
          <div
            class="user-card-wrapper"
            v-for="student in currentStudents"
            :key="student.id"
          >
            <div class="hover-wrapper">{{ student.name }}</div>
            <div class="user-card"></div>
          </div>
        </article>
        <article class="top-article-left bot">
          <StudentOX v-if="state.isOXOpen" />
          <StudentInclass v-else />
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
        <button class="option-btn red">
          <i class="fa-solid fa-microphone-slash"></i>
          &nbsp;음소거 해제
        </button>
        <button class="option-btn">
          <i class="fa-solid fa-video"></i>
          &nbsp;비디오 시작
        </button>
        <i class="fa-solid fa-xmark xmark"></i>
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

export default {
  name: "UserView",
  props: [
    "dataLen",
    "currentStudents",
    "initCurrentStudents",
    "prevClick",
    "nextClick",
  ],
  setup(props, { emit }) {
    const state = reactive({
      isParticipantsOpen: false,
      isChatOpen: false,
      isTopOpen: false,
      isOXOpen: false,
    });

    const toggleParticipants = () => {
      state.isParticipantsOpen = !state.isParticipantsOpen;
      state.isTopOpen = state.isParticipantsOpen;
      const len = !state.isTopOpen && !state.isChatOpen ? 4 : 5;
      if (len != props.dataLen.value) {
        emit("changeDataLen", len);
        props.initCurrentStudents();
      }
    };

    const toggleChat = () => {
      state.isChatOpen = !state.isChatOpen;
      const len = !state.isTopOpen && !state.isChatOpen ? 4 : 5;
      if (len != props.dataLen.value) {
        emit("changeDataLen", len);
        props.initCurrentStudents();
      }
    };

    props.initCurrentStudents();
    return {
      state,
      toggleParticipants,
      toggleChat,
    };
  },
  components: {
    ParticipantsList,
    ChatForm,
    StudentOX,
    StudentInclass,
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
