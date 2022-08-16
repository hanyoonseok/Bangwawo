<template>
  <article class="top-article top" v-show="state.isOXOpen && state.isOXResult">
    <div class="title-wrapper">
      <label>결과</label>
      <i class="fa-solid fa-xmark xmark" @click="toggleOX()"></i>
    </div>
    <div class="ox-form row">
      <article class="ox-top-article">
        <img src="@/assets/O-btn.png" />
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
        <div class="o-name blue">김수빈</div>
      </article>
      <article class="ox-top-article">
        <img src="@/assets/X-btn.png" />
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
        <div class="o-name red">김수빈</div>
      </article>
    </div>
    <label class="noanswer">무응답자 : {{ noneStudents.length }}</label>
  </article>
</template>

<script>
export default {
  name: "OXResult",
<<<<<<< Updated upstream
  props: ["state", "toggleOX"],
=======
  props: [
    "state",
    "toggleOX",
    "incorrectStudents",
    "correctStudents",
    "noneStudents",
  ],
>>>>>>> Stashed changes
  setup() {},
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
