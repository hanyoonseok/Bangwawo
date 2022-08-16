<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section">
      <article class="title-wrapper">
        <h2>학생 리뷰</h2>
      </article>
      <article class="list-wrapper">
        <div
          class="profile"
          v-for="student in students"
          :key="student.sid"
          @click="selected = student"
        >
          <ProfileCanvas :childColor="student.character" :idx="student.sid" />
          <div class="person-info">
            <label>별명</label>{{ student.nickname }}
          </div>
        </div>
      </article>
      <article class="btn-wrapper">
        <button class="submit-btn" @click="isConfirm = true">
          피드백 제출
        </button>
      </article>
    </section>
    <FeedbackModal v-if="selected" :info="selected" :close="close" :cid="CID" />
    <div class="confirm" v-if="isConfirm">
      <div class="container">
        <img src="@/assets/profile.png" alt="오리" />
        <h2>정말로 제출하시겠습니까?</h2>
        <div class="btn-wrapper">
          <router-link :to="{ name: 'mypage' }"
            ><button class="btn">네</button></router-link
          >
          <button class="btn" @click="isConfirm = false">아니요</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
import FeedbackModal from "@/components/feedback/FeedbackModal.vue";
import ProfileCanvas from "@/components/mypage/ProfileCanvas.vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default {
  name: "FeedBack",
  components: {
    HeaderNav,
    FeedbackModal,
    ProfileCanvas,
  },
  props: ["cid"],
  setup() {
    const route = useRoute();
    const store = useStore();
    const CID = ref(route.params.cid);
    const students = ref([]);
    let selected = ref(null);

    const getClassStudents = () => {
      store.dispatch("root/getClassStudents", CID.value).then((res) => {
        const { data } = res;
        for (let i = 0; i < data.length; i++) data[i].idx = i;

        students.value = data;
        console.log(students.value);
      });
    };
    getClassStudents();

    let isConfirm = ref(false);

    const close = (e, textareaValue) => {
      console.log(textareaValue);
      e.stopPropagation();
      const payload = {
        cid: CID.value,
        sid: selected.value.sid,
        feedback: textareaValue,
      };
      if (textareaValue) {
        store.dispatch("root/submitFeedback", payload);
      }
      selected.value = null;
    };

    return {
      students,
      selected,
      close,
      isConfirm,
      CID,
    };
  },
};
</script>

<style scoped src="@/css/feedback.scss" lang="scss"></style>
