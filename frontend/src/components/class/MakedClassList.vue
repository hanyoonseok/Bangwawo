<template>
  <div class="maked">
    <div class="search-bar">
      <input type="text" placeholder="찾고 싶은 수업을 입력하세요." />
      <button class="search-btn">
        <i class="fa-solid fa-magnifying-glass"></i>
      </button>
    </div>
    <div class="title">
      <h2>개설된 수업</h2>
      <p>현재 개설된 수업 목록입니다.</p>
    </div>
    <div class="contents">
      <div v-for="(item, index) in maked" :key="index">
        <router-link to="/class/detail"
          ><RectPostCard :state="item"
        /></router-link>
      </div>
    </div>
  </div>
</template>

<script>
import RectPostCard from "@/components/common/RectPostCard.vue";

export default {
  name: "MakedClassList",
  components: {
    RectPostCard,
  },
  props: ["maked"],
  setup() {
    return {};
  },
};
</script>

<style scoped src="@/css/classList.scss" lang="scss"></style>
