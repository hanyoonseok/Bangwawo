<template>
  <div class="post-card">
    <div class="img-box" v-if="state.classImgFile.length === 0">
      이미지를 추가해주세요.
    </div>
    <div class="img-box" v-else>
      <img :src="state.classImgFile" alt="썸네일이미지" />
    </div>
    <div class="card-info card-view">
      <h4>{{ state.className }}</h4>
      <div class="division">
        <label class="detail">{{ state.classContent }}</label>
        <label class="participants"
          ><i class="fa-solid fa-user"></i>&nbsp;{{ state.classPeople }}</label
        >
      </div>
    </div>
    <div class="post-card-hover">
      <p class="people">정원 : {{ state.classPeople }}</p>
      <p class="teacher">강사 : {{ state.classTeacher }}</p>
      <p class="time">
        시간 : {{ state.classDate }} &nbsp; {{ state.classStartTime }} ~
        {{ state.classEndTIme }}
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
