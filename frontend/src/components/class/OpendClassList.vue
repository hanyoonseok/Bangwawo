<template>
  <div class="opened">
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
      <swiper-slide v-for="(item, index) in opened" :key="index"
        ><router-link :to="{ name: 'classdetail', params: { cid: item.cid } }">
          <RectPostCard :state="item" />
        </router-link>
      </swiper-slide>
    </swiper>
  </div>
</template>

<script>
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

import RectPostCard from "@/components/common/RectPostCard.vue";

export default {
  name: "OpendClassList",
  components: {
    Swiper,
    SwiperSlide,
    RectPostCard,
  },
  props: ["opened"],
  setup() {
    return {
      modules: [Navigation],
    };
  },
};
</script>

<style scoped src="@/css/classList.scss" lang="scss"></style>
