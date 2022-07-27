<template>
  <div class="signup-volunteer">
    <div class="guide-volunteer">
      <img src="@/assets/guide-volunteer.png" />
      <div class="guide">안녕하세요 OOO님. <br />자기소개를 입력해주세요.</div>
    </div>
    <textarea
      class="introduction"
      placeholder="자기소개를 입력해주세요."
    ></textarea>
    <button class="signup-submit">확인</button>
  </div>
</template>

<script>
export default {
  name: "SignupUpVolunteer",
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
