<template>
  <div v-if="user.status === 3" class="parent-profile">
    <div class="bookmark-area" @click="changeChildTab">
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
      <i
        class="fa-solid fa-ellipsis-vertical profile-ellipse"
        @click="toggleModal"
      >
        <ul v-if="isModalOpen">
          <li @click="toggleModifyModal">
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
    <i
      class="fa-solid fa-ellipsis-vertical profile-ellipse"
      @click.stop="toggleModal"
    >
      <ul v-if="isModalOpen">
        <li @click="toggleModifyModal">
          <i class="fa-solid fa-pen-to-square"></i>
          &nbsp;<span>정보수정</span>
        </li>

        <li>
          <i class="fa-solid fa-arrow-right-from-bracket"></i>
          &nbsp;<span>회원탈퇴</span>
        </li>
      </ul>
    </i>
    <div class="img-box" @click="openCharacterModal">
      <ProfileCanvas />
      <!-- <img src="@/assets/profile.png" /> -->
      <div class="img-hover-box">캐릭터 편집</div>
    </div>
    <div class="person-info">
      <label>이름</label>
      <div class="label-value volunteer-value" v-if="user.status === 2">
        {{ user.name }}
      </div>
      <div class="label-value" v-else>{{ user.name }}</div>
    </div>

    <div class="person-info" v-if="user.status === 2">
      <label>자기소개</label>
      <div class="label-value introduce">
        {{ user.description }}
      </div>
      <textarea
        v-if="isModifyOpen"
        v-model="curDescription"
        class="modify-textarea"
      ></textarea
      ><i class="fa-solid fa-check" v-if="isModifyOpen"></i>
    </div>
    <div class="person-info" v-else @submit.prevent="submitModify">
      <label>별명</label>
      <div class="label-value">{{ user.nickname }}</div>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted } from "vue";
import ProfileCanvas from "@/components/mypage/ProfileCanvas.vue";
import { ref } from "vue";
export default {
  props: ["user", "toggleModifyModal"],
  emits: ["open-character-modal"],
  components: { ProfileCanvas },
  setup(props, { emit }) {
    let bookmark;
    let isModalOpen = ref(false);
    let isModifyOpen = ref(false);

    const toggleModal = () => {
      isModalOpen.value = !isModalOpen.value;
    };
    const state = reactive({
      childrenNo: 0,
    });
    onMounted(() => {
      if (props.user.status === 3) {
        bookmark = document.querySelectorAll(".children-name");
        bookmark[0].parentNode.classList.add("active");
      }
    });
    const changeChildTab = (e) => {
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

    const openCharacterModal = () => {
      emit("open-character-modal");
    };

    return {
      changeChildTab,
      toggleModal,
      isModalOpen,
      bookmark,
      state,
      openCharacterModal,
      isModifyOpen,
    };
  },
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
