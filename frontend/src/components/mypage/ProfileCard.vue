<template>
  <!--만약 유저가 학부모일 경우-->
  <div v-if="user.status === 3" class="parent-profile">
    <div class="left-box profile">
      <i class="fa-solid fa-ellipsis-vertical profile-ellipse">
        <ul>
          <li>
            <i class="fa-solid fa-pen-to-square"></i>
            &nbsp;<span>비밀번호 변경</span>
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
        <div class="label-value">이화연 바보</div>
      </div>

      <div class="person-info">
        <label>별명</label>
        <div class="label-value">애기화연</div>
      </div>
    </div>
    <div class="bookmark-area">
      <!--부모일경우 북마크 추가-->
      <div class="children-bookmark active" @click="doActive">
        <img src="@/assets/profile.png" />
        <div>애기화연</div>
      </div>
      <div class="children-bookmark" @click="doActive">
        <img src="@/assets/profile.png" />
        <div>애기화연</div>
      </div>
    </div>
  </div>
  <!--유저가 봉사자이거나 학생일 경우-->
  <div class="left-box profile" v-else>
    <i class="fa-solid fa-ellipsis-vertical profile-ellipse">
      <ul>
        <li v-if="user.status === 3">
          <i class="fa-solid fa-pen-to-square"></i>
          &nbsp;<span>비밀번호 변경</span>
        </li>
        <li v-else>
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

    <div class="person-info" v-if="user.status === 2">
      <label>자기소개</label>
      <div class="label-value introduce">자기소개 글</div>
    </div>
    <div class="person-info" v-else>
      <label>별명</label>
      <div class="label-value">애기화연</div>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted } from "vue";

export default {
  setup() {
    const children = reactive({
      childrenNo: 0,
    });
    let bookmark;
    onMounted(() => {
      bookmark = document.querySelectorAll(".children-bookmark");
    });
    const doActive = (e) => {
      // if (!e.target.classList.contains("active")) {
      //   console.log("애아아");
      //   e.target.classList.add("active");
      // }
      if (!e.target.classList.contains("active")) {
        e.target.classList.add("active");
      } else {
        e.target.classList.remove("active");
      }
    };
    const user = {
      status: 3,
      subscribe: 0,
    };
    return {
      user,
      children,
      doActive,
      bookmark,
    };
  },
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
