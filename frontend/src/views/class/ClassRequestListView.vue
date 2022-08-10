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
    <div v-if="userInfo.userType !== 'volunteer'">
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
            <td>{{ item.rid }}</td>
            <td>
              <span v-if="item.student">{{ item.student.nickname }}</span>
            </td>
            <td>
              <router-link :to="`requestdetail/${item.rid}`">
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
            <td>{{ item.count }}</td>
            <td>{{ item.likes }}</td>
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
import { reactive, ref, onMounted } from "vue";
import axios from "axios";

export default {
  name: "ClassRequestListView",
  components: {
    HeaderNav,
    PaginationView,
  },
  setup() {
    let userInfo = ref(null);
    const getUserInfo = async () => {
      userInfo.value = JSON.parse(localStorage.getItem("user"));
      // userInfo.userType = "student";
    };
    getUserInfo();
    const state = reactive({
      requestList: [],
      totalRequest: 0,
    });
    const pages = reactive({
      listData: [], // 6개의 페이지가 담겨질 것
      page: 1, // 현재 어느 페이지에 있는지
      limit: 6, // 한 페이지당 나타날 게시글 개수
      block: 5, // 한번에 보여질 페이지 수
      total: 0, // 총 게시글 수
    });
    const getRequestList = async (page) => {
      axios
        .get(`${process.env.VUE_APP_API_URL}/request?scrollcnt=${page}`)
        .then((response) => {
          const data = response.data.requestsList;
          state.requestList = data.content;
          pages.total = data.totalElements;
          pages.listData = state.requestList;
          console.log(data.content[1].student);
        });
    };
    onMounted(() => {
      pagingMethod(pages.page);
    });

    const pagingMethod = (page) => {
      pages.page = page;
      // 여기서 새로 데이터를 받아와야 함.
      getRequestList(pages.page);
      pageDataSetting(pages.total, pages.limit, pages.block, page);
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

      //5개씩 묶기
      let startIndex = (Math.ceil(currentPage / block) - 1) * block + 1;
      let endIndex =
        startIndex + block > totalPage ? totalPage : startIndex + block - 1;
      let list = [];
      for (let index = startIndex; index <= endIndex; index++) {
        list.push(index);
      }

      let startPage = 1;

      console.log(list);
      return { first, end, list, currentPage, totalPage, startPage };
    };

    const unresolved = ref(false);

    const check = (e) => {
      if (e.target.checked === true) {
        let arr = [];
        for (const item of state.requestList) {
          if (item.solved === false) {
            // 미해결만 배열에 담기
            arr.push(item);
          }
        }
        state.requestList = arr;
        pages.total = state.requestList.length;
        pagingMethod(pages.page);
      } else {
        console.log("false");
        // 나중에 다시 api에서 데이터 불러올것 지금 임시로 넣어놈
        pages.total = state.requestList.length;
        pagingMethod(pages.page);
      }
    };
    return {
      pages,
      unresolved,
      pagingMethod,
      getRequestList,
      pageDataSetting,
      check,
      userInfo,
      getUserInfo,
      state,
    };
  },
};
</script>

<style src="@/css/classRequestList.scss" lang="scss" scoped></style>
