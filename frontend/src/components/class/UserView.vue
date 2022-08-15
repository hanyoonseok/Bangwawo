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
          <!-- <div class="idx-btn-wrapper next" @click="nextClick">
            <button class="idx-btn next"></button>
          </div>
          <div class="idx-btn-wrapper prev" @click="prevClick">
            <button class="idx-btn prev"></button>
          </div> -->
          <div class="user-card-wrapper" id="myVideo">
            <div class="hover-wrapper">나</div>
            <div class="user-card" @click="updateMainVideoStreamManager(me)">
              <OvVideo :stream-manager="me" :onEmotion="true" />
            </div>
          </div>
          <div class="user-card-wrapper" v-for="(user, i) in students" :key="i">
            <div class="hover-wrapper">
              {{ getClientData(user) }}
            </div>
            <div class="user-card" @click="updateMainVideoStreamManager(user)">
              <OvVideo :stream-manager="user" />
            </div>
          </div>
        </article>
        <article class="top-article-left bot">
          <StudentOX v-if="oxState || ox" :oxData="oxData" @closeOX="closeOX" />
          <StudentInclass :publisher="volunteer" :screen="screen" v-else />
        </article>
      </article>

      <article class="top-right" v-if="state.isTopOpen || state.isChatOpen">
        <ParticipantsList
          :state="state"
          :toggleParticipants="toggleParticipants"
          :me="state.clientData"
          :subs="state.subs"
        />
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
import { reactive, computed, ref, watch } from "vue";
import ParticipantsList from "@/components/class/ParticipantsList.vue";
import ChatForm from "@/components/class/ChatForm.vue";
import StudentOX from "@/components/class/StudentOX.vue";
import StudentInclass from "@/components/class/StudentInclass.vue";
import OvVideo from "./OvVideo";
import { useStore } from "vuex";

export default {
  name: "UserView",
  props: [
    "dataLen",
    "currentUsers",
    "prevClick",
    "nextClick",
    "session",
    "chats",
    "screen",
    "me",
    "subs",
    "volunteerNickname",
    "ox",
    "oxData",
  ],
  setup(props, { emit }) {
    console.log("@@@@@@@@@@@@me", props.me);
    console.log("=============subs", props.subs);

    const volunteer = ref(null);
    const students = ref(null);

    let oxState = ref(false);

    watch(
      () => props.ox,
      () => {
        if (props.ox) {
          oxState = true;
        } else {
          oxState = false;
        }
      },
      { deep: true },
    );

    watch(
      () => props.subs,
      () => {
        // console.log("watch", cur);
        const arr = [];
        for (const item of props.subs) {
          const { connection } = item.stream;
          const data = JSON.parse(connection.data);
          const { clientData } = data;
          if (clientData === props.volunteerNickname) {
            volunteer.value = item;
          } else {
            arr.push(item);
          }
        }
        students.value = arr;
        // console.log("나머지 학생들이 담기나?", students.value);
        // console.log("봉사자가 담기나?", volunteer.value);
      },
      { deep: true },
    );

    console.log("~~~~~~~~~~~~~~~~~~~session", props.session);
    const store = useStore();
    store.commit("root/initEmotion");

    const state = reactive({
      isParticipantsOpen: false,
      isChatOpen: false,
      isTopOpen: false,
      isOXOpen: false,
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

    const getClientData = (user) => {
      let { connection } = user.stream;
      let { clientData } = JSON.parse(connection.data);
      return clientData;
    };

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

    const updateMainVideoStreamManager = (stream) => {
      emit("updateMainVideoStreamManager", stream);
    };

    // 수업 종료
    const leaveSession = () => {
      emit("leaveSession");
    };

    const closeOX = (answer) => {
      console.log("학생의 답??", answer);
      oxState.value = false;
      //학생이 맞았는지 틀렸는지 판단해서 signal 보내야함
      props.session
        .signal({
          data: answer,
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: "ox-end", // The type of message (optional)
        })
        .then(() => {
          console.log("ox-end");
        })
        .catch((error) => {
          console.error(error);
        });
    };

    //props.initCurrentStudents();
    return {
      state,
      volunteer,
      students,
      toggleParticipants,
      toggleChat,
      activeVideo,
      activeMute,
      getConnectionSubs,
      updateMainVideoStreamManager,
      leaveSession,
      getClientData,
      closeOX,
      oxState,
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
