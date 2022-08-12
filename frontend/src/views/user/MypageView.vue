<template>
  <div class="mypage-background">
    <HeaderNav />
    <div class="mypage-container">
      <div class="profile-info">
        <profile-card
          :user="state.userInfo"
          :children="children"
          :toggleModifyModal="toggleModifyModal"
          @open-character-modal="openCharacterModal"
          @selectChild="selectChild"
        ></profile-card>
        <div class="left-box calendar">
          <h4>수업 일정</h4>
          <calendar-area />
        </div>
      </div>
      <div class="lecture-container">
        <ul class="lecture-nav">
          <li
            :class="{ 'nav-item': true, active: !state.isEndTab }"
            @click="doMenuActive"
          >
            예정된 수업
          </li>
          <li
            :class="{ 'nav-item': true, active: state.isEndTab }"
            @click="doMenuActive"
          >
            종료된 수업
          </li>
        </ul>
        <lecture-area
          :user="state.userInfo"
          :isEndTab="state.isEndTab"
          :endClass="endClass"
          :comingClass="comingClass"
        ></lecture-area>
      </div>
    </div>
    <transition name="slide-up">
      <CharacterModal
        v-if="isCharacterModalOpen"
        @close-character-modal="closeCharacterModal"
        :user="state.userInfo"
    /></transition>
    <ModifyModal
      v-if="isModifyOpen"
      :user="state.userInfo"
      @modifyInfo="modifyInfo"
    />
  </div>
</template>

<script>
// import moment from "moment";
import { useStore } from "vuex";
import CalendarArea from "@/components/mypage/CalendarArea.vue";
import ProfileCard from "@/components/mypage/ProfileCard.vue";
import LectureArea from "@/components/mypage/LectureArea.vue";
import HeaderNav from "@/components/HeaderNav.vue";
import CharacterModal from "@/components/mypage/CharacterModal.vue";
import ModifyModal from "@/components/mypage/ModifyModal.vue";

import { ref } from "@vue/reactivity";
import { reactive, onMounted } from "vue";
export default {
  components: {
    CalendarArea,
    HeaderNav,
    ProfileCard,
    CharacterModal,
    LectureArea,
    ModifyModal,
  },
  setup() {
    onMounted(() => {
      navItem = document.querySelectorAll(".nav-item");
    });
    const store = useStore();
    const isCharacterModalOpen = ref(false);
    const children = ref(null);
    let state = reactive({
      userInfo: store.state.root.user, //computed(() => store.getters["root/userInfo"]),
      userType: store.state.root.user.userType, //computed(() => store.getters["root/getUserType"]),
      isEndTab: false,
    });
    let classes = ref([]);
    let endClass = ref([]);
    let comingClass = ref([]);
    let navItem;
    let isModifyOpen = ref(false);

    const getUserInfo = () => {
      if (state.userType === "parent") {
        store.dispatch("root/getChildren", state.userInfo.email).then((res) => {
          children.value = res.data.childs;
          if (children.value.length > 0) {
            getStudentClasses(children.value[0].sid);
          }
          console.log(children.value);
        });
      }
    };
    getUserInfo();

    const getStudentClasses = async (sid) => {
      await store.dispatch("root/getStudentClasses", sid).then((res) => {
        console.log(res);
        classes.value = res.data;

        const tempComingClass = [];
        const tempEndClass = [];

        classes.value.forEach((e) => {
          if (e.classes.state === 0 || e.classes.state === 1) {
            tempComingClass.push(e.classes);
          } else if (e.classes.state === 2) {
            tempEndClass.push(e.classes);
          }
        });
        comingClass.value = tempComingClass;
        endClass.value = tempEndClass;
      });
    };

    const getUserClasses = () => {
      if (state.userType === "student") {
        getStudentClasses(state.userInfo.sid);
      } else if (state.userType === "volunteer") {
        store
          .dispatch("root/getEndedClasses", state.userInfo.vid)
          .then((res) => {
            endClass.value = res.data;
            console.log(endClass.value);
          })
          .catch((err) => console.log(err.message));
        store
          .dispatch("root/getComingClasses", state.userInfo.vid)
          .then((res) => {
            comingClass.value = res.data;
          })
          .catch((err) => console.log(err.message));
      }
    };
    getUserClasses();

    const doMenuActive = (e) => {
      if (!e.target.classList.contains("active")) {
        e.target.classList.add("active");
      }
      if (e.target.innerText === "예정된 수업") {
        if (state.isEndTab) {
          state.isEndTab = !state.isEndTab;
        }
        navItem[1].classList.remove("active");
      } else {
        //만약 종료된수업을 눌렀다면!
        if (!state.isEndTab) {
          state.isEndTab = !state.isEndTab;
        }
        navItem[0].classList.remove("active");
      }
    };

    const openCharacterModal = () => {
      isCharacterModalOpen.value = true;
    };

    const closeCharacterModal = () => {
      isCharacterModalOpen.value = false;
    };

    const toggleModifyModal = () => {
      isModifyOpen.value = !isModifyOpen.value;
    };

    const modifyInfo = async (changeData) => {
      console.log(changeData);
      if (state.userType === "student") {
        store
          .dispatch("root/modifyUserInfo", {
            userType: "student",
            id: state.userInfo.sid,
            nickname: changeData,
          })
          .then(() => {
            store.commit("root/setModifiedStudentInfo", changeData);
          })
          .catch((err) => {
            console.log(err.message);
          });
      } else if (state.userType === "volunteer") {
        store
          .dispatch("root/modifyUserInfo", {
            userType: "volunteer",
            id: state.userInfo.vid,
            introduce: changeData,
          })
          .then(() => {
            store.commit("root/setModifiedVolunteerInfo", changeData);
          })
          .catch((err) => {
            console.log(err.message);
          });
      } else if (state.userInfo.userType === "parent") {
        store
          .dispatch("root/modifyUserInfo", {
            userType: "parent",
            email: state.userInfo.email,
            password: changeData,
          })
          .then((res) => {
            console.log(res);
          })
          .catch((err) => {
            console.log(err.message);
          });
      }
      isModifyOpen.value = false;
    };

    const selectChild = (child) => {
      getStudentClasses(child.sid);
    };

    return {
      isCharacterModalOpen,
      state,
      navItem,
      classes,
      endClass,
      comingClass,
      doMenuActive,
      openCharacterModal,
      closeCharacterModal,
      isModifyOpen,
      toggleModifyModal,
      modifyInfo,
      children,
      selectChild,
    };
  },
};
</script>
<style scoped src="@/css/mypage.scss" lang="scss"></style>
