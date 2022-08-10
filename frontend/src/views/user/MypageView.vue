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
        ></profile-card>
        <div class="left-box calendar">
          <h4>수업 일정</h4>
          <calendar-area />
        </div>
      </div>
      <div class="lecture-container">
        <ul class="lecture-nav">
          <li class="nav-item active" @click="doActive">예정된 수업</li>
          <li class="nav-item" @click="doActive">종료된 수업</li>
        </ul>
        <lecture-area
          :user="state.userInfo"
          :isEnd="state.isEnd"
          :endClass="endClass"
          :comingClass="comingClass"
        ></lecture-area>
      </div>
    </div>
    <transition name="slide-up">
      <CharacterModal
        v-if="isCharacterModalOpen"
        @close-character-modal="closeCharacterModal"
    /></transition>
    <ModifyModal
      v-if="isModifyOpen"
      :user="state.userInfo"
      @modifyInfo="modifyInfo"
    />
  </div>
</template>

<script>
import moment from "moment";
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
      isEnd: false,
    });
    let classes = ref([]);
    let endClass = ref([]);
    let comingClass = ref([]);
    let navItem;
    let isModifyOpen = ref(false);

    const getUserInfo = () => {
      if (state.userType === "parent") {
        store
          .dispatch("root/getChildren", state.userInfo.email)
          .then((res) => (children.value = res.data.childs));
      }
    };
    getUserInfo();

    const getUserClasses = () => {
      if (state.userType === "parent") {
        console.log("hi");
      } else if (state.userType === "student") {
        store
          .dispatch("root/getStudentClasses", state.userInfo.sid)
          .then((res) => {
            console.log(res);
            classes.value = res.data;
          });

        classes.value.forEach((e) => {
          if (
            moment(e.classStartTime).isBefore(
              moment().format("YYYY-MM-DD HH:mm:ss"),
            )
          ) {
            comingClass.push(e);
          } else {
            endClass.push(e);
          }
        });
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

    // 나중에 수정해야할것같은데 일단 해놓음.
    // 종료된거랑 종료 전이랑 어떻게 나눌지...
    // 아마 데이터 받아오면 mutation? 어디지 암튼 거기서 나눠줘야하나?

    const doActive = (e) => {
      if (!e.target.classList.contains("active")) {
        e.target.classList.add("active");
      }
      if (e.target.innerText === "예정된 수업") {
        if (state.isEnd) {
          state.isEnd = !state.isEnd;
        }
        navItem[1].classList.remove("active");
      } else {
        //만약 종료된수업을 눌렀다면!
        if (!state.isEnd) {
          state.isEnd = !state.isEnd;
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
            sId: state.userInfo.sid,
            nickname: changeData,
          })
          .then((res) => {
            store.commit("root/setModifiedStudentInfo", changeData);
            console.log(res);
          })
          .catch((err) => {
            console.log(err.message);
          });
      } else if (state.userType === "volunteer") {
        store
          .dispatch("root/modifyUserInfo", {
            userType: "volunteer",
            vId: state.userInfo.vid,
            introduce: changeData,
          })
          .then((res) => {
            store.commit("root/setModifiedVolunteerInfo", changeData);
            console.log(res);
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

    return {
      isCharacterModalOpen,
      state,
      navItem,
      classes,
      endClass,
      comingClass,
      doActive,
      openCharacterModal,
      closeCharacterModal,
      isModifyOpen,
      toggleModifyModal,
      modifyInfo,
      children,
    };
  },
};
</script>
<style scoped src="@/css/mypage.scss" lang="scss"></style>
