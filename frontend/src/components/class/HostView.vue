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
        <div class="user-card-wrapper">
          <div class="hover-wrapper">나</div>
          <div class="user-card"><OvVideo :stream-manager="me" /></div>
        </div>
        <!-- <div class="user-card-wrapper" v-for="(user, i) in subs" :key="user.id">
          <div class="hover-wrapper">이름{{ i }}</div>
          <div class="user-card"><OvVideo :stream-manager="user" /></div>
        </div> -->
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
import { reactive, computed } from "vue";
import ParticipantsList from "@/components/class/ParticipantsList.vue";
import ChatForm from "@/components/class/ChatForm.vue";
import OXForm from "@/components/class/OXForm.vue";
import OXResult from "@/components/class/OXResult.vue";
import OvVideo from "./OvVideo";

export default {
  name: "HostView",
  props: [
    "dataLen",
    "currentUsers",
    "prevClick",
    "nextClick",
    "leaveSession",
    "me",
    "subs",
  ],
  setup(props) {
    console.log("props.room", props.roomInfo);
    const state = reactive({
      isParticipantsOpen: false,
      isChatOpen: false,
      isOXOpen: false,
      isTopOpen: false,
      isOXResult: false,

      clientData: computed(() => {
        const { clientData } = getConnectionData();
        return clientData;
      }),
    });

    const getConnectionData = () => {
      console.log(props.me.stream);
      const { connection } = props.me.stream;
      return JSON.parse(connection.data);
    };

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
    OvVideo,
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
