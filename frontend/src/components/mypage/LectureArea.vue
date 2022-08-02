<template>
  <div class="right-box">
    <div class="lecture-area" v-if="!isEnd">
      <div
        v-for="(lecture, index) in scheduledClass"
        :key="index"
        class="lecture-box"
      >
        <div class="lecture-thumb">
          <div class="state-btn">
            <i class="fa-solid fa-circle"></i>&nbsp;{{ lecture.classStatus }}
          </div>
        </div>
        <div class="lecture-info">
          <div class="teacher-photo" v-if="!(user.status === 2)">
            <img src="@/assets/profile.png" />
          </div>
          <div class="lecture-teacher">{{ lecture.classTeacher }} 강사</div>
          <div class="lecture-title">{{ lecture.classTitle }}</div>
          <div class="lecture-date">{{ lecture.classStartTime }}</div>
          <button class="lecture-cancel" v-if="!user.status === 2">취소</button>
        </div>
      </div>
    </div>

    <div class="lecture-area" v-else>
      <div
        v-for="(lecture, index) in endClass"
        :key="index"
        class="lecture-box"
      >
        <div class="lecture-thumb">
          <div class="end-btn">{{ lecture.classStatus }}</div>
        </div>
        <div class="lecture-info">
          <div class="teacher-photo" v-if="!(user.status === 2)">
            <img src="@/assets/profile.png" />
          </div>
          <div class="lecture-teacher">{{ lecture.classTeacher }} 강사</div>
          <div class="lecture-title">{{ lecture.classTitle }}</div>
          <div class="lecture-date">{{ lecture.classEndTime }} 종료</div>
        </div>
      </div>
    </div>
    <div class="pagination-wrapper">
      <div v-if="page.startPage > 1">
        <i class="fa-solid fa-angles-left"></i>
      </div>
      <div v-if="page.startPage > 1">
        <i class="fa-solid fa-angle-left"></i>
      </div>
      <div class="page" v-for="(item, index) in page.pageRange" :key="index">
        <router-link to="item">&nbsp;</router-link>
      </div>
      <div v-if="page.endPage > page.totalPage">
        <i class="fa-solid fa-angle-right"></i>
      </div>
      <div v-if="page.endPage + 2 > page.totalPage">
        <i class="fa-solid fa-angles-right"></i>
      </div>
    </div>
  </div>
</template>

<script>
import { computed, reactive } from "vue";

export default {
  setup() {
    const page = reactive({
      startPage: 2, //화면에 보이는 페이지 시작 번호
      currentPage: 3, // 현재 페이지 번호
      totalCount: 6, // 총 콘텐츠
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
      endPage: 7, // 화면에 보이는 페이지 끝 번호
      totalPage: computed(() => {
        return page.totalCount / 5;
      }), // 총 페이지 수
    });
    return {
      page,
    };
  },
  props: ["isEnd", "user", "scheduledClass", "endClass"],
  components: {
    // Carousel,
    // Slide,
  },
};
</script>

<style scoped src="@/css/mypage.scss" lang="scss"></style>
