<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section">
      <section class="title-section">
        <img src="@/assets/consult-1.png" />
        <article class="title-wrapper">
          <h2>요청 글 수정</h2>
        </article>
        <img src="@/assets/consult-3.png" />
      </section>
      <section class="content-wrapper">
        <label class="input-label">제목</label>
        <input
          type="text"
          placeholder="제목을 입력해주세요."
          v-model="state.requestPost.title"
        />
      </section>
      <section class="content-wrapper" style="height: 100%">
        <label class="input-label">내용</label>
        <textarea
          placeholder="내용을 입력해주세요."
          v-model="state.requestPost.content"
        ></textarea>
      </section>
      <div class="btn-wrapper">
        <button @click="modifyRequest">등록하기</button>
      </div>
    </section>
  </div>
</template>

<script>
import { ref, reactive } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import axios from "axios";
export default {
  name: "ClassRequestModify",
  components: {
    HeaderNav,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    const data = ref({
      title: "이전에 사용했던 제목",
      content: "이전에 사용했던 내용",
    });
    let state = reactive({
      requestPost: {},
      userInfo: store.state.root.user,
      userType: store.state.root.user.userType,
    });
    const rid = route.params.rid;
    const getRequest = async () => {
      axios
        .get(`${process.env.VUE_APP_API_URL}/request/${rid}`)
        .then((response) => {
          state.requestPost = response.data.requsest;
        });
    };
    getRequest();
    const modifyRequest = async () => {
      axios
        .put(`${process.env.VUE_APP_API_URL}/request`, {
          content: state.requestPost.content,
          title: state.requestPost.title,
          rid: rid,
        })
        .then((response) => {
          router.push(`/class/requestdetail/${rid}`);
          console.log(response);
        });
    };

    return {
      data,
      modifyRequest,
      getRequest,
      state,
    };
  },
};
</script>

<style scoped src="@/css/classRequest.scss" lang="scss"></style>
