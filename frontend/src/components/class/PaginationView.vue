<template>
  <div class="pagination-wrapper" v-if="pageSetting.list.length">
    <div
      v-if="pageSetting.startPage !== null"
      @click="
        pageSetting.startPage !== null ? sendPage(pageSetting.startPage) : ''
      "
    >
      <i class="fa-solid fa-angles-left"></i>
    </div>
    <div
      v-if="pageSetting.first !== null"
      @click="pageSetting.first !== null ? sendPage(pageSetting.first) : ''"
    >
      <i class="fa-solid fa-angle-left"></i>
    </div>
    <div
      class="page"
      :class="{ active: page === pageSetting.currentPage }"
      v-for="page in pageSetting.list"
      :key="page"
      @click="sendPage(page)"
    >
      {{ page }}
    </div>
    <div
      v-if="pageSetting.end !== null"
      @click="pageSetting.end !== null ? sendPage(pageSetting.end) : ''"
    >
      <i class="fa-solid fa-angle-right"></i>
    </div>
    <div
      v-if="pageSetting.totalPage !== null"
      @click="
        pageSetting.totalPage !== null ? sendPage(pageSetting.totalPage) : ''
      "
    >
      <i class="fa-solid fa-angles-right"></i>
    </div>
  </div>
</template>

<script>
export default {
  props: ["pageSetting"],
  setup(props, { emit }) {
    const sendPage = (page) => {
      emit("paging", page);
    };
    return {
      sendPage,
    };
  },
};
</script>

<style src="@/css/classRequestList.scss" lang="scss" scoped></style>
