<template>
  <div class="background">
    <HeaderNav />
    <div class="back-btn-wrapper" @click="$router.go(-1)">
      <button class="back-btn"></button>
    </div>
    <div class="title">
      <h2>수업 수정</h2>
    </div>
    <div class="contents" v-if="classInfo">
      <article>
        <div class="left-box">
          <div class="info-box">
            <label for="className" class="info-title">제목</label>
            <input
              type="text"
              name="className"
              id="className"
              v-model="classInfo.title"
              @input="inputChange"
            />
          </div>
          <div class="info-box">
            <label for="classTime" class="info-title">시간</label>
            <div class="classTime">
              <input type="date" name="" id="" v-model="classInfo.dateStr" />
              <input
                type="time"
                name="startTime"
                id="startTime"
                v-model="classInfo.stimeStr"
              />
              -
              <input
                type="time"
                name="endTime"
                id="endTime"
                v-model="classInfo.etimeStr"
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
              <input type="checkbox" v-model="classInfo.opened" />
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
              v-model="classInfo.maxcnt"
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
              v-model="classInfo.introduce"
              @input="inputChange"
            ></textarea>
          </div>
        </div>
        <div class="right-box">
          <h4>미리보기</h4>
          <RectPostCard :state="classInfo" />
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

    const classInfo = reactive({
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
      state: 0,
    });

    // 수업 상세정보 가져오기
    const getClassDetail = async () => {
      store
        .dispatch("root/getClassDetail", cid)
        .then((response) => {
          const data = response.data;
          console.log(data);
          classInfo.title = data.title;
          classInfo.dateStr = data.dateStr;
          classInfo.stimeStr = data.stimeStr;
          classInfo.etimeStr = data.etimeStr;
          classInfo.thumbnail = data.thumbnail;
          classInfo.classOpen = data.opened;
          classInfo.maxcnt = data.maxcnt;
          classInfo.introduce = data.introduce;
          classInfo.state = data.state;
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
          classInfo.preview = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
        formData.append("thumbnail", input.files[0]);
      }
    };

    const classModify = async () => {
      const classDto = {
        cid: cid,
        vid: { vid: user.vid },
        title: classInfo.title,
        introduce: classInfo.introduce,
        maxcnt: classInfo.maxcnt,
        opened: classInfo.classOpen,
        thumbnail: classInfo.thumbnail,
        etime: "",
        stime: "",
        state: classInfo.state,
      };

      if (
        classDto.title === "" ||
        classDto.introduce === "" ||
        classDto.maxcnt === 0 ||
        classDto.opened === ""
      ) {
        isConfirm.status = true;
      } else {
        classDto.stime = classInfo.dateStr + "T" + classInfo.stimeStr;
        classDto.etime = classInfo.dateStr + "T" + classInfo.etimeStr;

        if (classDto.thumbnail !== "") {
          // 이미지 파일 등록
          store
            .dispatch("root/registerImage", formData)
            .then((response) => {
              console.log(response.data);
              classDto.thumbnail = response.data;
            })
            .catch((error) => {
              console.log(error);
            });
        }

        // 클래스 수정
        store
          .dispatch("root/modifyClass", classDto)
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

    const inputChange = (e) => {
      if (e.target.id === "className") {
        classInfo.title = e.target.value;
      } else {
        classInfo.introduce = e.target.value;
      }
    };

    return {
      classInfo,
      isConfirm,
      fileChange,
      classModify,
      inputChange,
    };
  },
};
</script>

<style src="@/css/classRegister.scss" lang="scss" scoped></style>
