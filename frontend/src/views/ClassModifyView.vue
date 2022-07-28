<template>
  <div class="background">
    <HeaderNav />
    <div class="title">
      <h2>수업 수정</h2>
    </div>
    <div class="contents">
      <article>
        <div class="left-box">
          <div class="info-box">
            <label for="className" class="info-title">제목</label>
            <input
              type="text"
              name="className"
              id="className"
              v-model="state.className"
            />
          </div>
          <div class="info-box">
            <label for="classTime" class="info-title">시간</label>
            <div class="classTime">
              <input type="date" name="" id="" v-model="state.classDate" />
              <input
                type="time"
                name="startTime"
                id="startTime"
                v-model="state.classStartTime"
              />
              -
              <input
                type="time"
                name="endTime"
                id="endTime"
                v-model="state.classEndTIme"
              />
            </div>
          </div>
          <div class="info-box">
            <label for="classThumbnail" class="info-title">썸네일</label>
            <div class="filebox">
              <label for="file"
                ><img src="@/assets/fileBtn.png" alt="파일 추가 버튼"
              /></label>
              <input type="file" id="file" @change="fileChange" />
            </div>
          </div>
          <div class="info-box">
            <label for="classOpen" class="info-title">공개 여부</label>
            <label class="switch">
              <input type="checkbox" v-model="state.classOpen" />
              <span class="slider round"></span>
            </label>
          </div>
          <div class="info-box">
            <label for="" class="info-title">최대 인원</label>
            <select v-model="state.classPeople">
              <option value="0">0</option>
              <option value="1">1</option>
              <option value="2">2</option>
            </select>
          </div>
          <div class="info-box">
            <label for="" class="info-title">내용</label>
            <textarea
              name=""
              id=""
              cols="30"
              rows="6"
              placeholder="내용을 입력하세요."
              v-model="state.classContent"
            ></textarea>
          </div>
        </div>
        <div class="right-box">
          <h4>미리보기</h4>
          <RectPostCard :state="state" />
        </div>
      </article>
      <button class="registerBtn">수정</button>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import RectPostCard from "@/components/common/RectPostCard.vue";
import { reactive } from "vue";
export default {
  components: {
    HeaderNav,
    RectPostCard,
  },
  setup() {
    const state = reactive({
      className: "",
      classDate: "",
      classStartTime: "",
      classEndTIme: "",
      classThumbnail: "",
      classOpen: "",
      classPeople: "",
      classContent: "",
      classImgFile: "",
    });

    const fileChange = (e) => {
      var input = e.target;
      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = (e) => {
          state.classImgFile = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
      }
    };

    return {
      state,
      fileChange,
    };
  },
};
</script>

<style src="@/css/classRegister.scss" lang="scss" scoped></style>
