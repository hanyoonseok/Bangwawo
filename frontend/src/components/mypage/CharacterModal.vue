<template>
  <div class="character-modal">
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
            <button class="select-btn hat" @click="doActive">모자</button>
            <button class="select-btn bag" @click="doActive">가방</button>
            <button class="select-btn glasses" @click="doActive">안경</button>
          </div>
          <ColorPicker
            class="color-content"
            :color="change.color"
            alpha-channel="hide"
            @color-change="updateColor"
            copy-button="hide"
          />
        </div>
        <div class="right-box">
          <TheCanvas :parts="parts" />
        </div>
      </div>
      <button class="save-btn" @click="saveCharacter">저장하기</button>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { reactive } from "@vue/runtime-core";
import { ColorPicker } from "vue-accessible-color-picker";
import TheCanvas from "@/components/mypage/TheCanvas.vue";

export default {
  name: "CharacterModal",
  emits: ["close-character-modal"],
  components: {
    ColorPicker,
    TheCanvas,
  },
  setup(props, { emit }) {
    const store = useStore();
    let change = reactive({
      color: "#" + store.state.root.user.characterColors[0].color,
      type: "body",
    });

    //초기 캐릭터 색 : store에서 받아온 초기 색
    let parts = reactive(store.state.root.user.characterColors);
    console.log("처음", parts);

    const updateColor = (eventData) => {
      change.color = eventData.colors.hex.slice(0, 7);
      document.querySelectorAll(".select-btn").forEach((item) => {
        if (item.classList.contains("active")) {
          if (item.classList.contains("body")) {
            change.type = "body";
          } else if (item.classList.contains("clothes")) {
            change.type = "clothes";
          } else if (item.classList.contains("foot")) {
            change.type = "foot";
          } else if (item.classList.contains("hat")) {
            change.type = "hat";
          } else if (item.classList.contains("bag")) {
            change.type = "bag";
          } else if (item.classList.contains("glasses")) {
            change.type = "glasses";
          }
        }
      });
      for (const item of parts) {
        if (item.id === change.type) {
          item.color = change.color.replaceAll("#", "");
        }
      }
      setPickerColor(change.type);
    };

    const doActive = (e) => {
      document.querySelectorAll(".select-btn").forEach((item) => {
        item.classList.remove("active");
      });

      let part = e.target.classList;
      let set = null;

      if (part.contains("body")) {
        set = "body";
      } else if (part.contains("clothes")) {
        set = "clothes";
      } else if (part.contains("foot")) {
        set = "foot";
      } else if (part.contains("hat")) {
        set = "hat";
      } else if (part.contains("bag")) {
        set = "bag";
      } else if (part.contains("glasses")) {
        set = "glasses";
      }
      part.add("active");

      setPickerColor(set);
    };

    // 탭 눌렀을때 parts에 있는 id값에 맞춰 컬러 피커 색 변경
    const setPickerColor = (set) => {
      for (const item of parts) {
        if (item.id === set) {
          change.color = "#" + item.color;
        }
      }
    };

    const closeCharacterModal = () => {
      emit("close-character-modal");
    };

    // DB로 캐릭터 부위별 색상 값 보내기
    const saveCharacter = () => {
      store.state.root.user.characterColors = parts;
      emit("close-character-modal");
    };

    return {
      // color,
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
