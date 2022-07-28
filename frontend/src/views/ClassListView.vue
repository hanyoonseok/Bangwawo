<template>
  <div class="class-list">
    <HeaderNav />
    <div class="contents">
      <section class="banner" id="banner">
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
          :slidesPerGroup="1"
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
          <swiper-slide v-for="(item, index) in state" :key="index"
            ><router-link to="/class/detail">
              <RectPostCard :state="item" />
            </router-link>
          </swiper-slide>
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
            <div v-for="(item, index) in state" :key="index">
              <router-link to="/class/detail"
                ><RectPostCard :state="item"
              /></router-link>
            </div>
          </ul>
        </div>
      </section>
      <a class="up" @click="top">
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

import { ref } from "vue";
export default {
  name: "ClassListView",
  components: {
    HeaderNav,
    RectPostCard,
    Swiper,
    SwiperSlide,
  },
  setup() {
    // 임시 테스트용 데이터
    const state = ref([
      {
        className: "dd",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
      },
      {
        className: "ss",
        classDate: "ss",
        classStartTime: "ss",
        classEndTIme: "ss",
        classThumbnail: "ss",
        classOpen: "ss",
        classPeople: "ss",
        classContent: "ss",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
      },
      {
        className: "ss",
        classDate: "ss",
        classStartTime: "ss",
        classEndTIme: "ss",
        classThumbnail: "ss",
        classOpen: "ss",
        classPeople: "ss",
        classContent: "ss",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
      },
      {
        className: "ss",
        classDate: "ss",
        classStartTime: "ss",
        classEndTIme: "ss",
        classThumbnail: "ss",
        classOpen: "ss",
        classPeople: "ss",
        classContent: "ss",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
      },
      {
        className: "ss",
        classDate: "ss",
        classStartTime: "ss",
        classEndTIme: "ss",
        classThumbnail: "ss",
        classOpen: "ss",
        classPeople: "ss",
        classContent: "ss",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
      },
      {
        className: "ss",
        classDate: "ss",
        classStartTime: "ss",
        classEndTIme: "ss",
        classThumbnail: "ss",
        classOpen: "ss",
        classPeople: "ss",
        classContent: "ss",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
      },
    ]);
    const top = () => {
      window.scrollTo({ top: 0, behavior: "smooth" });
    };
    return {
      top,
      state,
      modules: [Navigation],
    };
  },
};
</script>

<style scoped src="@/css/classList.scss" lang="scss"></style>
