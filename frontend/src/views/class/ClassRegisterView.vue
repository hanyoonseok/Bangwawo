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
      <button class="register-btn" @click="resolveRequest">등록</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";
import HeaderNav from "@/components/HeaderNav.vue";
import RectPostCard from "@/components/common/RectPostCard.vue";
import { reactive } from "vue";
import { useStore } from "vuex";

export default {
  components: {
    HeaderNav,
    RectPostCard,
  },
  setup() {
    const router = useRouter();
    const route = useRoute();
    const store = useStore();

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
      userInfo: store.state.root.user,
      userType: store.state.root.user.userType,
    });
    // 요청글에서 들어온 경우는 room id, 헤더에서 들어온 경우 -1임.
    const rid = route.params.rid;

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

    // 수업 생성시 요청 해결
    // 일단 나중에 합치면 제대로된 데이터 전달해야할듯... 임시로 넣어둔 데이터
    const resolveRequest = () => {
      if (rid !== -1) {
        axios
          .post(`${process.env.VUE_APP_API_URL}/class/${rid}`, {
            vid: { vid: state.userInfo.vid },
            title: "호그와트 지망생을 위한 마법수업",
            introduce: "소개글인데용?",
            maxcnt: 23,
            opened: 0,
            thumbnail: "/class/image/2022AUGUST101660116601.png",
            etime: "",
            stime: "",
          })
          .then((response) => {
            console.log(response);
            router.push(`/class/requestdetail/${rid}`);
          });
      }
    };

    return {
      state,
      fileChange,
      resolveRequest,
      rid,
    };
  },
};
</script>

<style src="@/css/classRegister.scss" lang="scss" scoped></style>
