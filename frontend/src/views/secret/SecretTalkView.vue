<template>
  <section class="main-section">
    <article class="title-wrapper">
      <h2>비밀친구 대화중</h2>
    </article>
    <article class="user-wrapper">
      <!-- <div class="user-card"> -->
      <SecretCanvas :parts="student" class="user-card" :user="stu" id="stu" />
      <!-- </div> -->
      <SecretCanvas :parts="volunteer" class="user-card" :user="vol" id="vol" />
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

export default {
  name: "SecretTalkView",
  components: {
    SecretCanvas,
  },
  setup() {
    //초기 캐릭터 색 : 백엔드에 저장한 db에서 받아올것임
    let student = reactive([
      { id: "body", color: "f1f1f1" },
      { id: "mouse", color: "000000" },
      { id: "lhand", color: "ffffff" },
      { id: "rhand", color: "527329" },
      { id: "body", color: "ff9696" },
      { id: "body", color: "ff9696" },
    ]);
    let volunteer = reactive([
      { id: "body", color: "f1f1f1" },
      { id: "mouse", color: "000000" },
      { id: "lhand", color: "ffffff" },
      { id: "rhand", color: "527329" },
      { id: "body", color: "ff9696" },
      { id: "body", color: "ff9696" },
    ]);

    let stu = "stu";
    let vol = "vol";
    return {
      student,
      volunteer,
      stu,
      vol,
    };
  },
};
</script>

<style scoped src="@/css/secret-friend.scss" lang="scss"></style>
