<template>
  <div class="right-box">
    <div class="lecture-area" v-if="!isEnd">
      <div
        v-for="(lecture, index) in comingClass"
        :key="index"
        class="lecture-box"
      >
        <router-link :to="{ name: 'classdetail' }">
          <div class="lecture-thumb">
            <div class="state-btn">
              <i class="fa-solid fa-circle"></i>&nbsp;예정
            </div>
          </div>
          <div class="lecture-info">
            <div class="lecture-teacher">{{ lecture.vid.nickname }} 강사</div>
            <div class="lecture-title">{{ lecture.title }}</div>
            <div class="lecture-date">{{ lecture.stimeStr }}</div>
            <button
              class="lecture-cancel"
              v-if="!user.userType === 'volunteer'"
            >
              취소
            </button>
          </div></router-link
        >
      </div>
    </div>

    <div class="lecture-area" v-else>
      <div
        v-for="(lecture, index) in endClass"
        :key="index"
        class="lecture-box"
      >
        <router-link :to="{ name: 'classdetail' }">
          <div class="lecture-thumb">
            <div class="end-btn">종료</div>
          </div>
          <div class="lecture-info">
            <div class="lecture-teacher">{{ lecture.vid.nickname }} 강사</div>
            <div class="lecture-title">{{ lecture.title }}</div>
            <div class="lecture-date">{{ lecture.etimeStr }}종료</div>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["isEnd", "user", "comingClass", "endClass"],
  setup() {},
  components: {
    // Carousel,
    // Slide,
  },
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
