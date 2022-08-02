<template>
  <div v-if="user.status === 3" class="parent-profile">
    <div class="bookmark-area" @click="doActive">
      <!--부모일경우 북마크 추가-->
      <div class="children-bookmark active">
        <div class="children-name">애기화연</div>
        <img src="@/assets/profile.png" />
      </div>
      <div class="children-bookmark">
        <div class="children-name">아기화연</div>
        <img src="@/assets/profile.png" />
      </div>
      <div class="children-bookmark">
        <div class="children-name">애긔화연</div>
        <img src="@/assets/profile.png" />
      </div>
      <div class="children-bookmark">
        <div class="children-name">아긔화연</div>
        <img src="@/assets/profile.png" />
      </div>
      <div class="children-bookmark">
        <div class="children-name">애긩화연</div>
        <img src="@/assets/profile.png" />
      </div>
    </div>
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
        {{ user.nickname }}
      </div>
      <div class="label-value" v-else>{{ user.nickname }}</div>
    </div>

    <div class="person-info" v-if="user.status === 2">
      <label>자기소개</label>
      <div class="label-value introduce">{{ user.description }}</div>
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
    let bookmark;
    const state = reactive({
      childrenNo: 1,
    });
    onMounted(() => {
      bookmark = document.querySelectorAll(".children-name");
    });
    const doActive = (e) => {
      const parentBox = e.target.parentNode;
      if (!parentBox.classList.contains("active")) {
        parentBox.classList.add("active");

        // 애들프로필 바뀌는건 데이터 받고하는게 나을것가틈...
      }

      bookmark.forEach((item) => {
        if (item.innerText !== e.target.innerText) {
          const parent = item.parentNode;
          if (parent.classList.contains("active")) {
            parent.classList.remove("active");
          }
        }
      });
    };
    return {
      doActive,
      bookmark,
      state,
    };
  },
  props: ["user"],
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
