<template>
  <div class="background-view">
    <HeaderNav />
    <h2 class="feedback-title">피드백</h2>
    <div class="feedback-container">
      <div class="left">
        <div class="lecture-area">
          <!-- <img src="@/assets/lecture-thumb.png" class="slide-open" /> -->
          <video controls class="slide-open" :src="videoURL">
            <!-- <source :src="videoURL" type="video/mp4" /> -->
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
              <li
                :class="{ 'menu-btn': true, active: state.step === 0 }"
                @click="toggleFeedback(0)"
              >
                강의 상세
              </li>
              <li
                :class="{ 'menu-btn': true, active: state.step === 1 }"
                @click="toggleFeedback(1)"
              >
                피드백
              </li>
              <li
                :class="{ 'menu-btn': true, active: state.step === 2 }"
                @click="toggleFeedback(2)"
              >
                감정 차트
              </li>
            </ul>
            <div class="feedback-info" v-if="feedbackInfo">
              <div class="emotion-feedback-box" v-if="state.step === 2">
                <DoughnutChart
                  v-if="state.chartNum === 0"
                  :emotion="feedbackInfo.emotion"
                />
                <BarChart
                  v-if="state.chartNum === 1"
                  :emotion="feedbackInfo.emotion"
                />
                <LineChart
                  v-if="state.chartNum === 2"
                  :emotion="feedbackInfo.emotion"
                />
              </div>
              <div class="feedback-box" v-if="state.step === 1">
                {{ feedbackInfo.feedback }}
              </div>
              <div class="feedback-box" v-if="state.step === 0">
                <div class="lecture-desc">
                  <div class="lecture-value">
                    <label>강사</label>
                    <div class="label-value">
                      {{ feedbackInfo.cid.volunteer.nickname }}
                    </div>
                  </div>
                  <div class="lecture-value">
                    <label>시간</label>
                    <div class="label-value">
                      {{ toTimeStr(feedbackInfo.cid.stime) }} ~<br />
                      {{ toTimeStr(feedbackInfo.cid.etime) }}
                    </div>
                  </div>
                  <div class="lecture-value">
                    <label>소개</label>
                    <div class="label-value">
                      {{ feedbackInfo.cid.volunteer.introduce }}
                    </div>
                  </div>
                  <div class="lecture-value">
                    <label>공개</label>
                    <div class="label-value">누구나 들어올 수 있어요~</div>
                  </div>
                  <div class="lecture-value">
                    <label>인원</label>
                    <div class="label-value">
                      {{ studentNum }}/{{ feedbackInfo.cid.maxcnt }}
                    </div>
                  </div>
                </div>
              </div>
              <button
                @click="state.chartNum = 0"
                class="chart-btn red"
                style="left: 0"
                v-if="state.step === 2"
              >
                도넛
              </button>
              <button
                @click="state.chartNum = 1"
                class="chart-btn blue"
                style="left: 90px"
                v-if="state.step === 2"
              >
                바
              </button>
              <button
                @click="state.chartNum = 2"
                class="chart-btn green"
                v-if="state.step === 2"
                style="left: 180px"
              >
                라인
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import { reactive, onMounted, ref } from "vue";
import BarChart from "@/components/mypage/Chart/BarChart";
import DoughnutChart from "@/components/mypage/Chart/DoughnutChart";
import LineChart from "@/components/mypage/Chart/LineChart";
import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default {
  setup() {
    let slider;
    let menuBtn;
    let slide;
    const route = useRoute();
    const store = useStore();
    const cid = route.params.cid;
    const sid = route.params.sid;
    let feedbackInfo = ref(null);
    let studentNum = ref(0);
    let videoURL = ref("");

    const state = reactive({
      step: 0,
      chartNum: 0,
    });

    const getStudentFeedback = () => {
      store.dispatch("root/getStudentFeedback", { sid, cid }).then((res) => {
        feedbackInfo.value = res.data;
        console.log(res.data);
        videoURL.value = `${process.env.VUE_APP_OV_DOMAIN}/openvidu/recordings/${feedbackInfo.value.recording}.webm`;
      });
    };
    getStudentFeedback();

    onMounted(() => {
      slider = document.querySelector(".slider");
      menuBtn = document.querySelectorAll(".menu-btn");
      slide = document.querySelector("video");
    });

    const doSlide = (e) => {
      //   console.dir(slider);
      slider = document.querySelector(".slider");
      if (slider.classList.contains("closed")) {
        slider.classList.remove("closed");
        document.querySelector(".feedback-container .right").style.display =
          "block";
        slide.classList.add("slide-open");
        e.target.classList.add("fa-arrow-right-from-line");
        e.target.classList.remove("fa-arrow-left-to-line");
        e.target.classList.add("open");
      } else {
        // slide.classList.remove("slide-open");
        // slide.classList.add("slide-closed");
        slider.classList.add("closed");
        document.querySelector(".feedback-container .right").style.display =
          "none";
        e.target.classList.remove("fa-arrow-right-from-line");
        e.target.classList.add("fa-arrow-left-to-line");
        e.target.classList.remove("open");
      }
    };
    const toggleFeedback = (step) => {
      state.step = step;
    };

    const toTimeStr = (timeArr) => {
      return `${timeArr[0]}.${timeArr[1]}.${timeArr[2]} ${timeArr[3]}:${
        timeArr[4] < 10 ? "0" : ""
      }${timeArr[4]}`;
    };

    const getClassStudentNum = () => {
      store.dispatch("root/getClassStudents", cid).then((res) => {
        studentNum.value = res.data.length;
      });
    };
    getClassStudentNum();

    return {
      state,
      doSlide,
      slider,
      menuBtn,
      slide,
      toggleFeedback,
      feedbackInfo,
      toTimeStr,
      studentNum,
      videoURL,
    };
  },
  components: {
    HeaderNav,
    BarChart,
    DoughnutChart,
    LineChart,
  },
};
</script>

<style lang="scss" scoped src="@/css/feedback.scss" />
