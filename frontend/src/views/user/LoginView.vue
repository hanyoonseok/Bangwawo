<template>
  <div class="login-form">
    <div class="three-duck">
      <img src="@/assets/threeDuck.png" />
    </div>
    <div class="user-select">
      <button class="user-btn active" @click="doActive">학생&봉사자</button>
      <button class="user-btn" @click="doActive">학부모</button>
    </div>

    <div class="stu-volu-wrapper" v-if="state.isStudent">
      <div class="login-desc">
        만 19세 미만의 유저는 학생으로, <br />이상의 유저는 봉사자로 참여할 수
        있어요
      </div>
      <button><img src="@/assets/kakao.png" />카카오톡으로 로그인</button>
    </div>
    <div class="parent" v-if="!state.isStudent">
      <div class="parent-login">
        <label><i class="fa-solid fa-user"></i></label>
        <input class="parent-input" type="text" placeholder="이메일" />
      </div>
      <div class="parent-login">
        <label><i class="fa-solid fa-lock-keyhole"></i></label>
        <input class="parent-input" type="text" placeholder="비밀번호" />
      </div>

      <button class="login-submit">로그인</button>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted } from "vue";
// import StuAndVoluLogin from "@/components/StuAndVoluLogin.vue";
// import ParentLogin from "@/components/ParentLogin.vue";
export default {
  name: "LoginView",
  setup() {
    let userBtn;
    onMounted(() => {
      userBtn = document.querySelectorAll(".user-btn");
    });
    const state = reactive({
      isStudent: true,
    });

    const doActive = (e) => {
      console.log(state.isStudent);
      if (!e.target.classList.contains("active")) {
        e.target.classList.add("active");
      }
      if (e.target.innerText === "학생&봉사자") {
        if (!state.isStudent) {
          state.isStudent = !state.isStudent;
        }
        userBtn[1].classList.remove("active");
      } else {
        // 만약 선택된 메뉴가 학부모라면,
        if (state.isStudent) {
          state.isStudent = !state.isStudent;
        }
        userBtn[0].classList.remove("active");
      }
    };

    return {
      state,
      doActive,
      userBtn,
    };
  },
  components: {
    // StuAndVoluLogin,
    // ParentLogin,
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
