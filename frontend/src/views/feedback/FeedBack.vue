<template>
  <div class="background-view">
    <HeaderNav />
    <h2 class="feedback-title">피드백</h2>
    <div class="feedback-container">
      <img src="@/assets/threeDuck.png" class="duck-img" />
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
                :class="{ 'menu-btn': true, active: !state.isFeedback }"
                @click="toggleFeedback(false)"
              >
                강의 상세
              </li>
              <li
                :class="{ 'menu-btn': true, active: state.isFeedback }"
                @click="toggleFeedback(true)"
              >
                피드백
              </li>
            </ul>
            <div class="feedback-info" v-if="feedbackInfo">
              <div class="feedback-box" v-if="state.isFeedback">
                {{ feedbackInfo.feedback }}
              </div>
              <div class="feedback-box" v-else>
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
import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default {
  setup() {
    let slider;
    let menuBtn;
    let slideOpen;
    const route = useRoute();
    const store = useStore();
    const cid = route.params.cid;
    const sid = route.params.sid;
    let feedbackInfo = ref(null);
    let studentNum = ref(0);
    let videoURL = ref("");

    const getStudentFeedback = () => {
      store.dispatch("root/getStudentFeedback", { sid, cid }).then((res) => {
        feedbackInfo.value = res.data;
        console.log(res.data);
      });
    };
    getStudentFeedback();

    onMounted(() => {
      slider = document.querySelector(".slider");
      menuBtn = document.querySelectorAll(".menu-btn");
      slideOpen = document.querySelector("video");
    });
    const state = reactive({
      isFeedback: false,
    });
    const doSlide = (e) => {
      //   console.dir(slider);
      slider = document.querySelector(".slider");
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
    const toggleFeedback = (boolean) => {
      state.isFeedback = boolean;
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

    const getRecordVideo = () => {
      store.dispatch("root/getRecordVideo", `${55}Class${55}`).then((res) => {
        console.log(res.data);
        videoURL.value = res.data.url;
      });
    };
    getRecordVideo();

    return {
      state,
      doSlide,
      slider,
      menuBtn,
      slideOpen,
      toggleFeedback,
      feedbackInfo,
      toTimeStr,
      studentNum,
      videoURL,
    };
  },
  components: {
    HeaderNav,
  },
};
</script>

<style lang="scss" scoped src="@/css/feedback.scss" />
