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
      <input
        type="checkbox"
        name="unresolved"
        id="unresolved"
        v-model="unresolved"
        @change="check($event)"
      />
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
          <tr v-for="item in pages.listData" :key="item.num">
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
    <PaginationView
      :pageSetting="
        pageDataSetting(pages.total, pages.limit, pages.block, pages.page)
      "
      @paging="pagingMethod"
    />
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import PaginationView from "@/components/class/PaginationView.vue";
import { onMounted, reactive, ref } from "vue";

export default {
  name: "ClassRequestListView",
  components: {
    HeaderNav,
    PaginationView,
  },
  setup() {
    const user = {
      status: 1,
      subscribe: 0,
    };

    let requests = reactive([
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
    ]);

    const pages = reactive({
      listData: [], // 6개의 페이지가 담겨질 것
      page: 1, // 현재 어느 페이지에 있는지
      limit: 6, // 한 페이지당 나타날 게시글 개수
      block: 5, // 한번에 보여질 페이지 수
      total: requests.length, // 총 게시글 수
    });

    const pagingMethod = (p) => {
      pages.listData = requests.slice((p - 1) * pages.limit, p * pages.limit); //6개 잘라서 넣기
      pages.page = p;
      pageDataSetting(pages.total, pages.limit, pages.block, p);
    };

    const pageDataSetting = (total, limit, block, page) => {
      let totalPage = Math.ceil(total / limit); //  총 몇개의 페이지가 나와야 하는지
      let currentPage = page; //현재 페이지
      //이전 버튼
      const first =
        currentPage > 1 ? parseInt(currentPage, 10) - parseInt(1, 10) : null;
      // 다음버튼
      const end =
        totalPage !== currentPage
          ? parseInt(currentPage, 10) + parseInt(1, 10)
          : null;

      let flag = false; // 현재 묶여진 페이지안에 totalpage수가 포함되어 있는지 확인하기 위한 변수
      //5개씩 묶기
      let startIndex = (Math.ceil(currentPage / block) - 1) * block + 1;
      let endIndex =
        startIndex + block > totalPage ? totalPage : startIndex + block - 1;
      let list = [];
      for (let index = startIndex; index <= endIndex; index++) {
        if (index === totalPage) {
          flag = true;
        }
        list.push(index);
      }

      let startPage = currentPage > 5 ? 1 : null;
      totalPage = flag ? null : totalPage;
      return { first, end, list, currentPage, totalPage, startPage };
    };

    const unresolved = ref(false);

    const check = (e) => {
      if (e.target.checked === true) {
        let arr = [];
        for (const item of requests) {
          if (item.solved === false) {
            // 미해결만 배열에 담기
            arr.push(item);
          }
        }
        requests = arr;
        pages.total = requests.length;
        pagingMethod(pages.page);
      } else {
        console.log("false");
        // 나중에 다시 api에서 데이터 불러올것 지금 임시로 넣어놈
        requests = [
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
        pages.total = requests.length;
        pagingMethod(pages.page);
      }
    };
    onMounted(() => {
      pagingMethod(pages.page);
    });

    return {
      user,
      requests,
      pages,
      unresolved,
      pagingMethod,
      pageDataSetting,
      check,
    };
  },
};
</script>

<style src="@/css/classRequestList.scss" lang="scss" scoped></style>
