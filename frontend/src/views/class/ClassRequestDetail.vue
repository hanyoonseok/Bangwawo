<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section detail">
      <div class="back-btn-wrapper" @click="$router.go(-1)">
        <button class="back-btn"></button>
      </div>
      <article class="content-section">
        <div class="content-box">
          <!-- <img src="@/assets/profile.png" /> -->
          <ProfileCanvas class="header-img" />
          <div class="content-title">
            {{ state.requestPost.title }}
          </div>
          <div class="content-writer">
            {{ state.nickname }}
            <label
              >&nbsp;{{ state.createDate[0] }}.{{ state.createDate[1] }}.{{
                state.createDate[2]
              }}</label
            >
          </div>
          <div class="status-wrapper">
            <button class="status-btn no" v-if="!state.requestPost.solved">
              <i class="fa-solid fa-circle"></i>&nbsp;미해결
            </button>
            <button class="status-btn yes" v-else>
              <i class="fa-solid fa-circle"></i>&nbsp;해결
            </button>
          </div>
          <div class="content-text">
            {{ state.requestPost.content }}
          </div>
          <div class="end-btn-wrapper" v-if="state.userType === 'student'">
            <div class="no" v-if="!state.requestPost.solved">
              <img src="@/assets/notice-text.png" class="notice-img" />
              <span></span>
              <button
                class="end-btn"
                @mouseover="state.isVisibleNoticeImg = true"
                @mouseleave="state.isVisibleNoticeImg = false"
                @click="addLikeRequest"
              >
                <i class="fa-solid fa-thumbs-up"></i> &nbsp;{{
                  state.requestPost.likes
                }}
              </button>
              <div
                v-if="
                  state.userType === 'student' &&
                  state.userInfo.sid === state.requestPost.student.sid
                "
              >
                <button class="end-btn" @click="isConfirm.status = true">
                  <i class="fa-solid fa-trash-can"></i>&nbsp;삭제
                </button>
                <router-link
                  :to="{
                    name: 'classrequestmodify',
                    params: { rid: rid },
                  }"
                >
                  <button class="end-btn">
                    <i class="fa-solid fa-pen"></i>&nbsp;수정
                  </button></router-link
                >
              </div>
              <div v-else style="width: 100px"></div>
            </div>
            <router-link :to="{ name: 'classdetail' }" v-else>
              <div class="yes">
                <img :src="post.link.thumbnail" />
                <div class="column">
                  <label class="link-title">{{ post.link.title }}</label>
                  <label class="link-description">{{
                    post.link.description
                  }}</label>
                </div>
              </div></router-link
            >
          </div>

          <div class="end-btn-wrapper" v-if="state.userType === 'volunteer'">
            <div class="no" v-if="!state.requestPost.solved">
              <span></span>
              <router-link :to="{ name: 'classregister' }">
                <button class="end-btn">
                  <i class="fa-solid fa-check"></i> &nbsp; 수업 생성하기
                </button></router-link
              >
              <span> </span>
            </div>

            <div class="yes" v-else>
              <img :src="post.link.thumbnail" />

              <div class="column">
                <label class="link-title">{{ post.link.title }}</label>
                <label class="link-description">{{
                  post.link.description
                }}</label>
              </div>
            </div>
          </div>
          <div class="status-wrapper">
            <label>{{ state.requestPost.count }}</label>
          </div>
        </div>
      </article>
    </section>
    <div class="confirm" v-if="isConfirm.status">
      <div class="container">
        <img src="@/assets/profile.png" alt="오리" />
        <h4>정말로 삭제하시겠습니까?</h4>
        <div class="btn-wrapper">
          <button class="btn" @click="deleteRequest">네</button>
          <button class="btn" @click="isConfirm.status = false">아니요</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, watch } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
import ProfileCanvas from "@/components/mypage/ProfileCanvas.vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default {
  name: "ClassRequestDetail",
  components: {
    HeaderNav,
    ProfileCanvas,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    const post = ref({
      status: 1, // 0미해결 1해결
      link: {
        thumbnail: require("@/assets/banner-illust.png"),
        title: "오리선생님의 역사 이야기",
        description:
          "오리 선생님이 역사 이야기를 해줄거에요sadfasdfsdㅁㄴㅇㄻㄴㅇㄹㄴㅇㄻㄴㅇㄹㄴㅇㅁ랴ㅐㅗㄴㅇㅁ래ㅕㅗㅁㄴㅇ륨아너로먄여로ㅑㅕㅗㄹㄷㅈㅁ러ㅏㅁㄴ오러ㅏㅁㅇ노러ㅏㄴ",
      },
    });
    const isConfirm = reactive({
      status: false,
    });

    let state = reactive({
      requestPost: {},
      nickname: "",
      createDate: ["", "", ""],
      isVisibleNoticeImg: false,
      userInfo: store.state.root.user,
      userType: store.state.root.user.userType,
    });

    const rid = route.params.rid;
    const getRequest = async () => {
      axios
        .get(`${process.env.VUE_APP_API_URL}/request/${rid}`)
        .then((response) => {
          console.log(response.data.requsest);
          state.requestPost = response.data.requsest;
          state.nickname = state.requestPost.student.nickname;
          state.createDate = state.requestPost.createDate;
        });
    };

    getRequest();

    const deleteRequest = async () => {
      axios
        .delete(`${process.env.VUE_APP_API_URL}/request/${rid}`)
        .then((response) => {
          console.log(response);
        });

      router.push("/class/requestlist");
    };

    watch(
      () => state.isVisibleNoticeImg,
      (cur) => {
        const noticeImg = document.querySelector(".notice-img");
        if (cur) {
          noticeImg.style.opacity = "1";
        } else {
          noticeImg.style.opacity = "0";
        }
      },
    );

    const addLikeRequest = () => {};

    return {
      getRequest,
      deleteRequest,
      addLikeRequest,
      state,
      post,
      isConfirm,
      rid,
    };
  },
};
</script>

<style scoped src="@/css/classRequest.scss" lang="scss"></style>
