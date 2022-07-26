<template>
  <section class="main-section">
    <article class="title-wrapper">
      <h2 class="title">비밀친구 대화중</h2>
    </article>
    <article class="user-wrapper">
      <div class="user-card"></div>
      <div class="user-card"></div>
    </article>
    <article class="btn-wrapper">
      <button class="option-btn">
        <i class="fa-solid fa-microphone-slash"></i>
        &nbsp;음소거 해제
      </button>
      <button class="option-btn">
        <i class="fa-solid fa-xmark"></i> &nbsp;대화 종료
      </button>
    </article>
  </section>
</template>

<script>
export default {
  name: "SecretTalkView",
};
</script>

<style scoped src="@/css/secret-friend.scss" lang="scss"></style>
