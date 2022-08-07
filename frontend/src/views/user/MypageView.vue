<template>
  <div class="mypage-background">
    <HeaderNav />
    <div class="mypage-container">
      <div class="profile-info">
        <profile-card
          :user="user"
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
          <li
            :class="{ 'nav-item': true, active: !state.isEndedMenuActive }"
            @click="doMenuActive"
          >
            예정된 수업
          </li>
          <li
            :class="{ 'nav-item': true, active: state.isEndedMenuActive }"
            @click="doMenuActive"
          >
            종료된 수업
          </li>
        </ul>
        <lecture-area
          :user="user"
          :isEnd="state.isEndedMenuActive"
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
    <ModifyModal v-if="isModifyOpen" :user="user" @modifyInfo="modifyInfo" />
  </div>
</template>

<script>
import moment from "moment";
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
    let navItem;
    onMounted(() => {
      navItem = document.querySelectorAll(".nav-item");
    });

    let isModifyOpen = ref(false);
    const isCharacterModalOpen = ref(false);

    const user = reactive({
      name: "이화연바보",
      nickname: "애기하연",
      description: "자기소개입니다",
      status: 1, //1학생 2봉사자 3부모
      subscribe: 0,
      password: "abcdefg",
      children: [
        {
          name: "일화연",
          nickname: "애긔화연",
          description: "자기소개입니다",
          status: 1,
          subscribe: 0,
        },
        {
          name: "이화연",
          nickname: "애기화연애긔",
          description: "자기소개입니다",
          status: 1,
          subscribe: 0,
        },
        {
          name: "삼화연",
          nickname: "애긩화연",
          description: "자기소개입니다",
          status: 1,
          subscribe: 0,
        },
        {
          name: "사화연",
          nickname: "아긩화연",
          description: "자기소개입니다",
          status: 1,
          subscribe: 0,
        },
      ],
    });
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
      isEndedMenuActive: false,
    });
    const doMenuActive = (e) => {
      if (e.target.innerText === "예정된 수업") {
        state.isEndedMenuActive = false;
      } else {
        state.isEndedMenuActive = true;
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

    const modifyInfo = (changeData) => {
      if (user.status === 1) user.nickname = changeData;
      else if (user.status === 2) user.description = changeData;
      else if (user.status === 3) user.password = changeData;
      console.log(user);
      isModifyOpen.value = false;
    };

    return {
      user,
      isCharacterModalOpen,
      state,
      navItem,
      classes,
      division,
      endClass,
      scheduledClass,
      doMenuActive,
      openCharacterModal,
      closeCharacterModal,
      isModifyOpen,
      toggleModifyModal,
      modifyInfo,
    };
  },
};
</script>
<style scoped src="@/css/mypage.scss" lang="scss"></style>
