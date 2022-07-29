<template>
  <div class="background">
    <HeaderNav />
    <section class="main-section detail">
      <div class="backbtn-wrapper">
        <button class="backbtn"></button>
      </div>
      <article class="content-section">
        <div class="content-box">
          <img src="@/assets/profile.png" class="header-img" />
          <h4>애기오리는 이러이러한 수업이 듣고싶습니다. 대충 아무제목...</h4>
          <div class="content-writer">
            애기오리 <label>&nbsp;2022.07.20</label>
          </div>
          <div class="status-wrapper">
            <button class="status-btn">
              <i class="fa-solid fa-circle"></i>&nbsp;미해결
            </button>
          </div>
          <div class="content-text">
            애기오리는 오리선생의 역사이야기가 너무 듣고싶습니다. <br />오리선생
            너무 귀여워용 ~~
          </div>
          <div class="end-btn-wrapper">
            <img src="@/assets/notice-text.png" />
            <span></span>
            <button class="end-btn">
              <i class="fa-solid fa-thumbs-up"></i> &nbsp;99
            </button>
            <div>
              <button class="end-btn">
                <i class="fa-solid fa-trash-can"></i>&nbsp;삭제</button
              ><button class="end-btn">
                <i class="fa-solid fa-pen"></i>&nbsp;수정
              </button>
            </div>
          </div>
          <div class="status-wrapper"><label>조회수 33</label></div>
        </div>
      </article>
    </section>
  </div>
</template>

<script>
import { ref } from "vue";
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  name: "ClassRequestDetail",
  components: {
    HeaderNav,
  },
  setup() {
    const data = ref({
      title: "이전에 사용했던 제목",
      content: "이전에 사용했던 내용",
    });

    return {
      data,
    };
  },
};
</script>

<style scoped src="@/css/classRequest.scss" lang="scss"></style>
