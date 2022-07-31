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
        <article class="top-article-left top">
          <div class="next-btn-wrapper"><button class="next-btn"></button></div>
          <div
            class="user-card-wrapper"
            v-for="student in students"
            :key="student.id"
          >
            <div class="user-card">s</div>
            <label>{{ student.name }}</label>
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
import { reactive, ref } from "vue";
import ParticipantsList from "@/components/class/ParticipantsList.vue";
import ChatForm from "@/components/class/ChatForm.vue";
import StudentOX from "@/components/class/StudentOX.vue";
import StudentInclass from "@/components/class/StudentInclass.vue";

export default {
  name: "UserView",
  setup() {
    const students = ref([
      {
        id: 1,
        name: "김수빈수빈1",
      },
      {
        id: 2,
        name: "김수빈수빈2",
      },
      {
        id: 3,
        name: "김수빈수빈3",
      },
      {
        id: 4,
        name: "김수빈수빈4",
      },
      {
        id: 5,
        name: "김수빈수빈5",
      },
      {
        id: 6,
        name: "김수빈수빈6",
      },
    ]);

    const state = reactive({
      isParticipantsOpen: false,
      isChatOpen: false,
      isTopOpen: false,
      isOXOpen: false,
    });

    const toggleParticipants = () => {
      state.isParticipantsOpen = !state.isParticipantsOpen;
      state.isTopOpen = state.isParticipantsOpen;
      console.log(state);
    };

    const toggleChat = () => {
      state.isChatOpen = !state.isChatOpen;
    };

    return {
      state,
      toggleParticipants,
      toggleChat,
      students,
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
