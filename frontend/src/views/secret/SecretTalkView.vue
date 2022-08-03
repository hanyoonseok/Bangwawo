<template>
  <section class="main-section">
    <article class="title-wrapper">
      <h2>비밀친구 대화중</h2>
    </article>
    <article class="user-wrapper">
      <SecretCanvas
        :parts="student"
        class="user-card"
        :user="stu"
        id="stu"
        :theModel="model"
      />
      <!-- <SecretCanvas
        :parts="volunteer"
        class="user-card"
        :user="vol"
        id="vol"
        :theModel="model"
      /> -->
    </article>
    <article class="btn-wrapper">
      <button class="option-btn">
        <i class="fa-solid fa-microphone-slash"></i>
        &nbsp;음소거 해제
      </button>
      <button class="option-btn">
        <i class="fa-solid fa-xmark"></i> &nbsp;대화 종료
      </button>
    </article>
  </section>
</template>

<script>
import SecretCanvas from "@/components/secret/SecretCanvas.vue";
import { reactive } from "vue";
import { useStore } from "vuex";

export default {
  name: "SecretTalkView",
  components: {
    SecretCanvas,
  },
  setup() {
    const store = useStore();
    console.log(store.state.root.user);
    let model = store.state.root.user.model;
    //초기 캐릭터 색 : 백엔드에 저장한 db에서 받아올것임
    let student = reactive(store.state.root.user.characterColors);
    let volunteer = reactive(store.state.root.user.characterColors);

    let stu = "stu";
    let vol = "vol";
    return {
      student,
      volunteer,
      stu,
      vol,
      model,
    };
  },
};
</script>

<style scoped src="@/css/secret-friend.scss" lang="scss"></style>
