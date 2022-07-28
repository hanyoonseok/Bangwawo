<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section">
      <section class="title-section">
        <img src="@/assets/consult-1.png" />
        <article class="title-wrapper">
          <h2>요청 글 등록</h2>
        </article>
        <img src="@/assets/consult-3.png" />
      </section>
      <section class="content-wrapper">
        <label class="input-label">제목</label>
        <input
          type="text"
          placeholder="제목을 입력해주세요."
          v-model="data.title"
        />
      </section>
      <section class="content-wrapper" style="height: 100%">
        <label class="input-label">내용</label>
        <textarea
          placeholder="내용을 입력해주세요."
          v-model="data.content"
        ></textarea>
      </section>
      <div class="btn-wrapper">
        <button>등록하기</button>
      </div>
    </section>
  </div>
</template>

<script>
import { ref } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  name: "ClassRequestModify",
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
