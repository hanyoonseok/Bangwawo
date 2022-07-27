<template>
  <div class="stu-ox-container">
    <section class="stu-ox-section" v-if="!state.select">
      <div class="quest-container">
        <i class="fa-solid fa-q"></i>
        <label
          >질뮨잚ㄴㅁ얀ㅁㅇ런ㅇㄹㅈㄷㅊㅁㄴㅇasdfasdfasdfdasfasdfasdfasdfasdfasdfasdfasdfasfㄹㄴㅁㅇㄻㄴㅇㅊ
        </label>
      </div>

      <div class="stu-ox-imgcontainer">
        <img src="@/assets/oDuck.png" @click="selectOne('o')" />
        <img src="@/assets/xDuck.png" @click="selectOne('x')" />
      </div>

      <div class="stu-ox-resttime"><label>30</label></div>
    </section>
    <section class="stu-ox-section result" v-if="state.select">
      <img src="@/assets/oDuck.png" v-if="state.select === 'o'" />
      <img src="@/assets/xDuck.png" v-if="state.select === 'x'" />
    </section>
  </div>
</template>

<script>
import { reactive } from "vue";
export default {
  name: "StudentOX",
  setup() {
    const state = reactive({
      select: "",
    });

    const selectOne = (option) => {
      if (state.select === option) state.select = "";
      else state.select = option;
    };

    return {
      state,
      selectOne,
    };
  },
  components: {},
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
