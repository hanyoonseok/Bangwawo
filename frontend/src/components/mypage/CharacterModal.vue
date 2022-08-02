<template>
  <div class="character-modal" v-if="character === true">
    <div class="content-wrapper">
      <button class="back-btn">
        <i class="fa-solid fa-xmark"></i>
      </button>
      <div class="box">
        <div class="left-box">
          <div class="select options">
            <button class="select-btn body active option" @click="doActive">
              몸
            </button>
            <button class="select-btn clothes" @click="doActive">옷</button>
            <button class="select-btn foot" @click="doActive">발</button>
            <button class="select-btn hair" @click="doActive">머리</button>
            <button class="select-btn bag" @click="doActive">가방</button>
            <button class="select-btn glasses" @click="doActive">안경</button>
          </div>
          <ColorPicker
            class="color-content"
            :color="color"
            alpha-channel="hide"
            @color-change="updateColor"
            copy-button="hide"
          />
        </div>
        <div class="right-box">
          <TheCanvas :change="change" />
        </div>
      </div>
      <button class="save-btn">저장하기</button>
    </div>
  </div>
</template>

<script>
import { onMounted, reactive, ref } from "@vue/runtime-core";
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
    let color = ref("#194d33"); // color picker 색
    let change = reactive({
      //canvas로 전달할 색, 타입
      color: "194d33",
      type: "body",
    });

    const updateColor = (eventData) => {
      console.log(change.color);
      // if (eventData.color === undefined) {
      //   change.color = "000000";
      // } else {
      change.color = eventData.colors.hex.replaceAll("#", "").slice(0, 6);
      // }
      document.querySelectorAll(".select-btn").forEach((item) => {
        if (item.classList.contains("active")) {
          if (item.classList.contains("body")) {
            change.type = "body";
          } else if (item.classList.contains("foot")) {
            change.type = "mouse";
            // change.type = "foot";
          } else if (item.classList.contains("clothes")) {
            // change.type = "clothes";
            change.type = "lhand";
          } else if (item.classList.contains("hair")) {
            // change.type = "hair";
            change.type = "rhand";
          } else if (item.classList.contains("bag")) {
            change.type = "bag";
          } else if (item.classList.contains("glasses")) {
            change.type = "glasses";
          }
        }
      });
      console.log(change.type);
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

      updateColor(e);
    };

    return {
      color,
      selectBtn,
      change,
      updateColor,
      doActive,
    };
  },
};
</script>

<style lang="scss" scoped src="@/css/character.scss"></style>
