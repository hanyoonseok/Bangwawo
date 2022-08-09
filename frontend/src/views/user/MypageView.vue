<template>
  <div class="mypage-background">
    <HeaderNav />
    <div class="mypage-container">
      <div class="profile-info">
        <profile-card
          :user="userInfo"
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
          :user="userInfo"
          :isEnd="state.isEnd"
          :endClass="endClass"
          :scheduledClass="scheduledClass"
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
      :user="userInfo"
      @modifyInfo="modifyInfo"
    />
  </div>
</template>

<script>
import moment from "moment";
import axios from "axios";
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

    let navItem;
    let isModifyOpen = ref(false);
    const isCharacterModalOpen = ref(false);
    const children = ref(null);
    let userInfo = ref(null);

    const getUserInfo = async () => {
      userInfo.value = { ...JSON.parse(localStorage.getItem("user")) };
      const userType = userInfo.value.userType;

      if (userType === "parent") {
        const response = await axios.get(
          `${process.env.VUE_APP_API_URL}/parent/${userInfo.value.email}`,
        );
        children.value = response.data.childs;
      }
    };
    getUserInfo();

    const classes = [
      {
        classTeacher: "김오리",
        classStartTime: "2022-07-20 13:00:00",
        classEndTime: "22-07-20 15:00:00",
        classTitle: "오리선생의 초1 수학 수업 입니당~",
        classContent:
          "김오리의 수학수업이다. 김오리의 수학쉅이다. 김오리 수학 수업 김올",
        classOpen: true,
        classStatus: "입장가능",
        classPeople: "12/45",
      },
      {
        classTeacher: "김오리",
        classStartTime: "2022-07-20 13:00:00",
        classEndTime: "22-07-20 15:00:00",
        classTitle: "오리선생의 초1 수학 수업 입니당~",
        classContent:
          "김오리의 수학수업이다. 김오리의 수학쉅이다. 김오리 수학 수업 김올",
        classOpen: true,
        classStatus: "입장대기",
        classPeople: "12/45",
      },
      {
        classTeacher: "김오리",
        classStartTime: "2022-07-20 13:00:00",
        classEndTime: "22-07-20 15:00:00",
        classTitle: "오리선생의 초1 수학 수업 입니당~",
        classContent:
          "김오리의 수학수업이다. 김오리의 수학쉅이다. 김오리 수학 수업 김올",
        classOpen: true,
        classStatus: "입장대기",
        classPeople: "12/45",
      },
      {
        classTeacher: "김오리",
        classStartTime: "2022-07-20 13:00:00",
        classEndTime: "22-07-20 15:00:00",
        classTitle: "오리선생의 초1 수학 수업 입니당~",
        classContent:
          "김오리의 수학수업이다. 김오리의 수학쉅이다. 김오리 수학 수업 김올",
        classOpen: true,
        classStatus: "입장대기",
        classPeople: "12/45",
      },
      {
        classTeacher: "김오리",
        classStartTime: "2022-07-20 13:00:00",
        classEndTime: "22-07-20 15:00:00",
        classTitle: "오리선생의 초1 수학 수업 입니당~",
        classContent:
          "김오리의 수학수업이다. 김오리의 수학쉅이다. 김오리 수학 수업 김올",
        classOpen: true,
        classStatus: "입장대기",
        classPeople: "12/45",
      },
      {
        classTeacher: "김오리",
        classTitle: "오리선생의 초1 수학 수업 입니당~",
        classStartTime: "2022-08-20 13:00:00",
        classEndTime: "22-08-20 15:00:00",
        classContent:
          "김오리의 수학수업이다. 김오리의 수학쉅이다. 김오리 수학 수업 김올",
        classOpen: true,
        classStatus: "종료",
        classPeople: "12/45",
      },
      {
        classTeacher: "김오리",
        classTitle: "오리선생의 초1 수학 수업 입니당~",
        classStartTime: "2022-08-20 13:00:00",
        classEndTime: "22-08-20 15:00:00",
        classContent:
          "김오리의 수학수업이다. 김오리의 수학쉅이다. 김오리 수학 수업 김올",
        classOpen: true,
        classStatus: "종료",
        classPeople: "12/45",
      },
      {
        classTeacher: "김오리",
        classTitle: "오리선생의 초1 수학 수업 입니당~",
        classStartTime: "2022-08-20 13:00:00",
        classEndTime: "22-08-20 15:00:00",
        classContent:
          "김오리의 수학수업이다. 김오리의 수학쉅이다. 김오리 수학 수업 김올",
        classOpen: true,
        classStatus: "종료",
        classPeople: "12/45",
      },
      {
        classTeacher: "김오리",
        classTitle: "오리선생의 초1 수학 수업 입니당~",
        classStartTime: "2022-08-20 13:00:00",
        classEndTime: "22-08-20 15:00:00",
        classContent:
          "김오리의 수학수업이다. 김오리의 수학쉅이다. 김오리 수학 수업 김올",
        classOpen: true,
        classStatus: "종료",
        classPeople: "12/45",
      },
    ];
    let endClass = [];
    let scheduledClass = [];
    // 나중에 수정해야할것같은데 일단 해놓음.
    // 종료된거랑 종료 전이랑 어떻게 나눌지...
    // 아마 데이터 받아오면 mutation? 어디지 암튼 거기서 나눠줘야하나?

    const division = () => {
      classes.forEach((e) => {
        if (
          moment(e.classStartTime).isBefore(
            moment().format("YYYY-MM-DD HH:mm:ss"),
          )
        ) {
          scheduledClass.push(e);
        } else {
          endClass.push(e);
        }
      });
    };
    division();
    const state = reactive({
      isEnd: false,
    });
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
      if (userInfo.value.userType === "student") {
        await axios
          .put(`${process.env.VUE_APP_API_URL}/student`, {
            sId: userInfo.value.sid,
            nickname: changeData,
          })
          .then((res) => {
            console.log(res);
          })
          .catch((err) => {
            console.log(err.message);
          });
        userInfo.value.nickname = changeData;
      } else if (userInfo.value.userType === "volunteer") {
        await axios
          .put(`${process.env.VUE_APP_API_URL}/volunteer`, {
            vId: userInfo.value.vid,
            introduce: changeData,
          })
          .then((res) => {
            console.log(res);
          })
          .catch((err) => {
            console.log(err.message);
          });
        userInfo.value.introduce = changeData;
      } else if (userInfo.value.userType === "parent") {
        await axios
          .put(`${process.env.VUE_APP_API_URL}/parent`, {
            email: userInfo.value.email,
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
      division,
      endClass,
      scheduledClass,
      doActive,
      openCharacterModal,
      closeCharacterModal,
      isModifyOpen,
      toggleModifyModal,
      modifyInfo,
      children,
      userInfo,
    };
  },
};
</script>
<style scoped src="@/css/mypage.scss" lang="scss"></style>
