<template>
  <div class="class-list">
    <HeaderNav />
    <div class="contents" @scroll="infiniteScroll">
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
      <OpendClassList :opened="openClass" />
      <MakedClassList :maked="filteredMakedClass" />
      <ForObserve @triggerIntersected="loadMakedClasses" />
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
import ForObserve from "@/components/class/ForObserve.vue";

import { ref } from "vue";
export default {
  name: "ClassListView",
  components: {
    HeaderNav,
    OpendClassList,
    MakedClassList,
    ForObserve,
  },
  setup() {
    // 임시 테스트용 데이터
    const allClasses = ref([
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
        isOpenClass: true,
      },
      {
        className: "dd1",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: true,
      },
      {
        className: "dd2",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: true,
      },
      {
        className: "dd3",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: true,
      },
      {
        className: "dd4",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: true,
      },
      {
        className: "dd5",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: true,
      },
      {
        className: "dd6",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd7",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd8",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd9",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd10",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd11",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd12",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd13",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd14",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd15",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd16",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd17",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd18",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd19",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd20",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
      {
        className: "dd21",
        classDate: "dd",
        classStartTime: "dd",
        classEndTIme: "dd",
        classThumbnail: "dd",
        classOpen: "dd",
        classPeople: "dd",
        classContent: "dd",
        classImgFile:
          "https://user-images.githubusercontent.com/33210124/181405552-fbd61f43-08e2-419c-a03e-a7bcf4983218.png",
        isOpenClass: false,
      },
    ]);
    const openClass = ref(allClasses.value.filter((e) => e.isOpenClass));
    const makedClass = ref(allClasses.value.filter((e) => !e.isOpenClass));
    const filteredMakedClass = ref([]);
    const dataIdx = ref(0);
    const dataLen = 15;

    const top = () => {
      window.scrollTo({ top: 0, behavior: "smooth" });
    };

    const loadMakedClasses = () => {
      if (dataIdx.value > makedClass.value.length) return;

      const prevArr = [...filteredMakedClass.value];
      for (
        let i = dataIdx.value;
        i < Math.min(makedClass.value.length, dataIdx.value + dataLen);
        i++
      ) {
        prevArr.push(makedClass.value[i]);
      }
      dataIdx.value += dataLen;
      filteredMakedClass.value = prevArr;
      console.log(filteredMakedClass.value);
    };

    loadMakedClasses();

    return {
      top,
      openClass,
      filteredMakedClass,
      loadMakedClasses,
    };
  },
};
</script>

<style scoped src="@/css/classList.scss" lang="scss"></style>
