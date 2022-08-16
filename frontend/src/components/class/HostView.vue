<template>
  <section class="main-section">
    <section class="top-section">
      <article
        :class="{
          'top-left': true,
          'host-2orless': subs.length < 2,
          'host-4orless': subs.length < 4 && subs.length >= 2,
          'host-6orless': subs.length < 6 && subs.length >= 4,
          'host-12orless': subs.length >= 6,
          expand: !state.isTopOpen && !state.isChatOpen,
        }"
      >
        <div class="idx-btn-wrapper next" @click="nextClick">
          <button class="idx-btn next"></button>
        </div>
        <div class="idx-btn-wrapper prev" @click="prevClick">
          <button class="idx-btn prev"></button>
        </div>
        <div id="container-screens">
          <h4>화면 공유</h4>
        </div>
        <div class="user-card-wrapper" id="myVideo">
          <div class="hover-wrapper">나</div>
          <div class="user-card">
            <user-video
              :stream-manager="me"
              @click="updateMainVideoStreamManager(me)"
            />
          </div>
        </div>
        <div class="user-card-wrapper" v-for="(user, i) in subs" :key="user.id">
          <div class="hover-wrapper">이름{{ i }}</div>
          <div class="user-card">
            <user-video
              :stream-manager="user"
              @click="updateMainVideoStreamManager(user)"
            />
          </div>
        </div>
      </article>

      <article class="top-right" v-if="state.isTopOpen || state.isChatOpen">
        <ParticipantsList
          :state="state"
          :toggleParticipants="toggleParticipants"
          :me="state.clientData"
          :subs="state.subs"
        />
<<<<<<< Updated upstream
        <OXForm :state="state" :toggleOX="toggleOX" />
        <OXResult :state="state" :toggleOX="toggleOX" />
=======
        <OXForm
          :state="state"
          :toggleOX="toggleOX"
          :session="session"
          @closeOX="closeOX"
        />
        <OXResult
          :state="state"
          :correctStudents="correctStudents"
          :incorrectStudents="incorrectStudents"
          :noneStudents="noneStudents"
          :toggleOX="toggleOX"
          v-if="oxResult || oxState"
        />
>>>>>>> Stashed changes
        <ChatForm
          :state="state"
          :toggleChat="toggleChat"
          :session="session"
          :chats="chats"
        />
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
          &nbsp;음소거 해제
        </button>
        <button class="option-btn" @click="activeVideo" v-if="state.videoState">
          <i class="fa-solid fa-video"></i>
          &nbsp;비디오 중지
        </button>
        <button class="option-btn" @click="activeVideo" v-else>
          <i class="fa-solid fa-video"></i>
          &nbsp;비디오 시작
        </button>
        <button
          class="option-btn"
          @click="publishScreenShare"
          id="screenShareStart"
        >
          <i class="fa-solid fa-arrow-up-from-square"></i>
          &nbsp;화면 공유 시작
        </button>
        <button class="option-btn" @click="toggleOX()">&nbsp;OX 퀴즈</button>
        <a @click="leaveSession">
          <i class="fa-solid fa-xmark xmark"></i>
        </a>
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
import UserVideo from "@/components/class/UserVideo.vue";

export default {
  name: "HostView",
  props: [
    "dataLen",
    "currentUsers",
    "prevClick",
    "nextClick",
    "me",
    "subs",
    "session",
    "chats",
    "screen",
    "cid",
<<<<<<< Updated upstream
=======
    "oxResult",
    "correctStudents",
    "incorrectStudents",
    "noneStudents",
>>>>>>> Stashed changes
  ],
  setup(props, { emit }) {
    console.log("@@@@@@@@@@@@me", props.me);
    console.log("=============subs", props.subs);

    const state = reactive({
      isParticipantsOpen: false,
      isChatOpen: false,
      isOXOpen: false,
      isTopOpen: false,
      isOXResult: false,
      videoState: true, // 비디오 on,off
      audioState: true, // 소리 on,off

      clientData: computed(() => {
        const { clientData } = getConnectionData();
        return clientData;
      }),

      subs: computed(() => {
        return getConnectionSubs();
      }),
    });

    const getConnectionData = () => {
      console.log(props.me.stream);
      const { connection } = props.me.stream;
      return JSON.parse(connection.data);
    };

    const getConnectionSubs = () => {
      console.log(props.subs);
      const arr = [];
      for (const item of props.subs) {
        console.log("item", item);
        let { connection } = item.stream;
        let { clientData } = JSON.parse(connection.data);
        arr.push(clientData);
      }
      console.log("arr", arr);

      return arr;
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

    const publishScreenShare = () => {
      emit("publishScreenShare");
    };

    // 수업 종료
    const leaveSession = (props) => {
      props.session
        .signal({
          data: 0,
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: "end", // The type of message (optional)
        })
        .then(() => {
          console.log("end");
        })
        .catch((error) => {
          console.error(error);
        });

      emit("leaveSession");
    };

    const updateMainVideoStreamManager = (stream) => {
      emit("updateMainVideoStreamManager", stream);
    };

    return {
      state,
      toggleParticipants,
      toggleChat,
      toggleOX,
      activeVideo,
      activeMute,
      publishScreenShare,
      leaveSession,
      updateMainVideoStreamManager,
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

<style scoped src="@/css/class.scss" lang="scss">
/* .top-section ::v-deep(#container-screens video) {
  width: 100px;
  height: 100px;
} */

#container-screens video {
  position: relative;
  width: 100px;
  height: 100px;
}
</style>
