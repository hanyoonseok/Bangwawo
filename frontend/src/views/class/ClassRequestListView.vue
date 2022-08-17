<template>
  <div class="background">
    <HeaderNav />
    <div class="title">
      <h2>수업 요청해요</h2>
    </div>
    <div class="search-bar">
      <input
        type="text"
        placeholder="찾고 싶은 수업을 입력하세요."
        @keyup="searchRequest"
        @input="changeKeyword"
      />
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
        @change="unSolvedFilter($event)"
      />
      <label>미해결 요청만 보기</label>
    </div>
    <div v-if="state.userType === 'student'">
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
            <td>
              {{ item.createDate[0] }}.{{ item.createDate[1] }}.{{
                item.createDate[2]
              }}
            </td>
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
import { reactive, ref, onMounted, watch } from "vue";
import axios from "axios";
import { useStore } from "vuex";

export default {
  name: "ClassRequestListView",
  components: {
    HeaderNav,
    PaginationView,
  },
  setup() {
    const store = useStore();
    const state = reactive({
      requestList: [],
      isUnsolvedFilter: false,
      userInfo: store.state.root.user,
      userType: store.state.root.user.userType,
      regDate: undefined,
      totalRequest: 0,
    });

    const keyword = ref("");
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
          console.log(data);
          state.requestList = data.content;
          pages.total = data.totalElements;
          pages.listData = state.requestList;
        });
    };
    onMounted(() => {
      pagingMethod(pages.page);
      searchRequest(pages.page);
    });
    const pagingMethod = (page) => {
      pages.page = page;
      // 여기서 새로 데이터를 받아와야 함.
      if (keyword.value === "" && !state.isUnsolvedFilter) {
        getRequestList(pages.page);
        pages.total = state.requestList.length;
      } else {
        pages.listData = state.requestList.slice(
          (page - 1) * pages.limit,
          page * pages.limit,
        ); //6개 잘라서 넣기
      }
      console.log("보여드림~", state.requestList);

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
    const unSolvedFilter = async (e) => {
      if (e.target.checked === true) {
        await axios
          .get(`${process.env.VUE_APP_API_URL}/request/unfind`)
          .then((response) => {
            console.log(response);
            state.requestList = response.data.requestsList;
            console.log(state.requestList);
            pages.total = state.requestList.length;
            state.isUnsolvedFilter = true;
            pagingMethod(pages.page);
          });
      } else {
        state.isUnsolvedFilter = false;
        pagingMethod(pages.page);
      }
    };

    const changeKeyword = (e) => {
      keyword.value = e.target.value;
    };

    watch(
      () => keyword.value,
      () => pagingMethod(pages.page),
    );

    // 검색기능
    const searchRequest = async () => {
      if (keyword.value !== "") {
        console.log(keyword.value);
        axios
          .get(`${process.env.VUE_APP_API_URL}/request/topic/${keyword.value}`)
          .then((response) => {
            const data = response.data;
            console.log("검색결과", data.requestsList);
            state.requestList = data.requestsList;
            pages.total = state.requestList.length;
            pagingMethod(1);
          });
      }
    };
    return {
      pages,
      unresolved,
      pagingMethod,
      getRequestList,
      pageDataSetting,
      unSolvedFilter,
      changeKeyword,
      searchRequest,
      state,
    };
  },
};
</script>

<style src="@/css/classRequestList.scss" lang="scss" scoped></style>
