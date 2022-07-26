<template>
  <div>
    <HeaderNav />
    <div class="contents">
      <section class="banner">
        <img class="banner-img" src="@/assets/banner-illust.png" />
        <div class="banner-info">
          <h1 class="banner-title" data-aos="fade-up" data-aos-duration="1000">
            방과후에는<br /><span class="underline"> 방과워</span>와 함께+
          </h1>
          <p
            class="banner-desc"
            data-aos="fade-up"
            data-aos-duration="1000"
            data-aos-delay="100"
          >
            방과워 서비스는<br />
            봉사자에게는 재능 나눔의 기회를 제공하고,<br />
            내 자녀에게는 방과후 활동을 제공합니다.
          </p>
        </div>
      </section>
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
      </section>
      <a class="up" href="#banner">
        <i class="fa-solid fa-arrow-up"></i>
      </a>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
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
    HeaderNav,
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
