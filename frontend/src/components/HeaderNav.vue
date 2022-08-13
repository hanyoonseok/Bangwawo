<template>
  <div class="container">
    <section class="left">
      <div class="listdiv" v-if="!user">
        <router-link :to="{ name: 'home' }" class="list">
          <div><img src="@/assets/header.png" /></div>
        </router-link>
        <router-link :to="{ name: 'classlist' }" class="list"
          >수업목록</router-link
        >
      </div>
      <div class="listdiv" v-if="user && user.userType === 'student'">
        <router-link :to="{ name: 'classlist' }" class="list"
          ><div><img src="@/assets/header.png" /></div
        ></router-link>
        <router-link :to="{ name: 'classlist' }" class="list"
          >수업목록</router-link
        >
        <router-link :to="{ name: 'classrequest' }" class="list"
          >수업요청</router-link
        >
        <router-link :to="{ name: 'secret' }" class="list"
          >비밀친구</router-link
        >
      </div>
      <div class="listdiv" v-if="user && user.userType === 'volunteer'">
        <router-link :to="{ name: 'classlist' }" class="list">
          <div><img src="@/assets/header.png" /></div>
        </router-link>
        <router-link :to="{ name: 'classlist' }" class="list"
          >수업목록</router-link
        >
        <router-link
          :to="{ name: 'classregister', params: { rid: -1 } }"
          class="list"
          >수업등록</router-link
        >
        <router-link :to="{ name: 'classrequest' }" class="list"
          >수업요청</router-link
        >
      </div>
      <div class="listdiv" v-if="user && user.userType === 'parent'">
        <div><img src="@/assets/header.png" /></div>
        <router-link :to="{ name: 'classlist' }" class="list"
          >수업목록</router-link
        >
        <router-link :to="{ name: 'classrequest' }" class="list"
          >수업요청</router-link
        >
      </div>
    </section>

    <section class="right">
      <router-link :to="{ name: 'login' }" v-if="!user">
        <button class="login">로그인</button>
      </router-link>
      <div class="img-wrapper" v-if="user">
        <img src="@/assets/profile.png" @click="toggleProfileModal" />
        <ul v-if="isProfileOpen">
          <router-link :to="{ name: 'mypage' }">마이페이지</router-link>
          <a href="" @click.prevent="logout">로그아웃</a>
        </ul>
      </div>
      <div class="bell-wrapper" v-if="user && user.userType != 'volunteer'">
        <i class="fa-solid fa-bell" @click="toggleNoticeModal"
          ><div class="count">{{ notices.length }}</div></i
        >

        <article class="modal" v-if="isNoticeOpen">
          <div class="title-wrapper">
            <i class="fa-solid fa-bell"></i>
            알림창
          </div>
          <div class="row-wrapper" v-if="user.userType === 'parent'">
            <div class="row" v-for="notice in notices" :key="notice.id">
              <img src="@/assets/profile.png" />
              <label
                ><p>자녀의 상담중 위험 용어가 발생했습니다.</p>
                <p class="date">2022-07-25</p></label
              >
              <i class="fa-solid fa-xmark close"></i>
            </div>
          </div>
          <div class="row-wrapper" v-else>
            <div class="row" v-for="(notice, index) in notices" :key="index">
              <label>
                <p>
                  요청글
                  <span class="notice-title">{{ notice.rtitle }}</span> 의
                  수업이 개설되었습니다.
                </p>
              </label>
              <router-link
                :to="{
                  name: 'classrequestdetail',
                  params: { rid: notice.rid },
                }"
                @click="checkAlarm(notice.rid)"
                ><i
                  class="fa-solid fa-circle-arrow-right"
                  style="font-size: 24px"
                ></i
              ></router-link>
            </div>
          </div>
        </article>
      </div>
      <div
        v-if="user && user.userType === 'volunteer'"
        @click="changeTalkableState"
      >
        <button class="consult on" v-if="user.talkable">
          <i class="fa-solid fa-circle"></i>&nbsp;상담 ON
        </button>
        <button class="consult off" v-else>
          <i class="fa-solid fa-circle"></i>&nbsp;상담 OFF
        </button>
      </div>
    </section>

    <div v-if="state.isMatchingModal" class="matching-modal">
      <div class="modal-wrapper">
        <RequestPostCanvas
          v-if="state.characterColor"
          :characterColor="state.characterColor"
          :isMatchingProfile="true"
        ></RequestPostCanvas>
        <div class="matching-title">비밀친구 매칭이 되었습니다!</div>
        <div>
          <button class="accept-matching" @click="acceptMatching">수락</button>
          <button class="reject-matching" @click="rejectMatching">거절</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref, getCurrentInstance, reactive } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import RequestPostCanvas from "@/components/class/RequestPostCanvas.vue";
import { useRouter } from "vue-router";

