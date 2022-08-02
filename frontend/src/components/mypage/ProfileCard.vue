<template>
  <div v-if="user.status === 3" class="parent-profile">
    <div class="bookmark-area" @click="doActive">
      <!--부모일경우 북마크 추가-->
      <div
        class="children-bookmark"
        v-for="(children, index) in user.children"
        :key="index"
      >
        <div class="children-name">{{ children.name }}</div>
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
        <div class="label-value">
          {{ user.children[state.childrenNo].name }}
        </div>
      </div>

      <div class="person-info">
        <label>별명</label>
        <div class="label-value">
          {{ user.children[state.childrenNo].nickname }}
        </div>
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
  props: ["user"],
  setup(props) {
    let bookmark;
    const state = reactive({
      childrenNo: 0,
    });
    onMounted(() => {
      if (props.user.status === 3) {
        bookmark = document.querySelectorAll(".children-name");
        bookmark[0].parentNode.classList.add("active");
      }
    });
    const doActive = (e) => {
      const parentBox = e.target.parentNode;
      if (!parentBox.classList.contains("active")) {
        parentBox.classList.add("active");
        for (var i = 0; i < bookmark.length; ++i) {
          if (e.target.innerText === bookmark[i].innerText) {
            state.childrenNo = i;
            break;
          }
        }
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
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
