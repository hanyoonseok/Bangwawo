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
import axios from "axios";

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
    let allClasses = ref([]);
    let openClass = ref([]);
    let makedClass = ref([]);
    const getClass = async () => {
      axios
        .get(`${process.env.VUE_APP_API_URL}/class`)
        .then((response) => {
          allClasses = response.data;
          console.log(allClasses);
          for (const item of allClasses) {
            console.log(item);
            if (item.opened) {
              openClass.value.push(item);
            } else {
              makedClass.value.push(item);
            }
          }
          console.log(openClass.value);
          console.log(makedClass.value);
        })
        .catch((error) => {
          console.log(error);
        });
    };
    getClass();
    const filteredMakedClass = ref([]);
    const dataIdx = ref(0);
    const dataLen = 15;

    const top = () => {
      window.scrollTo({ top: 0, behavior: "smooth" });
    };

    const loadMakedClasses = () => {
      console.log(dataIdx, " ", makedClass.value.length);
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

    return {
      top,
      getClass,
      openClass,
      filteredMakedClass,
      loadMakedClasses,
    };
  },
};
</script>

<style scoped src="@/css/classList.scss" lang="scss"></style>
