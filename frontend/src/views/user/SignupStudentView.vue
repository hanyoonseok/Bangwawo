<template>
  <div class="signup-container">
    <div class="input-box">
      <input class="sign-input" placeholder="학부모 이메일을 입력해주세요." />
      <input class="sign-input" placeholder="별명을 입력해주세요." />

      <button class="signup-submit">로그인</button>
    </div>
    <div class="duck-img">
      <div class="guide">별명, 부모님 이메일을 알려줘~</div>
      <img src="@/assets/signupDuck.png" />
    </div>
  </div>
</template>

<script>
export default {
  name: "SignupStudent",
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
