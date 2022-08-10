<template>
  <div class="background">
    <HeaderNav />
    <div class="back-btn-wrapper" @click="$router.go(-1)">
      <button class="back-btn"></button>
    </div>
    <div class="title">
      <h2>수업 수정</h2>
    </div>
    <div class="contents" v-if="state">
      <article>
        <div class="left-box">
          <div class="info-box">
            <label for="className" class="info-title">제목</label>
            <input
              type="text"
              name="className"
              id="className"
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
              <input type="checkbox" v-model="state.opened" />
              <span class="slider round"></span>
            </label>
          </div>
          <div class="info-box">
            <label for="" class="info-title">최대 인원</label>
            <input
              type="number"
              name="classPeople"
              min="0"
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
      <button class="modify-btn" @click="classModify">수정</button>
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
import { useRouter, useRoute } from "vue-router";
import { useStore } from "vuex";
import axios from "axios";

export default {
  components: {
    HeaderNav,
    RectPostCard,
  },
  setup() {
    const router = useRouter();
    const route = useRoute();
    const store = useStore();
    const user = reactive(store.state.root.user);
    const cid = route.params.cid;

    console.log(user);

    const state = reactive({
      title: "",
      dateStr: "",
      stimeStr: "",
      etimeStr: "",
      thumbnail: "",
      preview: null,
      classOpen: false,
      maxcnt: 0,
      introduce: "",
      vid: { nickname: user.nickname },
    });

    // 수업 상세정보 가져오기
    const getClassDetail = async () => {
      axios
        .get(`${process.env.VUE_APP_API_URL}/class/${cid}`)
        .then((response) => {
          const data = response.data;
          console.log(data);
          state.title = data.title;
          state.dateStr = data.dateStr;
          state.stimeStr = data.stimeStr;
          state.etimeStr = data.etimeStr;
          state.thumbnail = data.thumbnail;
          state.classOpen = data.classOpen;
          state.maxcnt = data.maxcnt;
          state.introduce = data.introduce;
        })
        .catch((error) => {
          console.log(error);
        });
    };

    getClassDetail();

    const formData = new FormData();

    const fileChange = (e) => {
      var input = e.target;
      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = (e) => {
          console.log("??");
          state.preview = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
        formData.append("thumbnail", input.files[0]);
      }
    };

    const classModify = async () => {
      const classDto = {
        vid: { vid: user.vid },
        title: state.title,
        introduce: state.introduce,
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
        classDto.opened === ""
      ) {
        isConfirm.status = true;
      } else {
        classDto.stime = state.dateStr + "T" + state.stimeStr;
        classDto.etime = state.dateStr + "T" + state.etimeStr;

        if (classDto.thumbnail !== "") {
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
        }

        // 클래스 수정
        await axios
          .put(`${process.env.VUE_APP_API_URL}/class`, classDto)
          .then((response) => {
            console.log(response);
            router.push({ name: "classdetail", params: { cid: cid } });
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
      isConfirm,
      fileChange,
      classModify,
    };
  },
};
</script>

<style src="@/css/classRegister.scss" lang="scss" scoped></style>
