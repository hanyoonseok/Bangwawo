<template>
  <div class="stu-ox-container">
    <section class="stu-ox-section">
      <div class="quest-container">
        <i class="fa-solid fa-q"></i>
        <label>{{ oxData.question }} </label>
      </div>

      <div class="stu-ox-imgcontainer">
        <img src="@/assets/oDuck.png" @click="selectOne(true)" />
        <img src="@/assets/xDuck.png" @click="selectOne(false)" />
      </div>
      <div class="selected-answer">
        선택한 답 : {{ state.answer ? "O" : "X" }}
      </div>
      <div class="stu-ox-resttime">
        <label>{{ timer }}</label>
      </div>
    </section>
    <!-- <section class="stu-ox-section result" v-if="state.select">
      <img src="@/assets/oDuck.png" v-if="state.select === 'o'" />
      <img src="@/assets/xDuck.png" v-if="state.select === 'x'" />
    </section> -->
  </div>
</template>

<script>
import { onMounted, reactive, ref } from "vue";
export default {
  props: ["oxData"],
  name: "StudentOX",
  setup(props, { emit }) {
    console.log("학생이 보는 ox", props.oxData);
    const state = reactive({ answer: false });

    const timer = ref(5);

    const selectOne = (option) => {
      state.answer = option;
    };
    onMounted(() => {
      // 30초 지나고 ox 종료시키기
      setInterval(() => {
        timer.value--;
        if (timer.value == 0) {
          console.log("30초 지났음 ox 사라져");
          console.log("학생이 선택한 답", state.answer);
          emit("closeOX", state.answer);
        }
      }, 1000);
    });

    return {
      state,
      timer,
      selectOne,
    };
  },
  components: {},
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
