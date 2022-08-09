<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section detail">
      <div class="back-btn-wrapper" @click="$router.go(-1)">
        <button class="back-btn"></button>
      </div>
      <article class="content-section">
        <div class="content-box">
          <img src="@/assets/profile.png" class="header-img" />
          <div class="content-title">
            {{ state.requestPost.title }}
          </div>
          <div class="content-writer">
            애기오리 <label>&nbsp;2022.07.20</label>
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
          <div class="end-btn-wrapper" v-if="userType === 'student'">
            <div class="no" v-if="!state.requestPost.solved">
              <img src="@/assets/notice-text.png" class="notice-img" />
              <span></span>
              <button class="end-btn">
                <i class="fa-solid fa-thumbs-up"></i> &nbsp;{{
                  state.requestPost.likes
                }}
              </button>
              <div>
                <button class="end-btn">
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

          <div class="end-btn-wrapper" v-if="userType === 'volunteer'">
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
  </div>
</template>

<script>
import { ref, reactive } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
import axios from "axios";
// import { useRouter } from "vue-router";
import { useRoute } from "vue-router";

export default {
  name: "ClassRequestDetail",
  components: {
    HeaderNav,
  },
  setup() {
    const route = useRoute();
    // const router = useRouter();z
    const post = ref({
      status: 1, // 0미해결 1해결
      link: {
        thumbnail: require("@/assets/banner-illust.png"),
        title: "오리선생님의 역사 이야기",
        description:
          "오리 선생님이 역사 이야기를 해줄거에요sadfasdfsdㅁㄴㅇㄻㄴㅇㄹㄴㅇㄻㄴㅇㄹㄴㅇㅁ랴ㅐㅗㄴㅇㅁ래ㅕㅗㅁㄴㅇ륨아너로먄여로ㅑㅕㅗㄹㄷㅈㅁ러ㅏㅁㄴ오러ㅏㅁㅇ노러ㅏㄴ",
      },
    });

    let userInfo = ref(null);
    let userId;
    let userType;
    const getUserInfo = async () => {
      userInfo.value = JSON.parse(localStorage.getItem("user"));
      userType = "student";
      userId =
        userType === "volunteer" ? userInfo.value.vid : userInfo.value.sid;
    };
    let state = reactive({
      requestPost: {},
    });
    const rid = route.params.rid;
    getUserInfo();
    const getRequest = async () => {
      axios
        .get(`${process.env.VUE_APP_API_URL}/request/${rid}/${userId}`)
        .then((response) => {
          state.requestPost = response.data.requsest;
        });
    };
    getRequest();

    return {
      getRequest,
      getUserInfo,
      userType,
      state,
      post,
      userId,
      rid,
    };
  },
};
</script>

<style scoped src="@/css/classRequest.scss" lang="scss"></style>
