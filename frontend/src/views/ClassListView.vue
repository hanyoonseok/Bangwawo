<template>
  <div class="container">
    <section id="top"></section>
    <section class="opened">
      <div class="title">
        <h2>공개 수업</h2>
        <p>현재 진행중인 공개수업 목록입니다.</p>
      </div>
      <swiper
        :slidesPerView="3"
        :slidesPerGroup="3"
        :spaceBetween="10"
        :loop="true"
        :loopFillGroupWithBlank="true"
        :navigation="true"
        :style="{
          '--swiper-navigation-color': '#C0C0C0',
          '--swiper-pagination-color': '#C0C0C0',
        }"
        :modules="modules"
        class="slide"
      >
        <swiper-slide
          ><a href=""><RectPostCard /></a
        ></swiper-slide>
        <swiper-slide
          ><a href=""><RectPostCard /></a
        ></swiper-slide>
        <swiper-slide
          ><a href=""><RectPostCard /></a
        ></swiper-slide>
        <swiper-slide
          ><a href=""><RectPostCard /></a
        ></swiper-slide>
        <swiper-slide
          ><a href=""><RectPostCard /></a
        ></swiper-slide>
        <swiper-slide
          ><a href=""><RectPostCard /></a
        ></swiper-slide>
      </swiper>
    </section>
    <section class="maked">
      <div class="search-bar">
        <input type="text" placeholder="찾고 싶은 수업을 입력하세요." />
        <button class="searchBtn">
          <i class="fa-solid fa-magnifying-glass"></i>
        </button>
      </div>
      <div class="title">
        <h2>개설된 수업</h2>
        <p>현재 개설된 수업 목록입니다.</p>
      </div>
      <div class="contents">
        <ul>
          <li>
            <a><rect-post-card /></a>
          </li>
          <li>
            <a><rect-post-card /></a>
          </li>
          <li>
            <a><rect-post-card /></a>
          </li>
          <li>
            <a><rect-post-card /></a>
          </li>
          <li>
            <a><rect-post-card /></a>
          </li>
          <li>
            <a><rect-post-card /></a>
          </li>
        </ul>
      </div>
      <div class="swiper-button-prev">dd</div>
      <div class="swiper-button-next">dd</div>
    </section>
    <a class="up" href="#top">
      <i class="fa-solid fa-arrow-up"></i>
    </a>
  </div>
</template>

<script>
import RectPostCard from "@/components/common/RectPostCard.vue";

//vue-awesome-swiper 라이브러리
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from "swiper/vue";
// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
// import required modules
import { Navigation } from "swiper";

import "swiper/css/pagination";
import "swiper/css/navigation";
export default {
  name: "ClassListView",
  components: {
    RectPostCard,
    Swiper,
    SwiperSlide,
  },
  setup() {
    return {
      modules: [Navigation],
    };
  },
};
</script>

<style scoped src="@/css/classList.scss" lang="scss"></style>
