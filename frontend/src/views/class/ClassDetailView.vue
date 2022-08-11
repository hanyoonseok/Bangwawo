<template>
  <div class="background">
    <HeaderNav />
    <section v-if="classInfo">
      <div class="back-btn-wrapper" @click="$router.go(-1)">
        <button class="back-btn"></button>
      </div>
      <div class="detail">
        <div class="empty" @click="hideProfile"></div>
        <div class="img-box">
          <img src="@/assets/backhead.png" alt="오리 뒷모습" />
          <!-- <img :src="state.thumbnail" alt="오리 뒷모습" /> -->
        </div>
        <div class="title">
          <h3>{{ classInfo.title }}</h3>
        </div>
        <div class="content">
          <article>
            <div class="left-box round-post-card">
              <img
                src="@/assets/thumbnail.png"
                alt="썸네일이미지"
                v-if="classInfo.thumbnail"
                class="left-box-img"
              />

              <img
                v-else
                :src="'http://localhost:8081/api' + classInfo.thumbnail"
                alt="썸네일이미지"
                class="left-box-img"
              />
            </div>
            <div class="right-box">
              <div class="info-box">
                <p class="info-title post-card">강사</p>
                <p class="info-content">
                  <span>{{ classInfo.vid.nickname }}</span>
                  <button @click="showProfile">
                    <i class="fa-solid fa-circle-info"></i>
                  </button>
                </p>
              </div>
              <div class="info-box">
                <p class="info-title post-card">강의 시간</p>
                <p class="info-content">
                  {{ classInfo.dateStr }} {{ classInfo.stimeStr }} ~
                  {{ classInfo.etimeStr }}
                </p>
              </div>
              <div class="info-box">
                <p class="info-title post-card">수업 소개</p>
                <p class="info-content">
                  {{ classInfo.introduce }}
                </p>
              </div>
              <div class="info-box">
                <p class="info-title post-card">공개</p>
                <p class="info-content">
                  {{ classInfo.opened ? "공개" : "비공개" }}
                </p>
              </div>
              <div class="info-box">
                <p class="info-title post-card">정원</p>
                <p class="info-content">{{ classInfo.maxcnt }}</p>
              </div>
            </div>
          </article>
          <div class="button-box">
            <!-- 봉사자(2), 학생(1) -->
            <div
              v-if="
                userInfo.userType === 'VOLUNTEER' &&
                userInfo.vid === classInfo.vid.vid
              "
            >
              <router-link :to="{ name: 'inclass' }">
                <button class="class-status-btn">
                  수업 활성화
                </button></router-link
              >

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
              <button v-else class="class-subscribe-btn" @click="enrolClass">
                수업 신청
              </button>
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
            <p class="info-content">{{ classInfo.vid.nickname }}</p>
          </div>
          <div class="info-box">
            <p class="info-title post-card">자기소개</p>
            <p class="info-content bg">
              {{ classInfo.vid.introduce }}
            </p>
          </div>
        </div>
      </div>
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
import { reactive, ref } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import axios from "axios";

export default {
  name: "ClassDetailView",
  components: {
    HeaderNav,
    // RoundPostCard,
  },
  setup() {
    const route = useRoute();
    const store = useStore();
    const userInfo = store.state.root.user;
    const cid = route.params.cid;
    const classInfo = ref(null);

    // 수업 상세정보 가져오기
    const getClassDetail = async () => {
      axios
        .get(`${process.env.VUE_APP_API_URL}/class/${cid}`)
        .then((response) => {
          classInfo.value = response.data;
          console.log(classInfo.value);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    getClassDetail();

    // 해당 수업 신청한 학생중에 현재 사용자가 있는지 확인하기 위함
    const getEnrolStudent = async () => {
      axios
        .get(`${process.env.VUE_APP_API_URL}/enrol/class/${cid}`)
        .then((response) => {
          console.log(response);
          let flag = false;
          for (const item of response.data) {
            if (item.student.sid === userInfo.sid) {
              flag = true;
              break;
            }
          }
          user.subscribe = flag;
        })
        .catch((error) => {
          error;
        });
    };

    getEnrolStudent();

    // 수업 신청하기
    const enrolClass = () => {
      axios
        .post(`${process.env.VUE_APP_API_URL}/enrol`, {
          cid: { cid },
          sid: { sid: userInfo.sid },
        })
        .then((response) => {
          console.log(response);
          user.subscribe = true;
        })
        .catch((error) => {
          error;
        });
    };

    const user = reactive({
      status: 2,
      subscribe: 0,
    });

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
      classInfo,
      user,
      userInfo,
      showProfile,
      hideProfile,
      enrolClass,
      isConfirm,
    };
  },
};
</script>

<style scoped src="@/css/classDetail.scss" lang="scss"></style>
