<template>
  <div class="background">
    <HeaderNav />
    <div class="title">
      <h2>수업 요청해요</h2>
    </div>
    <div class="search-bar">
      <input type="text" placeholder="찾고 싶은 수업을 입력하세요." />
      <button class="searchBtn">
        <i class="fa-solid fa-magnifying-glass"></i>
      </button>
    </div>
    <div class="filter">
      <input type="checkbox" name="unresolved" id="unresolved" />
      <label for="">미해결 요청만 보기</label>
    </div>
    <!-- 봉사자 아닐 경우에만 요청 글 작성 가능 -->
    <div v-if="user.status !== 2">
      <button class="request-register">작성하기</button>
    </div>
    <div class="list">
      <div class="table-header"></div>
      <table>
        <thead>
          <tr>
            <th>글 번호</th>
            <th>작성자</th>
            <th>제목</th>
            <th>해결</th>
            <th>작성시간</th>
            <th>조회수</th>
            <th>원해요</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in requests" :key="item.num">
            <td>{{ item.num }}</td>
            <td>{{ item.writer }}</td>
            <td>
              <router-link to="class/request/detail">
                {{ item.title }}
              </router-link>
            </td>
            <td>
              <p v-if="item.solved === true" class="solved status">
                <i class="fa-solid fa-circle"></i>해결
              </p>
              <p class="unsolved status" v-else>
                <i class="fa-solid fa-circle"></i>미해결
              </p>
            </td>
            <td>{{ item.time }}</td>
            <td>{{ item.views }}</td>
            <td>{{ item.want }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <a class="up" @click="top">
      <i class="fa-solid fa-arrow-up"></i>
    </a>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  name: "ClassRequestView",
  components: {
    HeaderNav,
  },
  setup() {
    const user = {
      status: 1,
      subscribe: 0,
    };

    const requests = [
      {
        num: 1,
        writer: "앵그리윤석",
        title:
          "앵그리윤석이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기진호",
        title:
          "애기진호는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: false,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기다빈",
        title:
          "애기다빈이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기수콩(김)",
        title:
          "애기수콩이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: false,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기화연",
        title:
          "애기화연이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기수콩(정)",
        title:
          "애기수콩이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "앵그리윤석",
        title:
          "앵그리윤석이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기진호",
        title:
          "애기진호는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: false,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기다빈",
        title:
          "애기다빈이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기수콩(김)",
        title:
          "애기수콩이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: false,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기화연",
        title:
          "애기화연이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기수콩(정)",
        title:
          "애기수콩이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "앵그리윤석",
        title:
          "앵그리윤석이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기진호",
        title:
          "애기진호는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: false,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기다빈",
        title:
          "애기다빈이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기수콩(김)",
        title:
          "애기수콩이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: false,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기화연",
        title:
          "애기화연이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
      {
        num: 1,
        writer: "애기수콩(정)",
        title:
          "애기수콩이는 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다 이러이러한 수업을 듣고 싶슴다",
        solved: true,
        time: "14:55",
        views: 1,
        want: 1,
      },
    ];

    const top = () => {
      window.scrollTo({ top: 0, behavior: "smooth" });
    };

    return {
      user,
      requests,
      top,
    };
  },
};
</script>

<style src="@/css/classRequest.scss" lang="scss" scoped></style>
