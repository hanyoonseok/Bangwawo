<template>
  <div class="user-background">
    <div class="login-form">
      <div class="three-duck">
        <img src="@/assets/threeDuck.png" />
      </div>
      <div class="user-select">
        <button class="user-btn">학생&봉사자</button>
        <button class="user-btn">학부모</button>
      </div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginView",
};
</script>

<style scoped src="@/css/login.scss" lang="scss"></style>
