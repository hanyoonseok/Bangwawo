<template>
  <div class="background">
    <HeaderNav />
    <section>
      <button class="back">
        <i class="fa-solid fa-chevron-left"></i>
      </button>
      <div class="detail">
        <div class="img-box">
          <img src="@/assets/backhead.png" alt="오리 뒷모습" />
        </div>
        <div class="title"><h3>오리 선생님의 수학수업</h3></div>
        <div class="content">
          <article>
            <div class="left-box round-post-card"></div>
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
              <button class="classStatusBtn">수업 활성화</button>
              <button class="classModifyBtn">
                <i class="fa-solid fa-pencil"></i>
              </button>
              <button class="classDeleteBtn">
                <i class="fa-solid fa-trash-can"></i>
              </button>
            </div>
            <div v-else>
              <!-- 수업 신청한 경우(1), 수업 신청 안한 경우(0) -->
              <button v-if="user.subscribe === 1" class="classEntranceBtn">
                수업 입장
              </button>
              <button v-else class="classSubscribeBtn">수업 신청</button>
            </div>
          </div>
        </div>
      </div>
      <!-- 강사 프로필 -->
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
              반가워어ㅓdfsdfsdfsd어ㅓㅓ안여ㅏㅇ나녕안여넝어너어어어 나는 아주
              착한 봉사자 선한 봉사자 다정한 봉사자 내가 최고다
              반가워어ㅓdfsdfsdfsd어ㅓㅓ안여ㅏㅇ나녕안여넝어너어어어
            </p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";

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

    return {
      state,
      user,
    };
  },
  methods: {
    showProfile() {
      if (document.querySelector(".profile").style.display === "block") {
        document.querySelector(".profile").style.display = "none";
      } else {
        document.querySelector(".profile").style.display = "block";
      }
    },
  },
};
</script>

<style scoped src="@/css/classDetail.scss" lang="scss"></style>
