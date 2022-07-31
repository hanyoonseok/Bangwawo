<template>
  <div class="left-box profile">
    <i class="fa-solid fa-ellipsis-vertical profile-ellipse">
      <ul>
        <li>
          <i class="fa-solid fa-pen-to-square"></i>
          &nbsp;<span>정보수정</span>
        </li>

        <li>
          <i class="fa-solid fa-arrow-right-from-bracket"></i>
          &nbsp;<span>회원탈퇴</span>
        </li>
      </ul></i
    >

    <div>
      <img src="@/assets/profile.png" />
    </div>
    <div class="person-info">
      <label>이름</label>
      <div class="label-value volunteer-value" v-if="user.status === 2">
        이화연 바보
      </div>
      <div class="label-value" v-else>이화연 바보</div>
    </div>
    <div class="person-info" v-if="user.status === 1">
      <label>별명</label>
      <div class="label-value">애기화연</div>
    </div>

    <div class="person-info" v-if="user.status === 2">
      <label>자기소개</label>
      <div class="label-value introduce">자기소개 글</div>
    </div>
    <div class="person-info" v-else>
      <label>자녀</label>
      <div class="label-value">
        <button class="children-name">애기화연</button>
        <button class="children-name">애기화연</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  setup() {
    const user = {
      status: 3,
      subscribe: 0,
    };
    return {
      user,
    };
  },
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
