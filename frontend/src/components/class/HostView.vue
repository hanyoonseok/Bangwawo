<template>
  <section class="main-section">
    <section class="top-section">
      <article class="left"></article>
      <article class="right">h</article>
    </section>
    <section class="bot-section">
      <article class="bot-left">
        <button class="option-btn red">
          <i class="fa-solid fa-microphone-slash"></i>
          &nbsp;음소거 해제
        </button>
        <button class="option-btn">
          <i class="fa-solid fa-video"></i>
          &nbsp;비디오 시작
        </button>
        <button class="option-btn">
          <i class="fa-solid fa-arrow-up-from-square"></i>
          &nbsp;화면 공유
        </button>
        <button class="option-btn">&nbsp;OX 퀴즈</button>
        <i class="fa-solid fa-xmark xmark"></i>
      </article>
      <article class="bot-right">
        <button class="option-btn">
          <i class="fa-solid fa-user"></i>
          &nbsp;참가자
        </button>
        <button class="option-btn">
          <i class="fa-solid fa-comments"></i>
          &nbsp;채팅
        </button>
      </article>
    </section>
  </section>
</template>

<script>
export default {
  name: "HostView",
  setup() {},
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
