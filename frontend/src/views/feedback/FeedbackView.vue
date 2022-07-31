<template>
  <div class="background-view">
    <HeaderNav />
    <h2 class="feedback-title">피드백</h2>
    <div class="feedback-container">
      <img src="@/assets/threeDuck.png" class="duck-img" />
      <div class="left">
        <div class="lecture-area">
          <!-- <img src="@/assets/lecture-thumb.png" class="slide-open" /> -->
          <video controls class="slide-open">
            <source src="@/assets/video.mp4" type="video/mp4" />
            Your browser does not support the video tag.
          </video>
          <i
            class="fa-solid fa-arrow-right-from-line open"
            @click="doSlide"
          ></i>
        </div>
      </div>
      <div class="right">
        <div class="slider">
          <div class="feedback-area">
            <ul class="feedback-menu">
              <li class="menu-btn active" @click="doActive">강의 상세</li>
              <li class="menu-btn" @click="doActive">피드백</li>
            </ul>
            <div class="feedback-info">
              <div class="feedback-box" v-if="state.isFeedback">
                아이가 너무 잘합니다 집중력도 좋고 적fff극적이에요! 아이가 너무
                잘합니다 집중력도 좋고 적극적이에요! 아이가 너무 잘합니다
                집중력도 좋고 적극적이에요! 아이가 너무 잘합니다 집중력도 좋고
                적극적이에요!
              </div>
              <div class="feedback-box" v-else>
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
                      대충 수업소개글임 대충 수업소개글임 대충 수업 소개글임
                      대충 수업 소개글!!
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
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import { reactive, onMounted } from "vue";
export default {
  setup() {
    let slider;
    let menuBtn;
    let slideOpen;
    onMounted(() => {
      slider = document.querySelector(".slider");
      menuBtn = document.querySelectorAll(".menu-btn");
      slideOpen = document.querySelector(".slide-open");
    });
    const state = reactive({
      isFeedback: false,
    });
    const doSlide = (e) => {
      //   console.dir(slider);
      if (slider.classList.contains("closed")) {
        slider.classList.remove("closed");
        console.log(slideOpen);
        slideOpen.classList.add("slide-open");
        e.target.classList.add("fa-arrow-right-from-line");
        e.target.classList.remove("fa-arrow-left-to-line");
        e.target.classList.add("open");
      } else {
        slideOpen.classList.remove("slide-open");
        slider.classList.add("closed");
        e.target.classList.remove("fa-arrow-right-from-line");
        e.target.classList.add("fa-arrow-left-to-line");
        e.target.classList.remove("open");
      }
    };
    const doActive = (e) => {
      if (!e.target.classList.contains("active")) {
        e.target.classList.add("active");
      }
      if (e.target.innerText === "강의 상세") {
        if (state.isFeedback) {
          state.isFeedback = !state.isFeedback;
        }
        menuBtn[1].classList.remove("active");
      } else {
        if (!state.isFeedback) {
          state.isFeedback = !state.isFeedback;
        }
        menuBtn[0].classList.remove("active");
      }
    };
    return {
      state,
      doSlide,
      slider,
      menuBtn,
      slideOpen,
      doActive,
    };
  },
  components: {
    HeaderNav,
  },
};
</script>

<style lang="scss" scoped src="@/css/feedback.scss" />
