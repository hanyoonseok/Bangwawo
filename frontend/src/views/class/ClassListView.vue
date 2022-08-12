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
      <div class="search-bar">
        <input
          type="text"
          placeholder="찾고 싶은 수업을 입력하세요."
          @keyup="searchClass"
          @input="changeKeyword"
        />
        <button class="search-btn">
          <i class="fa-solid fa-magnifying-glass"></i>
        </button>
      </div>
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
import { useStore } from "vuex";
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
    const store = useStore();
    let allClasses = ref([]);
    let openClass = ref([]);
    let makedClass = ref([]);
    const keyword = ref();
    const filteredMakedClass = ref([]);
    const dataIdx = ref(0);
    const dataLen = 15;

    const getClassList = (flag) => {
      store
        .dispatch("root/getClassList")
        .then((res) => {
          // console.log(res);
          allClasses = res.data;
          console.log(allClasses);
          const openArray = [];
          const makedArray = [];
          for (const item of allClasses) {
            if (item.opened) {
              openArray.push(item);
            } else {
              makedArray.push(item);
            }
          }

          openClass.value = openArray;
          makedClass.value = makedArray;
          console.log(openClass.value);
          console.log(makedClass.value);
          if (flag) {
            filteredMakedClass.value = [];
          } else {
            filteredMakedClass.value = makedArray;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    };
    getClassList(true);

    const searchClass = async () => {
      console.log(keyword.value);
      if (keyword.value !== "") {
        axios
          .get(`${process.env.VUE_APP_API_URL}/class`, {
            params: { title: keyword.value },
          })
          .then((response) => {
            const data = response.data;
            console.log("검색 결과 ", data);

            const searchArr = [];
            for (const item of data) {
              if (!item.opened) {
                searchArr.push(item);
              }
            }
            makedClass.value = searchArr;
            filteredMakedClass.value = searchArr;
            console.log(makedClass.value);
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        console.log("엥?");
        getClassList(false);
      }
    };

    const changeKeyword = (e) => {
      keyword.value = e.target.value;
    };

    const top = () => {
      window.scrollTo({ top: 0, behavior: "smooth" });
    };

    const loadMakedClasses = () => {
      if (dataIdx.value > makedClass.value.length) return;
      let prevArr = [...filteredMakedClass.value];

      for (
        let i = dataIdx.value;
        i < Math.min(makedClass.value.length, dataIdx.value + dataLen);
        i++
      ) {
        prevArr.push(makedClass.value[i]);
      }
      dataIdx.value += dataLen;
      filteredMakedClass.value = prevArr;
      // console.log(filteredMakedClass.value);
    };

    return {
      top,
      getClassList,
      openClass,
      filteredMakedClass,
      loadMakedClasses,
      keyword,
      changeKeyword,
      searchClass,
    };
  },
};
</script>

<style scoped src="@/css/classList.scss" lang="scss"></style>
