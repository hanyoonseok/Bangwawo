<template>
  <div v-if="user.userType === 'parent'" class="parent-profile">
    <div class="bookmark-area">
      <!--부모일경우 북마크 추가-->
      <div
        class="children-bookmark"
        v-for="(child, index) in children"
        :key="index"
        @click="selectChild(child)"
      >
        <div class="children-name">{{ child.nickname }}</div>
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
        </ul></i
      >
      <div>
        <img src="@/assets/profile.png" />
      </div>

      <div class="person-info">
        <label>별명</label>
        <div class="label-value" v-text="selectedChild"></div>
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

        <li @click="deleteUser">
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

    <div class="person-info" v-if="user.userType === 'volunteer'">
      <label>자기소개</label>
      <div class="label-value introduce">
        {{ user.introduce }}
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
import ProfileCanvas from "@/components/mypage/ProfileCanvas.vue";
import { ref, watch } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  props: ["user", "toggleModifyModal", "children"],
  emits: ["open-character-modal"],
  components: { ProfileCanvas },
  setup(props, { emit }) {
    let isModalOpen = ref(false);
    let isModifyOpen = ref(false);
    let selectedChild = ref(null);
    const store = useStore();
    const router = useRouter();

    watch(
      () => props.children,
      (cur) => {
        selectedChild.value = props.children ? cur[0].nickname : "";
      },
      { deep: true },
    );

    const toggleModal = () => {
      isModalOpen.value = !isModalOpen.value;
    };

    const selectChild = (child) => {
      selectedChild.value = child.nickname;
      emit("selectChild", child);
    };

    const openCharacterModal = () => {
      emit("open-character-modal");
    };

    const deleteUser = () => {
      store.dispatch("root/deleteUser", props.user);
      store
        .dispatch("root/deleteKakaoInfo", props.user.accessToken)
        .then(() => {
          store.commit("root/logoutUser");
          router.push("/");
        });
    };

    return {
      toggleModal,
      isModalOpen,
      openCharacterModal,
      isModifyOpen,
      selectedChild,
      selectChild,
      deleteUser,
    };
  },
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
