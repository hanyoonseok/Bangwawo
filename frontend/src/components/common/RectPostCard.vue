<template>
  <div class="post-card">
    <div
      class="img-box"
      v-if="state.thumbnail.length === 0 && state.preview === null"
    >
      <img src="@/assets/thumbnail.png" alt="썸네일없을때이미지" />
    </div>
    <div
      class="img-box"
      v-if="state.thumbnail.length === 0 && state.preview !== null"
    >
      <img :src="state.preview" alt="프리뷰이미지" />
    </div>
    <div
      class="img-box"
      v-if="state.thumbnail.length !== 0 && state.preview === null"
    >
      <img
        :src="'http://localhost:8081/api/' + state.thumbnail"
        alt="썸네일이미지"
      />
    </div>
    <div class="card-info card-view">
      <h4>{{ state.title }}</h4>
      <div class="division">
        <label class="detail">{{ state.introduce }}</label>
        <label class="participants"
          ><i class="fa-solid fa-user"></i>&nbsp;{{ state.enrolcnt }}</label
        >
      </div>
    </div>
    <div class="post-card-hover">
      <p class="people">정원 : {{ state.maxcnt }}</p>
      <p class="teacher">강사 : {{ state.vid.nickname }}</p>
      <p class="time">
        시간 : {{ state.dateStr }} {{ state.stimeStr }} ~
        {{ state.etimeStr }}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "RectPostCard",
  props: ["state"],
};
</script>

<style scoped src="@/css/global.scss" lang="scss"></style>
