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
            애기오리는 이러이러한 수업이 듣고싶습니다. 대충 아무제목...
          </div>
          <div class="content-writer">
            애기오리 <label>&nbsp;2022.07.20</label>
          </div>
          <div class="status-wrapper">
            <button class="status-btn no" v-if="post.status === 0">
              <i class="fa-solid fa-circle"></i>&nbsp;미해결
            </button>
            <button class="status-btn yes" v-if="post.status === 1">
              <i class="fa-solid fa-circle"></i>&nbsp;해결
            </button>
          </div>
          <div class="content-text">
            애기오리는 오리선생의 역사이야기가 너무 듣고싶습니다. <br />오리선생
            너무 귀여워용 ~~
          </div>
          <div class="end-btn-wrapper" v-if="user.status === 0">
            <div class="no" v-if="post.status === 0">
              <img src="@/assets/notice-text.png" class="notice-img" />
              <span></span>
              <button class="end-btn">
                <i class="fa-solid fa-thumbs-up"></i> &nbsp;99
              </button>
              <div>
                <button class="end-btn">
                  <i class="fa-solid fa-trash-can"></i>&nbsp;삭제
                </button>
                <router-link :to="{ name: 'classrequestmodify' }">
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

          <div class="end-btn-wrapper" v-if="user.status === 1">
            <div class="no" v-if="post.status === 0">
              <span></span>
              <button class="end-btn">
                <i class="fa-solid fa-check"></i> &nbsp; 수업 생성하기
              </button>
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
            >
          </div>
          <div class="status-wrapper"><label>조회수 33</label></div>
        </div>
      </article>
    </section>
  </div>
</template>

<script>
import { ref } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  name: "ClassRequestDetail",
  components: {
    HeaderNav,
  },
  setup() {
    const post = ref({
      status: 0, // 0미해결 1해결
      link: {
        thumbnail: require("@/assets/banner-illust.png"),
        title: "오리선생님의 역사 이야기",
        description:
          "오리 선생님이 역사 이야기를 해줄거에요sadfasdfsdㅁㄴㅇㄻㄴㅇㄹㄴㅇㄻㄴㅇㄹㄴㅇㅁ랴ㅐㅗㄴㅇㅁ래ㅕㅗㅁㄴㅇ륨아너로먄여로ㅑㅕㅗㄹㄷㅈㅁ러ㅏㅁㄴ오러ㅏㅁㅇ노러ㅏㄴ",
      },
    });
    const user = ref({
      status: 0, //0학생 1봉사자 2부모
    });

    return {
      user,
      post,
    };
  },
};
</script>

<style scoped src="@/css/classRequest.scss" lang="scss"></style>
