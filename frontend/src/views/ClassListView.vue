<template>
  <div class="container">
    <section></section>
    <section class="opened">
      <div class="title">
        <h2>공개 수업</h2>
        <p>현재 진행중인 공개수업 목록입니다.</p>
      </div>
      <div class="contents slide">
        <ul class="slides">
          <li><rect-post-card /></li>
          <li><rect-post-card class="active" /></li>
          <li><rect-post-card /></li>
          <li><rect-post-card /></li>
          <li><rect-post-card /></li>
          <li><rect-post-card /></li>
        </ul>
      </div>
      <div class="controller">
        <span class="prev" @click="prev()"
          ><i class="fa-solid fa-chevron-left"></i
        ></span>
        <span class="next" @click="next()"
          ><i class="fa-solid fa-chevron-right"></i
        ></span>
      </div>
    </section>
    <section class="maked"></section>
  </div>
</template>

<script>
import RectPostCard from "@/components/common/RectPostCard.vue";
export default {
  name: "ClassListView",
  components: {
    RectPostCard,
  },
  data() {
    return { currentIdx: 0 };
  },
  methods: {
    next() {
      const slides = document.querySelector(".slides"); //전체 슬라이드 컨테이너
      const slideItem = document.querySelectorAll(".slides li"); //모든 슬라이드들
      console.log(slideItem.length);
      const slideCount = slideItem.length; // 슬라이드 개수
      console.log(slideCount);
      //   const next = document.querySelector(".next"); //다음 버튼
      //   const slideWidth = 350; //한개의 슬라이드 넓이
      //   const slideMargin = 32; //슬라이드간의 margin 값

      //전체 슬라이드 컨테이너 넓이 설정
      //   slides.style.width = (slideWidth + slideMargin) * slideCount + "px";

      function moveSlide(num) {
        slides.style.left = -num * 382 + "px";
        this.currentIdx = num;
      }

      //   next.addEventListener("click", function () {
      if (this.currentIdx !== slideCount - 1) {
        moveSlide(this.currentIdx + 1);
      }
      //   });
    },
    prev() {
      const slides = document.querySelector(".slides"); //전체 슬라이드 컨테이너
      const slideItem = document.querySelectorAll(".slides li"); //모든 슬라이드들
      console.log(slideItem.length);
      const slideCount = slideItem.length; // 슬라이드 개수
      console.log(slideCount);

      //   const prev = document.querySelector(".prev"); //이전 버튼
      //   const slideWidth = 350; //한개의 슬라이드 넓이
      //   const slideMargin = 32; //슬라이드간의 margin 값

      //전체 슬라이드 컨테이너 넓이 설정
      //   slides.style.width = (slideWidth + slideMargin) * slideCount + "px";

      function moveSlide(num) {
        slides.style.left = -num * 382 + "px";
        this.currentIdx = num;
      }
      console.log("??", this.currentIdx);
      if (this.currentIdx !== 0) moveSlide(this.currentIdx - 1);
    },
  },
};
</script>

<style scoped src="@/css/classList.scss" lang="scss"></style>
