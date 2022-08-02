<template>
  <div class="mypage-background">
    <HeaderNav />
    <div class="mypage-container">
      <div class="profile-info">
        <profile-card :user="user"></profile-card>
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
          :user="user"
          :isEnd="state.isEnd"
          :endClass="endClass"
          :scheduledClass="scheduledClass"
        ></lecture-area>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import CalendarArea from "@/components/mypage/CalendarArea.vue";
import ProfileCard from "@/components/mypage/ProfileCard.vue";
import LectureArea from "@/components/mypage/LectureArea.vue";
import HeaderNav from "@/components/HeaderNav.vue";
import { reactive, onMounted } from "vue";
export default {
  components: {
    CalendarArea,
    HeaderNav,
    ProfileCard,
    LectureArea,
  },
  setup() {
    let navItem;
    onMounted(() => {
      navItem = document.querySelectorAll(".nav-item");
    });
    const user = {
      name: "이화연바보",
      nickname: "애기하연",
      description: "자기소개입니다",
      status: 3,
      subscribe: 0,
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
    };
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

    return {
      user,
      state,
      navItem,
      classes,
      division,
      endClass,
      scheduledClass,
      doActive,
    };
  },
};
</script>
<style scoped src="@/css/mypage.scss" lang="scss"></style>
