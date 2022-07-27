<template>
  <div class="container">
    <section class="left">
      <ul class="listUl" v-if="user.status === 0">
        <div><img src="@/assets/header.png" /></div>
      </ul>
      <ul class="listUl" v-if="user.status === 1">
        <div><img src="@/assets/header.png" /></div>
        <li class="list">수업목록</li>
        <li class="list">수업요청</li>
        <li class="list">비밀친구</li>
      </ul>
      <ul class="listUl" v-if="user.status === 2">
        <div><img src="@/assets/header.png" /></div>
        <li class="list">수업목록</li>
        <li class="list">수업등록</li>
        <li class="list">수업요청</li>
      </ul>
      <ul class="listUl" v-if="user.status === 3">
        <div><img src="@/assets/header.png" /></div>
        <li class="list">수업목록</li>
        <li class="list">수업요청</li>
      </ul>
    </section>

    <section class="right">
      <button class="login" v-if="user.status === 0">로그인</button>
      <div class="img-wrapper">
        <img src="@/assets/profile.png" v-if="user.status >= 1" />
        <ul>
          <li>마이페이지</li>
          <li>로그아웃</li>
        </ul>
      </div>
      <div class="bell-wrapper" @click="isModalOpen = !isModalOpen">
        <i
          class="fa-solid fa-bell"
          v-if="user.status === 1 || user.status === 3"
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
      status: 1,
      messages: ["asd"],
    };

    const isModalOpen = ref(false);
    return {
      user,
      isModalOpen,
    };
  },
};
</script>

<style scoped src="@/css/header.scss" lang="scss"></style>
