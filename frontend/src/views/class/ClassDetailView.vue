<template>
  <div class="background">
    <HeaderNav />
    <section>
      <div class="back-btn-wrapper" @click="$router.go(-1)">
        <button class="back-btn"></button>
      </div>
      <div class="detail">
        <div class="empty" @click="hideProfile"></div>
        <div class="img-box">
          <img src="@/assets/backhead.png" alt="오리 뒷모습" />
        </div>
        <div class="title"><h3>오리 선생님의 수학수업</h3></div>
        <div class="content">
          <article>
            <div class="left-box round-post-card">
              <img src="@/assets/banner-illust.png" class="left-box-img" />
            </div>
            <div class="right-box">
              <div class="info-box">
                <p class="info-title post-card">강사</p>
                <p class="info-content">
                  <span>김오리</span>
                  <button @click="showProfile">
                    <i class="fa-solid fa-circle-info"></i>
                  </button>
                </p>
              </div>
              <div class="info-box">
                <p class="info-title post-card">강의 시간</p>
                <p class="info-content">김오리</p>
              </div>
              <div class="info-box">
                <p class="info-title post-card">수업 소개</p>
                <p class="info-content">
                  sdadsdassssssssssssssssssssssss라라라ㅏ라라랄라sssssssssssssssssssssssss
                </p>
              </div>
              <div class="info-box">
                <p class="info-title post-card">공개</p>
                <p class="info-content">김오리</p>
              </div>
              <div class="info-box">
                <p class="info-title post-card">인원</p>
                <p class="info-content">김오리</p>
              </div>
            </div>
          </article>
          <div class="button-box">
            <!-- 봉사자(2), 학생(1) -->
            <div v-if="user.status === 2">
              <button class="class-status-btn">수업 활성화</button>

              <router-link to="/class/modify" class="class-modify-btn">
                <i class="fa-solid fa-pencil"></i>
              </router-link>
              <button class="class-delete-btn" @click="isConfirm.status = true">
                <i class="fa-solid fa-trash-can"></i>
              </button>
            </div>
            <div v-else>
              <!-- 수업 신청한 경우(1), 수업 신청 안한 경우(0) -->
              <button v-if="user.subscribe === 1" class="class-entrance-btn">
                수업 입장
              </button>
              <button v-else class="class-subscribe-btn">수업 신청</button>
            </div>
          </div>
        </div>
      </div>
      <!-- 강사 프로필 -->
      <transition name="slide-fade">
        <div class="profile">
          <div class="profile-img">
            <img src="@/assets/profile.png" alt="프로필이미지" />
          </div>
          <div class="profile-info">
            <div class="info-box">
              <p class="info-title post-card">이름</p>
              <p class="info-content">김오리</p>
            </div>
            <div class="info-box">
              <p class="info-title post-card">자기소개</p>
              <p class="info-content bg">
                나는 아주 착한 봉사자 선한 봉사자 다정한 봉사자 내가 최고다
              </p>
            </div>
          </div>
        </div></transition
      >
    </section>
    <div class="confirm" v-if="isConfirm.status">
      <div class="container">
        <img src="@/assets/profile.png" alt="오리" />
        <h2>정말로 삭제하시겠습니까?</h2>
        <div class="btn-wrapper">
          <button class="btn">네</button>
          <button class="btn" @click="isConfirm.status = false">아니요</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import { reactive } from "vue";

export default {
  name: "ClassDetailView",
  components: {
    HeaderNav,
    // RoundPostCard,
  },
  setup() {
    const state = {
      classTeacher: "김오리",
      classTime: "22.07.20 13:00 ~ 15:00",
      classContent:
        "김오리의 수학수업이다. 김오리의 수학쉅이다. 김오리 수학 수업 김올",
      classOpen: true,
      classPeople: "12/45",
    };

    const user = {
      status: 2,
      subscribe: 0,
    };

    const showProfile = () => {
      if (document.querySelector(".profile").style.display === "block") {
        document.querySelector(".profile").style.display = "none";
      } else {
        document.querySelector(".profile").style.display = "block";
      }
    };

    const hideProfile = () => {
      if (document.querySelector(".profile").style.display === "block") {
        document.querySelector(".profile").style.display = "none";
      }
    };

    const isConfirm = reactive({
      status: false,
    });

    return {
      state,
      user,
      showProfile,
      hideProfile,
      isConfirm,
    };
  },
};
</script>

<style scoped src="@/css/classDetail.scss" lang="scss"></style>
