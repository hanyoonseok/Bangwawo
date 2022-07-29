<template>
  <section class="modal">
    <div class="empty" @click="close"></div>
    <div class="container">
      <div class="stretch">
        <article class="btn-wrapper">
          <i class="fa-solid fa-xmark close" @click="close"></i>
        </article>

        <article class="title-wrapper">
          <h2>피드백</h2>
        </article>
        <article class="center-wrapper">
          <div class="modal-profile">
            <img :src="info.img" />
            <div class="person-info">
              <label>이름</label>
              {{ info.name }}
            </div>
            <div class="person-info">
              <label>별명</label>{{ info.nickname }}
            </div>
          </div>
          <textarea></textarea>
        </article>
        <article class="btn-wrapper">
          <button class="submit-btn">작성 완료</button>
        </article>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "FeedbackModal",
  props: ["info", "close"],
};
</script>

<style scoped src="@/css/feedback.scss" lang="scss"></style>
