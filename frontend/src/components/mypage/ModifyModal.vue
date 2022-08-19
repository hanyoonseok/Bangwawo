<template>
  <div class="modify-modal">
    <div class="modal-wrapper">
      <h4>회원정보 수정</h4>
      <!-- 학생 -->
      <div class="contents" v-if="user.userType === 'student'">
        <span
          ><label>별명</label> <input type="text" v-model="changeData"
        /></span>
      </div>

      <!--봉사자-->
      <div class="contents" v-if="user.userType === 'volunteer'">
        <label>자기소개</label> <textarea v-model="changeData"></textarea>
      </div>

      <!-- 부모 -->
      <div class="contents" v-if="user.userType === 'parent'">
        <span
          ><label>비밀번호</label> <input type="password" v-model="changeData"
        /></span>
        <span
          ><label>재입력</label> <input type="password" v-model="newPassword"
        /></span>
      </div>
      <div class="width100"><button @click="clickModify">수정</button></div>
    </div>
    <div class="confirm" v-if="isConfirmOpen">
      <div class="container">
        <h4>비밀번호가 일치하지 않습니다</h4>
        <button class="btn" @click="isConfirmOpen = false">확인</button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
export default {
  name: "ModifyModal",
  props: ["user"],
  setup(props, { emit }) {
    const isConfirmOpen = ref(false);
    const changeData = ref(
      props.user.userType === "student"
        ? props.user.nickname
        : props.user.userType === "volunteer"
        ? props.user.introduce
        : "",
    );

    const newPassword = ref("");

    const clickModify = () => {
      if (props.user.userType === "parent") {
        if (changeData.value !== newPassword.value) {
          isConfirmOpen.value = true;
          return;
        }
      }
      emit("modifyInfo", changeData.value);
    };

    return {
      changeData,
      newPassword,
      clickModify,
      isConfirmOpen,
    };
  },
};
</script>
<style scoped src="@/css/mypage.scss" lang="scss"></style>
