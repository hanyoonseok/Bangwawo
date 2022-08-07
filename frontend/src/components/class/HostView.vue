<template>
  <section class="main-section">
    <section class="top-section">
      <article
        :class="{
          'top-left': true,
          'host-2orless': roomInfo.subscribers.length <= 2,
          'host-4orless':
            roomInfo.subscribers.length <= 4 && roomInfo.subscribers.length > 2,
          'host-6orless':
            roomInfo.subscribers.length <= 6 && roomInfo.subscribers.length > 4,
          'host-12orless': roomInfo.subscribers.length > 6,
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
          v-for="student in roomInfo.subscribers"
          :key="student.stream.connection.connectionId"
        >
          <user-video
            :stream-manager="student"
            @click="updateMainVideoStreamManager(student)"
          />

          <div class="hover-wrapper">이름들어갈곳</div>
        </div>
        <div class="volunteer-video">
          <user-video
            :stream-manager="roomInfo.publisher"
            @click="updateMainVideoStreamManager(roomInfo.publisher)"
          />
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
        <button class="option-btn red" @click="doMute">
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
import UserVideo from "@/components/class/UserVideo.vue";

export default {
  name: "HostView",
  props: [
    "dataLen",
    "currentStudents",
    "initCurrentStudents",
    "prevClick",
    "nextClick",
    "streamManager",
    "roomInfo",
    "joinSession",
  ],
  setup(props) {
    console.log("props.room", props.roomInfo);
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

    const doMute = (e) => {
      console.log(e.target);
      console.log(props.roomInfo);
    };
    props.joinSession();
    onMounted(() => {
      console.log(props.streamManager);
      // const instance = getCurrentInstance();
      // props.roomInfo.publisher.addVideoElement(this.$el);
    }),
      props.initCurrentStudents();
    return {
      state,
      toggleParticipants,
      toggleChat,
      toggleOX,
      doMute,
    };
  },
  components: {
    ParticipantsList,
    ChatForm,
    OXForm,
    OXResult,
    UserVideo,
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
