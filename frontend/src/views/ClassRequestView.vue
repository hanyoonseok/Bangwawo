<template>
  <div class="background">
    <HeaderNav />
    <div class="title">
      <h2>수업 요청해요</h2>
    </div>
    <div class="search-bar">
      <input type="text" placeholder="찾고 싶은 수업을 입력하세요." />
      <button class="searchBtn">
        <i class="fa-solid fa-magnifying-glass"></i>
      </button>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  name: "ClassRequestView",
  component: {
    HeaderNav,
  },
};
</script>

<style src="@/css/classRequest.scss" lang="scss" scoped></style>
