<template>
  <section class="modal">
    <div class="empty" @click="close"></div>
    <div class="container">
      <div class="stretch">
        <article class="btn-wrapper">
          <i class="fa-solid fa-xmark close" @click="close"></i>
        </article>

        <article class="title-wrapper">
          <h2>피드백</h2>
        </article>
        <article class="center-wrapper">
          <div class="modal-profile">
            <ProfileCanvas :childColor="info.character" :idx="info.sid + 'a'" />
            <div class="person-info">
              <label>별명</label>{{ info.nickname }}
            </div>
          </div>
          <textarea v-model="textareaValue"></textarea>
        </article>
        <article class="btn-wrapper">
          <button class="submit-btn" @click="(e) => close(e, textareaValue)">
            작성 완료
          </button>
        </article>
      </div>
    </div>
  </section>
</template>

<script>
import ProfileCanvas from "@/components/mypage/ProfileCanvas.vue";
import { ref } from "vue";
import { useStore } from "vuex";

export default {
  name: "FeedbackModal",
  props: ["info", "close", "cid"],
  setup(props) {
    const store = useStore();
    let textareaValue = ref("");
    console.log(props.info);
    store
      .dispatch("root/getStudentFeedback", {
        sid: props.info.sid,
        cid: props.cid,
      })
      .then((res) => {
        console.log(res);
        textareaValue.value = res.data.feedback;
      });

    return {
      textareaValue,
    };
  },
  components: {
    ProfileCanvas,
  },
};
</script>

<style scoped src="@/css/feedback.scss" lang="scss"></style>
