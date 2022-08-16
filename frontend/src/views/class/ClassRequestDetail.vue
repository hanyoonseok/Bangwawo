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
          <RequestPostCanvas
            v-if="state.characterColor"
            :characterColor="state.characterColor"
            class="header-img"
          />
          <div class="content-title">
            {{ requestInfo.title }}
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
            <button class="status-btn no" v-if="!requestInfo.solved">
              <i class="fa-solid fa-circle"></i>&nbsp;미해결
            </button>
            <button class="status-btn yes" v-else>
              <i class="fa-solid fa-circle"></i>&nbsp;해결
            </button>
          </div>
          <div class="content-text">
            {{ requestInfo.content }}
          </div>
          <div class="end-btn-wrapper" v-if="userInfo.userType === 'student'">
            <div class="no" v-if="!requestInfo.solved">
              <img src="@/assets/notice-text.png" class="notice-img" />
              <span></span>
              <button
                class="end-btn"
                @mouseover="state.isVisibleNoticeImg = true"
                @mouseleave="state.isVisibleNoticeImg = false"
                @click="addLikeRequest"
              >
                <i class="fa-solid fa-thumbs-up"></i> &nbsp;{{
                  requestInfo.likes
                }}
              </button>
              <div
                v-if="
                  userInfo.userType === 'student' &&
                  userInfo.sid === state.postSid
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
            <router-link
              :to="{ name: 'classdetail', params: { cid: classInfo.cid } }"
              v-else
            >
              <div class="yes">
                <img :src="classInfo.thumbnail" />
                <div class="column">
                  <label class="link-title">{{ classInfo.title }}</label>
                  <label class="link-description">{{
                    classInfo.introduce
                  }}</label>
                </div>
              </div></router-link
            >
          </div>

          <div class="end-btn-wrapper" v-if="userInfo.userType === 'volunteer'">
            <div class="no" v-if="!requestInfo.solved">
              <span></span>
              <router-link :to="{ name: 'classregister' }">
                <button class="end-btn">
                  <i class="fa-solid fa-check"></i> &nbsp; 수업 생성하기
                </button></router-link
              >
              <span> </span>
            </div>
            <router-link
              :to="{ name: 'classdetail', params: { cid: classInfo.cid } }"
              v-else
            >
              <div class="yes">
                <img :src="classInfo.thumbnail" />

                <div class="column">
                  <label class="link-title">{{ classInfo.title }}</label>
                  <label class="link-description">{{
                    classInfo.introduce
                  }}</label>
                </div>
              </div>
            </router-link>
          </div>
          <div class="status-wrapper">
            <label>{{ requestInfo.count }}</label>
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
import RequestPostCanvas from "@/components/class/RequestPostCanvas.vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default {
  name: "ClassRequestDetail",
  components: {
    HeaderNav,
    RequestPostCanvas,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    const isConfirm = reactive({
      status: false,
    });

    const user = ref(store.state.root.user);
    const userInfo = reactive(store.state.root.user);

    let state = reactive({
      characterColor: null,
      postSid: null,
      nickname: "",
      createDate: ["", "", ""],
      isVisibleNoticeImg: false,
    });
    const requestInfo = ref("");
    const classInfo = ref("");

    const rid = route.params.rid;
    const getRequest = async () => {
      await axios
        .get(`${process.env.VUE_APP_API_URL}/request/${rid}`)
        .then((response) => {
          console.log(response.data);
          requestInfo.value = response.data.requsest;
          classInfo.value = response.data.classinfo;
          state.createDate = requestInfo.value.createDate;
          state.nickname = requestInfo.value.student.nickname;
          state.postSid = requestInfo.value.student.sid;
          state.characterColor = requestInfo.value.student.character;
          console.log("캐릭터컬러", state.characterColor);
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

    // 좋아요 추가
    const addLikeRequest = () => {
      store
        .dispatch("root/addLikeRequest", {
          rid: rid,
          sid: user.value.sid,
        })
        .then((response) => {
          console.log(response);
          // 좋아요 누르면 다시 정보
          getRequest();
        })
        .catch((error) => {
          console.log(error);
        });
    };

    return {
      getRequest,
      deleteRequest,
      addLikeRequest,
      state,
      userInfo,
      isConfirm,
      classInfo,
      requestInfo,
      rid,
    };
  },
};
</script>

<style scoped src="@/css/classRequest.scss" lang="scss"></style>
