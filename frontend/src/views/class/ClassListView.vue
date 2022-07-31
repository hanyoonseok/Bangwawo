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
      <OpendClassList :opened="state" />
      <MakedClassList :maked="state" />
      <a class="up" @click="top">
        <i class="fa-solid fa-arrow-up"></i>
      </a>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import OpendClassList from "@/components/class/OpendClassList.vue";
import MakedClassList from "@/components/class/MakedClassList.vue";

import { reactive } from "vue";
export default {
  name: "ClassListView",
  components: {
    HeaderNav,
    OpendClassList,
    MakedClassList,
  },
  setup() {
    // 임시 테스트용 데이터
    const state = reactive([
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
    };
  },
};
</script>

<style scoped src="@/css/classList.scss" lang="scss"></style>
