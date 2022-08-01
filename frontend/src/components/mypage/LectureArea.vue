<template>
  <div class="lecture-area">
    <div v-for="(lecture, index) in classes" :key="index" class="lecture-box">
      <div class="lecture-thumb"></div>
      <div class="lecture-info">
        <div class="teacher-photo">
          <img src="@/assets/profile.png" />
        </div>
        <div class="lecture-teacher">{{ lecture.classTeacher }}강사</div>
        <div class="lecture-title">{{ lecture.classTitle }}</div>
        <div class="lecture-date">{{ lecture.classStartTime }}</div>
        <button class="lecture-cancel">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  setup() {},
  props: ["isEnd", "user", "classes"],
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
