<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section">
      <article class="title-wrapper">
        <h2>학생 리뷰</h2>
      </article>
      <article class="list-wrapper">
        <div
          class="profile"
          v-for="student in students"
          :key="student.id"
          @click="selected = student"
        >
          <img :src="student.img" />
          <div class="person-info">
            <label>이름</label>
            {{ student.name }}
          </div>
          <div class="person-info">
            <label>별명</label>{{ student.nickname }}
          </div>
        </div>
      </article>
      <article class="btn-wrapper">
        <button class="submit-btn" @click="isConfirm = true">
          피드백 제출
        </button>
      </article>
    </section>
    <FeedbackModal v-if="selected" :info="selected" :close="close" />
    <div class="confirm" v-if="isConfirm">
      <div class="container">
        <img src="@/assets/profile.png" alt="오리" />
        <h2>정말로 제출하시겠습니까?</h2>
        <div class="btn-wrapper">
          <router-link :to="{ name: 'mypage' }"
            ><button class="btn">네</button></router-link
          >
          <button class="btn" @click="isConfirm = false">아니요</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
import FeedbackModal from "@/components/feedback/FeedbackModal.vue";
import { useRoute } from "vue-router";

export default {
  name: "FeedBack",
  components: {
    HeaderNav,
    FeedbackModal,
  },
  props: ["cid"],
  setup() {
    const route = useRoute();
    console.log(route.params.cid);

    const students = ref([
      {
        id: 1,
        img: require("@/assets/profile.png"),
        name: "이화연",
        nickname: "애기화연",
      },
      {
        id: 2,
        img: require("@/assets/profile.png"),
        name: "이수빈",
        nickname: "애기수빈",
      },
      {
        id: 3,
        img: require("@/assets/profile.png"),
        name: "이진호",
        nickname: "애기진호",
      },
      {
        id: 4,
        img: require("@/assets/profile.png"),
        name: "이윤석",
        nickname: "애기윤석",
      },
      {
        id: 5,
        img: require("@/assets/profile.png"),
        name: "이다빈",
        nickname: "애기다빈",
      },
      {
        id: 6,
        img: require("@/assets/profile.png"),
        name: "이반장",
        nickname: "애기반장",
      },
      {
        id: 7,
        img: require("@/assets/profile.png"),
        name: "이CA",
        nickname: "애기CA",
      },
    ]);

    let selected = ref(null);

    let isConfirm = ref(false);

    const close = (e) => {
      e.stopPropagation();
      selected.value = null;
    };

    return {
      students,
      selected,
      close,
      isConfirm,
    };
  },
};
</script>

<style scoped src="@/css/feedback.scss" lang="scss"></style>
