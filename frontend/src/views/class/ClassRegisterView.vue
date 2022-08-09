<template>
  <div class="background">
    <HeaderNav />
    <div class="title">
      <h2>수업 등록</h2>
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
              placeholder="제목을 입력하세요."
              v-model="state.classTitle"
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
            <div class="file-box">
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
            <label for="classPeople" class="info-title">최대 인원</label>
            <input
              type="number"
              min="0"
              name="classPeople"
              id="classPeople"
              oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
              v-model="state.classPeople"
            />
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
      <button class="register-btn" @click="classRegister">등록</button>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import RectPostCard from "@/components/common/RectPostCard.vue";
import { reactive } from "vue";
import axios from "axios";

export default {
  components: {
    HeaderNav,
    RectPostCard,
  },
  setup() {
    const state = reactive({
      classTitle: "",
      classDate: "",
      classStartTime: "",
      classEndTIme: "",
      classThumbnail: "",
      classOpen: "",
      classPeople: "",
      classContent: "",
      classImgFile: "",
    });

    const formData = new FormData();
    const fileChange = async (e) => {
      var input = e.target;
      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = (e) => {
          state.classImgFile = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
        formData.append("thumbnail", input.files[0]);
      }
    };

    const classRegister = async () => {
      const user = JSON.parse(localStorage.getItem("user"));

      const classDto = {
        vid: { vid: user.vid },
        title: state.classTitle,
        introduce: state.classContent,
        stime: state.classDate + "T" + state.classStartTime,
        etime: state.classDate + "T" + state.classEndTIme,
        maxcnt: state.classPeople,
        opened: state.classOpen,
        thumbnail: state.classThumbnail,
      };

      // 이미지 파일 등록
      await axios
        .post(`${process.env.VUE_APP_API_URL}/class/image`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((response) => {
          console.log(response.data);
          classDto.thumbnail = response.data;
        })
        .catch((error) => {
          console.log(error);
        });

      // 클래스 등록
      await axios
        .post(`${process.env.VUE_APP_API_URL}/class`, classDto)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    return {
      state,
      fileChange,
      classRegister,
    };
  },
};
</script>

<style src="@/css/classRegister.scss" lang="scss" scoped></style>
