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
              v-model="state.title"
            />
          </div>
          <div class="info-box">
            <label for="classTime" class="info-title">시간</label>
            <div class="classTime">
              <input type="date" name="" id="" v-model="state.dateStr" />
              <input
                type="time"
                name="startTime"
                id="startTime"
                v-model="state.stimeStr"
              />
              -
              <input
                type="time"
                name="endTime"
                id="endTime"
                v-model="state.etimeStr"
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
              v-model="state.maxcnt"
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
              v-model="state.introduce"
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
    <div class="confirm" v-if="isConfirm.status">
      <div class="container">
        <img src="@/assets/profile.png" alt="오리" />
        <h2>모두 입력해주세요!</h2>
        <div class="btn-wrapper">
          <button class="btn" @click="isConfirm.status = false">확인</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import RectPostCard from "@/components/common/RectPostCard.vue";
import { reactive } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

export default {
  components: {
    HeaderNav,
    RectPostCard,
  },
  setup() {
    const router = useRouter();
    const user = JSON.parse(localStorage.getItem("user"));

    const state = reactive({
      title: "",
      dateStr: "",
      stimeStr: "",
      etimeStr: "",
      thumbnail: "",
      classOpen: false,
      maxcnt: 0,
      introduce: "",
      vid: { nickname: user.nickname },
    });

    const formData = new FormData();
    const fileChange = async (e) => {
      var input = e.target;
      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = (e) => {
          console.log(e.target.result);
          state.thumbnail = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
        formData.append("thumbnail", input.files[0]);
      }
    };

    const classRegister = async () => {
      console.log(state.classDate);

      const classDto = {
        vid: { vid: user.vid },
        title: state.title,
        introduce: state.introduce,
        // dateStr: state.dateStr,
        // stimeStr: state.stimeStr,
        // etimeStr: state.etimeStr,
        maxcnt: state.maxcnt,
        opened: state.classOpen,
        thumbnail: state.thumbnail,
        etime: "",
        stime: "",
      };

      if (
        classDto.title === "" ||
        classDto.introduce === "" ||
        classDto.maxcnt === 0 ||
        classDto.opened === "" ||
        classDto.thumbnail === undefined
      ) {
        console.log(classDto);
        isConfirm.status = true;
      } else {
        classDto.stime = state.dateStr + "T" + state.stimeStr;
        classDto.etime = state.dateStr + "T" + state.etimeStr;
        console.log(formData);
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
            router.push("/class/list");
          })
          .catch((error) => {
            console.log(error);
          });
      }
    };

    const isConfirm = reactive({
      status: false,
    });

    return {
      state,
      fileChange,
      classRegister,
      isConfirm,
    };
  },
};
</script>

<style src="@/css/classRegister.scss" lang="scss" scoped></style>
