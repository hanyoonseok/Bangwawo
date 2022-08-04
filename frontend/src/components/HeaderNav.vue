<template>
  <div class="container">
    <section class="left">
      <div class="listdiv" v-if="user.status === 0">
        <div><img src="@/assets/header.png" /></div>
      </div>
      <div class="listdiv" v-if="user.status === 1">
        <div><img src="@/assets/header.png" /></div>
        <router-link to="/class/list" class="list">수업목록</router-link>
        <router-link to="/class/requestlist" class="list">수업요청</router-link>
        <router-link to="/secret" class="list">비밀친구</router-link>
      </div>
      <div class="listdiv" v-if="user.status === 2">
        <div><img src="@/assets/header.png" /></div>
        <router-link to="/class/list" class="list">수업목록</router-link>
        <router-link to="/class/register" class="list">수업등록</router-link>
        <router-link to="/class/requestlist" class="list">수업요청</router-link>
      </div>
      <div class="listdiv" v-if="user.status === 3">
        <div><img src="@/assets/header.png" /></div>
        <router-link to="/class/list" class="list">수업목록</router-link>
        <router-link to="/class/requestlist" class="list">수업요청</router-link>
      </div>
    </section>

    <section class="right">
      <button class="login" v-if="user.status === 0">로그인</button>
      <div class="img-wrapper">
        <img
          src="@/assets/profile.png"
          v-if="user.status >= 1"
          @click="toggleProfile"
        />
        <ul v-if="isProfileOpen">
          <router-link to="/mypage">마이페이지</router-link>
          <router-link to="/logout">로그아웃</router-link>
        </ul>
      </div>
      <div class="bell-wrapper">
        <i
          class="fa-solid fa-bell"
          v-if="user.status === 1 || user.status === 3"
          @click="toggleModal"
          ><div class="count" v-if="user.messages.length > 0">
            {{ user.messages.length }}
          </div></i
        >
        <article class="modal" v-if="isModalOpen">
          <div class="title-wrapper">
            <i class="fa-solid fa-bell"></i>
            알림창
          </div>
          <div class="row-wrapper">
            <div class="row">
              <img src="@/assets/profile.png" />
              <label
                ><p>자녀의 상담중 위험 용어가 발생했습니다.</p>
                <p class="date">2022-07-25</p></label
              >
              <i class="fa-solid fa-xmark close"></i>
            </div>
            <div class="row">
              <img src="@/assets/profile.png" />
              <label
                ><p>자녀의 상담중 위험 용어가 발생했습니다.</p>
                <p class="date">2022-07-25</p></label
              >
              <i class="fa-solid fa-xmark close"></i>
            </div>
            <div class="row">
              <img src="@/assets/profile.png" />
              <label
                ><p>자녀의 상담중 위험 용어가 발생했습니다.</p>
                <p class="date">2022-07-25</p></label
              >
              <i class="fa-solid fa-xmark close"></i>
            </div>
            <div class="row">
              <img src="@/assets/profile.png" />
              <label
                ><p>자녀의 상담중 위험 용어가 발생했습니다.</p>
                <p class="date">2022-07-25</p></label
              >
              <i class="fa-solid fa-xmark close"></i>
            </div>
            <div class="row">
              <img src="@/assets/profile.png" />
              <label
                ><p>자녀의 상담중 위험 용어가 발생했습니다.</p>
                <p class="date">2022-07-25</p></label
              >
              <i class="fa-solid fa-xmark close"></i>
            </div>
          </div>
        </article>
      </div>

      <button class="consult" v-if="user.status === 2">
        <i class="fa-solid fa-circle"></i>&nbsp;상담 ON
      </button>
    </section>
  </div>
</template>

<script>
import { ref } from "vue";
export default {
  name: "HeaderNav",
  setup() {
    const user = {
      status: 1, //0비로그인 1로그인
      messages: ["asd"],
    };

    let isModalOpen = ref(false);

    let isProfileOpen = ref(false);

    const toggleProfile = () => {
      isModalOpen.value = false;
      isProfileOpen.value = !isProfileOpen.value;
    };

    const toggleModal = () => {
      isProfileOpen.value = false;
      isModalOpen.value = !isModalOpen.value;
    };
    return {
      user,
      isModalOpen,
      isProfileOpen,
      toggleProfile,
      toggleModal,
    };
  },
};
</script>

<style scoped src="@/css/header.scss" lang="scss"></style>
