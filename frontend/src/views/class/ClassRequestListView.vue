<template>
  <div class="background">
    <HeaderNav />
    <div class="title">
      <h2>수업 요청해요</h2>
    </div>
    <div class="search-bar">
      <input type="text" placeholder="찾고 싶은 수업을 입력하세요." />
      <button class="search-btn">
        <i class="fa-solid fa-magnifying-glass"></i>
      </button>
    </div>
    <div class="filter">
      <input type="checkbox" name="unresolved" id="unresolved" />
      <label for="">미해결 요청만 보기</label>
    </div>
    <!-- 봉사자 아닐 경우에만 요청 글 작성 가능 -->
    <div v-if="user.status !== 2">
      <router-link :to="{ name: 'classrequestregist' }">
        <button class="request-register">작성하기</button></router-link
      >
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
              <router-link to="/class/requestdetail">
                {{ item.title }}
              </router-link>
            </td>
            <td>
              <button v-if="item.solved === true" class="solved status">
                <i class="fa-solid fa-circle"></i>해결
              </button>
              <button class="unsolved status" v-else>
                <i class="fa-solid fa-circle"></i>미해결
              </button>
            </td>
            <td>{{ item.time }}</td>
            <td>{{ item.views }}</td>
            <td>{{ item.want }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="pagination-wrapper">
      <div v-if="page.startPage > 1">
        <i class="fa-solid fa-angles-left"></i>
      </div>
      <div v-if="page.startPage > 1">
        <i class="fa-solid fa-angle-left"></i>
      </div>
      <div class="page" v-for="(item, index) in page.pageRange" :key="index">
        <router-link to="item">{{ item }}</router-link>
      </div>
      <div v-if="page.endPage < page.totalPage">
        <i class="fa-solid fa-angle-right"></i>
      </div>
      <div v-if="page.endPage + 2 < page.totalPage">
        <i class="fa-solid fa-angles-right"></i>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import { computed, reactive } from "vue";

export default {
  name: "ClassRequestListView",
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
    ];

    const page = reactive({
      startPage: 2, //화면에 보이는 페이지 시작 번호
      currentPage: 3, // 현재 페이지 번호
      totalCount: 50, // 총 게시글 수
      pageRange: computed(() => {
        let array = [];
        for (
          let index = page.currentPage - 2;
          index <= page.currentPage + 2;
          index++
        ) {
          array.push(index);
        }
        return array;
      }),
      endPage: 6, // 화면에 보이는 페이지 끝 번호
      totalPage: computed(() => {
        return page.totalCount / 5;
      }), // 총 페이지 수
    });

    // const pageRange = computed(() => {
    //   let array = [];
    //   for (
    //     let index = page.currentPage - 2;
    //     index <= page.currentPage + 2;
    //     index++
    //   ) {
    //     array.push(index);
    //   }
    //   return array;
    // });

    // const totalPage = computed(() => {
    //   return page.totalCount / 5;
    // });

    return {
      user,
      requests,
      page,
      // pageRange,
      // totalPage,
    };
  },
};
</script>

<style src="@/css/classRequestList.scss" lang="scss" scoped></style>
