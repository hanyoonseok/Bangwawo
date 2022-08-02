<template>
  <div class="character-modal" v-if="character === true">
    <div class="content-wrapper">
      <button class="back-btn">
        <i class="fa-solid fa-xmark"></i>
      </button>
      <div class="box">
        <div class="left-box">
          <div class="select">
            <button class="select-btn body active" @click="doActive">몸</button>
            <button class="select-btn clothes" @click="doActive">옷</button>
            <button class="select-btn foot" @click="doActive">발</button>
            <button class="select-btn hair" @click="doActive">머리</button>
            <button class="select-btn bag" @click="doActive">가방</button>
            <button class="select-btn glasses" @click="doActive">안경</button>
          </div>
          <ColorPicker
            class="color-content"
            color="hsl(270 100% 50% / 0.8)"
            alpha-channel="hide"
            @color-change="updateColor"
            copy-button="hide"
          />
        </div>
        <div class="right-box">
          <TheCanvas />
        </div>
      </div>
      <button class="save-btn">저장하기</button>
    </div>
  </div>
</template>

<script>
import { onMounted } from "@vue/runtime-core";
import { ColorPicker } from "vue-accessible-color-picker";
import TheCanvas from "@/components/mypage/TheCanvas.vue";

export default {
  name: "CharacterModal",
  props: ["character"],
  components: {
    ColorPicker,
    TheCanvas,
  },
  setup() {
    const colors = {
      hex: "#194d33",
      hex8: "#194D33A8",
      hsl: { h: 150, s: 0.5, l: 0.2, a: 1 },
      hsv: { h: 150, s: 0.66, v: 0.3, a: 1 },
      rgba: { r: 25, g: 77, b: 51, a: 1 },
      a: 1,
    };

    const updateColor = (eventData) => {
      console.log(eventData.cssColor);
      // color.value = eventData.cssColor;
    };

    let selectBtn;
    onMounted(() => {
      selectBtn = document.querySelectorAll(".select-btn");
    });

    const doActive = (e) => {
      selectBtn.forEach((item) => {
        item.classList.remove("active");
      });

      let part = e.target.classList;

      if (part.contains("body")) {
        console.log("몸");
      } else if (part.contains("foot")) {
        console.log("발");
      } else if (part.contains("clothes")) {
        console.log("옷");
      } else if (part.contains("hair")) {
        console.log("머리");
      } else if (part.contains("bag")) {
        console.log("가방");
      } else if (part.contains("glasses")) {
        console.log("안경");
      }
      part.add("active");
    };

    return {
      colors,
      updateColor,
      selectBtn,
      doActive,
    };
  },
};
</script>

<style lang="scss" scoped src="@/css/character.scss"></style>
