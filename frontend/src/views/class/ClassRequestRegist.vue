<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section">
      <section class="title-section">
        <img src="@/assets/consult-1.png" />
        <article class="title-wrapper">
          <h2>요청 글 등록</h2>
        </article>
        <img src="@/assets/consult-3.png" />
      </section>
      <section class="content-wrapper">
        <label class="input-label">제목</label>
        <input
          type="text"
          placeholder="제목을 입력해주세요."
          v-model="state.requestTitle"
        />
      </section>
      <section class="content-wrapper" style="height: 100%">
        <label class="input-label">내용</label>
        <textarea
          placeholder="내용을 입력해주세요."
          v-model="state.requestContent"
        ></textarea>
      </section>
      <div class="btn-wrapper">
        <button @click="submitRequestRegister">등록하기</button>
      </div>
    </section>
  </div>
</template>

<script>
import axios from "axios";
import HeaderNav from "@/components/HeaderNav.vue";
import { reactive } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
export default {
  name: "ClassRequestRegist",
  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      requestTitle: "",
      requestContent: "",
      userInfo: store.state.root.user,
      userType: store.state.root.user.userType,
    });

    const submitRequestRegister = async () => {
      axios
        .post(`${process.env.VUE_APP_API_URL}/request`, {
          sid: {
            sid: state.userInfo.sid,
          },
          content: state.requestContent,
          title: state.requestTitle,
        })
        .then((response) => {
          console.log(response);
          router.push("/class/requestlist");
        });
    };
    return { state, submitRequestRegister };
  },

  components: {
    HeaderNav,
  },
};
</script>

<style scoped src="@/css/classRequest.scss" lang="scss"></style>
