<template>
  <article class="top-article top" v-show="state.isOXOpen && !state.isOXResult">
    <div class="title-wrapper">
      <label>OX게임 등록</label>
      <i class="fa-solid fa-xmark xmark" @click="toggleOX()"></i>
    </div>
    <div class="ox-form">
      <article class="ox-top-article">
        <h4>
          질문을 적고 O/X 정답을 고르면 학생들이 OX퀴즈를 시작하게 됩니다.
        </h4>
        <input
          type="text"
          class="ox-input"
          placeholder="질문을 등록하세요"
          v-model="ox.question"
        />
        <div class="ox-btn-wrapper">
          <img src="@/assets/O-btn.png" />
          <img src="@/assets/X-btn.png" />
        </div>
      </article>
      <button class="submit-btn" @click="enrolOX">등록하기</button>
    </div>
  </article>
</template>

<script>
import { reactive } from "@vue/reactivity";
export default {
  name: "OXForm",
  props: ["state", "toggleOX"],
  setup() {
    const ox = reactive({
      question: "",
    });
    const enrolOX = () => {};

    return {
      ox,
      enrolOX,
    };
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
