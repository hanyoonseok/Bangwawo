<template>
  <div class="lecture-area" v-if="!isEnd">
    <div
      v-for="(lecture, index) in scheduledClass"
      :key="index"
      class="lecture-box"
    >
      <div class="lecture-thumb">
        <div class="state-btn">
          <i class="fa-solid fa-circle"></i>&nbsp;{{ lecture.classStatus }}
        </div>
      </div>
      <div class="lecture-info">
        <div class="teacher-photo">
          <img src="@/assets/profile.png" />
        </div>
        <div class="lecture-teacher">{{ lecture.classTeacher }} 강사</div>
        <div class="lecture-title">{{ lecture.classTitle }}</div>
        <div class="lecture-date">{{ lecture.classStartTime }}</div>
        <button class="lecture-cancel">취소</button>
      </div>
    </div>
  </div>
  <div class="lecture-area" v-else>
    <div v-for="(lecture, index) in endClass" :key="index" class="lecture-box">
      <div class="lecture-thumb">
        <div class="end-btn">
          <i class="fa-solid fa-circle"></i>&nbsp;{{ lecture.classStatus }}
        </div>
      </div>
      <div class="lecture-info">
        <div class="teacher-photo">
          <img src="@/assets/profile.png" />
        </div>
        <div class="lecture-teacher">{{ lecture.classTeacher }} 강사</div>
        <div class="lecture-title">{{ lecture.classTitle }}</div>
        <div class="lecture-date">{{ lecture.classEndTime }} 종료</div>
      </div>
    </div>
  </div>
</template>

<script>
// import moment from "moment";
export default {
  setup() {
    // console.log(moment().format("YYYY-MM-DD HH:mm:ss"));
  },

  props: ["isEnd", "user", "scheduledClass", "endClass"],
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
