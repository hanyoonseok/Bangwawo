<template>
  <div class="parent">
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
</template>

<script>
export default {
  name: "ParentLogin",
};
</script>
<style scoped src="@/css/login.scss" lang="scss"></style>
