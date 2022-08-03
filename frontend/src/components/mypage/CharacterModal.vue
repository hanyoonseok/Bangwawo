<template>
  <div class="character-modal" v-if="character === true">
    <div class="content-wrapper">
      <button class="back-btn" @click="closeCharacterModal">
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
          <TheCanvas :change="change" :parts="parts" />
        </div>
      </div>
      <button class="save-btn" @click="saveCharacter">저장하기</button>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "@vue/runtime-core";
import { ColorPicker } from "vue-accessible-color-picker";
import TheCanvas from "@/components/mypage/TheCanvas.vue";

export default {
  name: "CharacterModal",
  props: ["character"],
  emits: ["close-character-modal"],
  components: {
    ColorPicker,
    TheCanvas,
  },
  setup(props, { emit }) {
    let color = ref("#194d33"); // color picker 색
    let change = reactive({
      //canvas로 전달할 색, 타입
      color: "194d33",
      type: "body",
    });

    //초기 캐릭터 색 : 백엔드에 저장한 db에서 받아올것임
    let parts = reactive([
      { id: "body", color: "f1f1f1" },
      { id: "hair", color: "ff9696" },
      { id: "bag", color: "000000" },
      { id: "clothes", color: "ffffff" },
      { id: "hat", color: "527329" },
      { id: "foot", color: "ff9696" },
    ]);

    const updateColor = (eventData) => {
      console.log(change.color);
      // if (eventData.color === undefined) {
      //   change.color = "000000";
      // } else {
      console.log(eventData.colors);
      change.color = eventData.colors.hex.replaceAll("#", "").slice(0, 6);
      // }
      document.querySelectorAll(".select-btn").forEach((item) => {
        if (item.classList.contains("active")) {
          if (item.classList.contains("body")) {
            change.type = "body";
          } else if (item.classList.contains("clothes")) {
            // change.type = "clothes";
            change.type = "clothes";
          } else if (item.classList.contains("foot")) {
            change.type = "foot";
            // change.type = "foot";
          } else if (item.classList.contains("hair")) {
            change.type = "hair";
            // change.type = "rhand";
          } else if (item.classList.contains("bag")) {
            change.type = "bag";
          } else if (item.classList.contains("glasses")) {
            change.type = "glasses";
          }
        }
      });
      console.log(change.type);
      setColor(change.type);
      for (const item of parts) {
        if (item.id === change.type) {
          item.color = change.color;
        }
      }
    };

    // let selectBtn;
    // onMounted(() => {
    //   selectBtn = document.querySelectorAll(".select-btn");
    // });

    const doActive = (e) => {
      document.querySelectorAll(".select-btn").forEach((item) => {
        item.classList.remove("active");
      });
      // console.log(index);
      console.log(e);

      let part = e.target.classList;
      let set = null;

      if (part.contains("body")) {
        set = "body";
        console.log("몸");
      } else if (part.contains("clothes")) {
        // set = "clothes";
        set = "clothes";
        console.log("옷");
      } else if (part.contains("foot")) {
        // set = "foot";
        set = "lhand";
        console.log("발");
      } else if (part.contains("hair")) {
        set = "hair";
        console.log("머리");
      } else if (part.contains("bag")) {
        set = "bag";
        console.log("가방");
      } else if (part.contains("glasses")) {
        set = "glasses";
        console.log("안경");
      }
      part.add("active");

      console.log(e);
      setColor(set);
    };

    // 탭 눌렀을때 parts에 있는 id값에 맞춰 컬러 피커 색 변경
    const setColor = (set) => {
      console.log(set);
      for (const item of parts) {
        if (item.id === set) {
          color.value = "#" + item.color;
        }
      }
    };

    const closeCharacterModal = () => {
      emit("close-character-modal");
    };

    // DB로 캐릭터 부위별 색상 값 보내기
    const saveCharacter = () => {
      console.log(parts);
    };

    return {
      color,
      parts,
      change,
      updateColor,
      doActive,
      closeCharacterModal,
      saveCharacter,
    };
  },
};
</script>

<style lang="scss" scoped src="@/css/character.scss"></style>
