<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section detail">
      <div class="backbtn-wrapper">
        <button></button>
      </div>
      <article class="content-section"></article>
    </section>
  </div>
</template>

<script>
import { ref } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  name: "ClassRequestDetail",
  components: {
    HeaderNav,
  },
  setup() {
    const data = ref({
      title: "이전에 사용했던 제목",
      content: "이전에 사용했던 내용",
    });

    return {
      data,
    };
  },
};
</script>

<style scoped src="@/css/classRequest.scss" lang="scss"></style>
