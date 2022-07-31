<template>
  <div class="background-view">
    <div class="feedback-container">
      <div class="left">
        <div class="lecture-area">
          <img src="@/assets/lecture-thumb.png" />
          <i
            class="fa-solid fa-arrow-right-from-line open"
            @click="doSlide"
          ></i>
        </div>
      </div>
      <div class="right">
        <div class="slider">
          <div class="feedback-container">
            <ul class="feedback-menu">
              <li>강의 상세</li>
              <li>피드백</li>
            </ul>
            <div class="feedback-box"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";
export default {
  setup() {
    let slider;
    onMounted(() => {
      slider = document.querySelector(".slider");
    });
    const doSlide = (e) => {
      //   console.dir(slider);
      if (slider.classList.contains("closed")) {
        slider.classList.remove("closed");
        e.target.classList.add("fa-arrow-right-from-line");
        e.target.classList.remove("fa-arrow-left-to-line");
        e.target.classList.add("open");
      } else {
        slider.classList.add("closed");
        e.target.classList.remove("fa-arrow-right-from-line");
        e.target.classList.add("fa-arrow-left-to-line");
        e.target.classList.remove("open");
      }
    };
    return {
      doSlide,
      slider,
    };
  },
};
</script>

<style lang="scss" scoped src="@/css/feedback.scss" />
