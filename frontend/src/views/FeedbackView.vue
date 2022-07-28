<template>
  <div>
    <div class="feedback-background">
      <div class="feedback-page">
        <div class="feedback-area">
          <section>
            <h2 class="title">강의정보</h2>
            <div class="lecture-info">
              <div class="lecture-thumb round-post-card"></div>
              <div class="lecture-desc">
                <div class="lecture-value">
                  <label>강사</label>
                  <div class="label-value">김오리</div>
                </div>
                <div class="lecture-value">
                  <label>시간</label>
                  <div class="label-value">22.07.20 13:00 ~ 15:00</div>
                </div>
                <div class="lecture-value">
                  <label>소개</label>
                  <div class="label-value">
                    대충 수업소개글임 대충 수업소개글임 대충 수업 소개글임 대충
                    수업 소개글!!
                  </div>
                </div>
                <div class="lecture-value">
                  <label>공개</label>
                  <div class="label-value">누구나 들어올 수 있어요~</div>
                </div>
                <div class="lecture-value">
                  <label>인원</label>
                  <div class="label-value">12/45</div>
                </div>
              </div>
            </div>
          </section>
          <section>
            <h2 class="title">피드백</h2>
            <div class="feedback-desc">
              아이가 너무 잘합니다 집중력도 좋고 적극적이에요! 아이가 너무
              잘합니다 집중력도 좋고 적극적이에요! <br />
              아이가 너무 잘합니다 집중력도 좋고 적극적이에요! 아이가 너무
              잘합니다 집중력도 좋고 적극적이에요!
            </div>
          </section>
          <section>
            <h2 class="title">강의 동영상</h2>
            <div class="lecture-video-area">
              <div class="lecture-video"></div>
              <div class="lecture-video"></div>
              <div class="lecture-video"></div>
            </div>
          </section>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "v-calendar/dist/style.css";

export default {
  name: "CalendarArea",
};
</script>

<style scoped src="@/css/feedback.scss" lang="scss"></style>
