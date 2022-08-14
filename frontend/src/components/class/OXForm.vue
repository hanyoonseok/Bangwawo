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
          <div>
            <input type="radio" id="answer" value="o" v-model="ox.selected" />
            <img src="@/assets/O-btn.png" />
          </div>
          <div>
            <input type="radio" id="answer" value="x" v-model="ox.selected" />
            <img src="@/assets/X-btn.png" />
          </div>
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
  props: ["session", "state", "toggleOX"],
  setup(props, { emit }) {
    const ox = reactive({
      question: "",
      selected: "",
    });
    const enrolOX = () => {
      console.log("ox", ox);
      props.session
        .signal({
          data: ox.question,
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: "start-question", // The type of message (optional)
        })
        .then(() => {
          console.log("start-question");
        })
        .catch((error) => {
          console.error(error);
        });
      props.session
        .signal({
          data: ox.selected === "o" ? true : false,
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: "start-answer", // The type of message (optional)
        })
        .then(() => {
          console.log("start-answer");
          emit("closeOX");
        })
        .catch((error) => {
          console.error(error);
        });
    };

    return {
      ox,
      enrolOX,
    };
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
