<template>
  <div class="right-box">
    <div class="lecture-area" v-if="!isEndTab && comingClass">
      <div
        v-for="(lecture, index) in comingClass"
        :key="index"
        class="lecture-box"
      >
        <router-link
          :to="{ name: 'classdetail', params: { cid: lecture.cid } }"
        >
          <div class="lecture-thumb">
            <div class="state-btn">
              <i class="fa-solid fa-circle"></i> &nbsp;
              {{ statusText(lecture.state) }}
            </div>
          </div>
          <div class="lecture-info">
            <div class="lecture-teacher">
              {{ lecture.volunteer.nickname }} 강사
            </div>
            <div class="lecture-title">{{ lecture.title }}</div>
            <div class="lecture-date">
              {{ lecture.stime[1] }}/{{ lecture.stime[2] }}
              {{ lecture.stime[3] }}:{{ lecture.stime[4] }}
            </div>
            <!-- <button class="lecture-cancel" v-if="user.userType !== 'volunteer'">
              취소
            </button> -->
          </div>
        </router-link>
      </div>
    </div>

    <div class="lecture-area" v-if="isEndTab && endClass">
      <div
        v-for="(lecture, index) in endClass"
        :key="index"
        class="lecture-box"
      >
        <router-link
          :to="{ name: 'classdetail', params: { cid: lecture.cid } }"
        >
          <div class="lecture-thumb">
            <div class="end-btn">
              <i class="fa-solid fa-circle"></i> &nbsp;
              {{ statusText(lecture.state) }}
            </div>
          </div>
          <div class="lecture-info">
            <div class="lecture-teacher">
              {{ lecture.volunteer.nickname }} 강사
            </div>
            <div class="lecture-title">{{ lecture.title }}</div>
            <div class="lecture-date">
              {{ lecture.stime[1] }}/{{ lecture.stime[2] }}
              {{ lecture.stime[3] }}:{{ lecture.stime[4] }} 종료
            </div>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["isEndTab", "user", "comingClass", "endClass"],
  setup(props) {
    const statusText = (state) => {
      if (props.user.userType === "volunteer") {
        if (state === 0) return "예정";
        else if (state === 1) return "수업중";
        else return "종료";
      } else {
        if (state === 0) return "예정";
        else if (state === 1) return "수업중";
        else return "종료";
      }
    };

    return {
      statusText,
    };
  },
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
