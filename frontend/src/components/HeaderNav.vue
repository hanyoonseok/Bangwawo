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
      <div class="bell-wrapper" v-if="user">
        <i
          class="fa-solid fa-bell"
          v-if="user.userType === 'student' || user.userType === 'parent'"
          @click="toggleNoticeModal"
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
            <div class="row">
              <img src="@/assets/profile.png" />
              <label
                ><p>수업이 개설되었습니다.</p>
                <p class="date">2022-07-25</p></label
              >
              <i class="fa-solid fa-xmark close"></i>
            </div>
          </div>
        </article>
      </div>

      <button class="consult on" v-if="user && user.userType === 'volunteer'">
        <i class="fa-solid fa-circle"></i>&nbsp;상담 ON
      </button>
    </section>
  </div>
</template>

<script>
import { ref } from "vue";
import { useStore } from "vuex";
import axios from "axios";

export default {
  name: "HeaderNav",
  setup() {
    const store = useStore();
    const user = ref(store.state.root.user);

    let isNoticeOpen = ref(false);

    let isProfileOpen = ref(false);

    // const notices = ref([
    //   {
    //     id: "1",
    //     content: "자녀의 상담중 위험 단어를 감지했습니다.",
    //     date: "07-22",
    //   },
    //   {
    //     id: "2",
    //     content: "자녀의 상담중 위험 단어를 감지했습니다.",
    //     date: "07-22",
    //   },
    //   {
    //     id: "3",
    //     content: "자녀의 상담중 위험 단어를 감지했습니다.",
    //     date: "07-22",
    //   },
    //   {
    //     id: "4",
    //     content: "자녀의 상담중 위험 단어를 감지했습니다.",
    //     date: "07-22",
    //   },
    // ]);
    const notices = ref("");
    const toggleProfileModal = () => {
      isNoticeOpen.value = false;
      isProfileOpen.value = !isProfileOpen.value;
    };

    const toggleNoticeModal = () => {
      isProfileOpen.value = false;
      isNoticeOpen.value = !isNoticeOpen.value;
    };

    const logout = () => {
      store.commit("root/logoutUser");
      location.href = "/";
    };

    //클래스 오픈 알람
    const getClassOpenAlarm = () => {
      axios
        .get(`${process.env.VUE_APP_API_URL}/likes/${user.sid}`)
        .then((response) => {
          console.log(response);
        });
    };

    if (user.userType === "student") {
      getClassOpenAlarm();
    }

    return {
      user,
      isNoticeOpen,
      isProfileOpen,
      toggleProfileModal,
      getClassOpenAlarm,
      toggleNoticeModal,
      notices,
      logout,
    };
  },
};
</script>

<style scoped src="@/css/header.scss" lang="scss"></style>