export default {
  components: {
    RequestPostCanvas,
  },
  name: "HeaderNav",
  setup() {
    const router = useRouter();
    const app = getCurrentInstance();
    const $soketio = app.appContext.config.globalProperties.$soketio;
    const store = useStore();
    const user = ref(store.state.root.user);
    const state = reactive({
      isMatchingModal: false,
      characterColor: null,
      studentName: "",
      matchingSid: null,
    });
    let isNoticeOpen = ref(false);

    let isProfileOpen = ref(false);

    const notices = ref("");
    const children = ref("");
    const toggleProfileModal = () => {
      isNoticeOpen.value = false;
      isProfileOpen.value = !isProfileOpen.value;
    };

    const toggleNoticeModal = () => {
      isProfileOpen.value = false;
      isNoticeOpen.value = !isNoticeOpen.value;
    };

    const logout = () => {
      store.dispatch("root/inactiveKakaoToken", user.accessToken).then(() => {
        store.commit("root/logoutUser");
        location.href = "/";
      });
    };

    //클래스 오픈 알람
    const getClassOpenAlarm = async () => {
      await axios
        .get(`${process.env.VUE_APP_API_URL}/likes/${user.value.sid}`)
        .then((response) => {
          console.log("좋아요버튼", response.data.requsest);
          notices.value = response.data.requsest;
        });
    };
    console.log(user);
    // 학생일 경우 학생에 대한 알람 socket 알람을 받어.
    if (user.value && user.value.userType === "student") {
      $soketio.on("updateStudentAlarm", () => {
        getClassOpenAlarm();
      });
    }

    onMounted(() => {
      if (user.value && user.value.userType === "student") {
        getClassOpenAlarm();
      }
      if (user.value && user.value.userType === "parent") {
        // getChildren();
        // getChildrenDangerAlarm();
      }
      console.log(user.value);
    });

    const getChildren = async () => {
      await store
        .dispatch("root/getChildren", user.value.email)
        .then((res) => console.log(res));
    };

    // 학생별 알람에 대한 읽기 완료
    const checkAlarm = async (rid) => {
      await axios
        .post(`${process.env.VUE_APP_API_URL}/likes/read`, {
          rid: rid,
          sid: user.value.sid,
        })
        .then((response) => {
          console.log(response);
          $soketio.emit("readStudentAlarm");
        });
    };
    //자식들에 위험단어 알림을 받는다~
    const getChildrenDangerAlarm = async () => {
      store.dispatch("root/getChildrenDangerAlarm").then((response) => {
        console.log(response);
        notices.value = response.data.requsest;
      });
    };

    // 상담 on/off 상태 변경
    const changeTalkableState = async () => {
      console.log("시작한다아아");
      await axios
        .put(
          `${process.env.VUE_APP_API_URL}/volunteer/talkable/${user.value.vid}`,
          { id: user.value.vid },
        )
        .then((response) => {
          console.log(response);
          store.commit("root/setVolunteerTalkingState");
          $soketio.emit("volunteerChangeTalkState");
          console.log(user.value.talkable);
        });
    };

    const rejectMatching = () => {
      state.isMatchingModal = false;
    };
    console.log(user.value);

    // 봉사자일 경우 socket 알람을 받는다.
    if (
      user.value &&
      user.value.userType === "volunteer" &&
      user.value.talkable
    ) {
      $soketio.on("newMessage", (data) => {
        state.isMatchingModal = true;
        store.dispatch("root/getStudentInfo", data).then((res) => {
          state.characterColor = res.data.user.character;
          state.matchingSid = res.data.user.sid;
          state.studentName = res.data.user.nickname;
        });
      });

      // 한명이라도 수락을 누르면 모달창이 다 닫혀야 하니깐~
      // 동시에누를수도있나? 그건모름..
      $soketio.on("closeModalMatching", () => {
        state.isMatchingModal = false;
      });
    }

    const acceptMatching = () => {
      // 한명이 수락을 하면 다른사람들의 모달을 다 지워줘야 함.
      $soketio.emit("matchingComplete");

      //봉사자가 요청을 받아들였을 때
      // 대충 봉사자가 비밀친구 페이지로 가는 코드
      router.push({
        name: "secrettalk",
        params: { sid: state.matchingSid, vid: user.value.vid },
      });
      // 학생에게 봉사자가 매칭을 승인했다고 알려주기.
      $soketio.emit("volunteerAcceptMatching", user.value.vid);
    };

    return {
      user,
      isNoticeOpen,
      isProfileOpen,
      toggleProfileModal,
      getClassOpenAlarm,
      toggleNoticeModal,
      changeTalkableState,
      acceptMatching,
      rejectMatching,
      notices,
      getChildrenDangerAlarm,
      checkAlarm,
      children,
      logout,
      state,
      getChildren,
    };
  },
};
</script>

<style scoped src="@/css/header.scss" lang="scss"></style>
