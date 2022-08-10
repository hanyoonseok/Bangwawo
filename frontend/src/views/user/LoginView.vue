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
      <button @click="kakaoClick">
        <img src="@/assets/kakao.png" />카카오톡으로 로그인
      </button>
    </div>
    <div class="parent" v-if="!state.isStudent">
      <div class="parent-login">
        <label><i class="fa-solid fa-user"></i></label>
        <input
          class="parent-input"
          type="text"
          placeholder="이메일"
          v-model="pEmail"
        />
      </div>
      <div class="parent-login">
        <label><i class="fa-solid fa-lock-keyhole"></i></label>
        <input
          class="parent-input"
          type="password"
          placeholder="비밀번호"
          v-model="pPassword"
        />
      </div>
      <button class="login-submit" @click="loginParent">로그인</button>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import jwt_decode from "jwt-decode";
import { reactive, onMounted, ref } from "vue";

export default {
  name: "LoginView",
  setup() {
    const store = useStore();
    let userBtn;
    let pEmail = ref("");
    let pPassword = ref("");
    const router = useRouter();

    const getKakao = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_RESTAPI_KEY}&redirect_uri=${process.env.VUE_APP_REDIRECT_URL}&response_type=code`;
    onMounted(() => {
      userBtn = document.querySelectorAll(".user-btn");
    });
    const state = reactive({
      isStudent: true,
    });

    const kakaoClick = () => {
      console.log("click kakao");
      window.location.href = getKakao;
    };

    const doActive = (e) => {
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

    const loginParent = () => {
      const loginObj = {
        email: pEmail.value,
        password: pPassword.value,
      };
      console.log(loginObj);
      loginObj.userType = "parent";
      store
        .dispatch("root/loginUser", loginObj)
        .then((res) => {
          console.log(res.data);
          const decode_jwt = jwt_decode(res.data.JWT);
          decode_jwt.user.userType = decode_jwt.userType.toLowerCase();
          store.commit("root/setUserInfo", decode_jwt.user);
          router.push("/mypage");
        })
        .catch((err) => {
          console.log(err.message);
          alert("아이디 혹은 비밀번호가 일치하지 않습니다");
        });
    };

    return {
      state,
      doActive,
      userBtn,
      kakaoClick,
      pEmail,
      pPassword,
      loginParent,
    };
  },
  components: {
    // StuAndVoluLogin,
    // ParentLogin,
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
