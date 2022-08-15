<template>
  <article class="top-article top" v-show="state.isOXOpen && state.isOXResult">
    <div class="title-wrapper">
      <label>결과</label>
      <i class="fa-solid fa-xmark xmark" @click="toggleOX()"></i>
    </div>
    <div class="ox-form row">
      <article class="ox-top-article">
        <img src="@/assets/O-btn.png" />
        <div class="o-name red" v-for="(item, i) in correctStudents" :key="i">
          {{ item.sender }}
        </div>
      </article>
      <article class="ox-top-article">
        <img src="@/assets/X-btn.png" />
        <div class="o-name red" v-for="(item, i) in incorrectStudents" :key="i">
          {{ item.sender }}
        </div>
      </article>
    </div>
    <label class="noanswer">무응답자 : 3</label>
  </article>
</template>

<script>
export default {
  name: "OXResult",
  props: ["state", "toggleOX", "incorrectStudents", "correctStudents"],
  setup() {},
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
