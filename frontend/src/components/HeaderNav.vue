<template>
  <div class="container">
    <section class="left">
      <div class="listdiv" v-if="!user">
        <router-link :to="{ name: 'home' }" class="list">
          <div><img src="@/assets/header.png" /></div>
        </router-link>
        <router-link :to="{ name: 'classlist' }" class="list"
          >수업목록</router-link
        >
      </div>
      <div class="listdiv" v-if="user && user.userType === 'student'">
        <router-link :to="{ name: 'classlist' }" class="list"
          ><div><img src="@/assets/header.png" /></div
        ></router-link>
        <router-link :to="{ name: 'classlist' }" class="list"
          >수업목록</router-link
        >
        <router-link :to="{ name: 'classrequest' }" class="list"
          >수업요청</router-link
        >
        <router-link :to="{ name: 'secret' }" class="list"
          >비밀친구</router-link
        >
      </div>
      <div class="listdiv" v-if="user && user.userType === 'volunteer'">
        <router-link :to="{ name: 'classlist' }" class="list">
          <div><img src="@/assets/header.png" /></div>
        </router-link>
        <router-link :to="{ name: 'classlist' }" class="list"
          >수업목록</router-link
        >
        <router-link
          :to="{ name: 'classregister', params: { rid: -1 } }"
          class="list"
          >수업등록</router-link
        >
        <router-link :to="{ name: 'classrequest' }" class="list"
          >수업요청</router-link
        >
      </div>
      <div class="listdiv" v-if="user && user.userType === 'parent'">
        <div><img src="@/assets/header.png" /></div>
        <router-link :to="{ name: 'classlist' }" class="list"
          >수업목록</router-link
        >
        <router-link :to="{ name: 'classrequest' }" class="list"
          >수업요청</router-link
        >
      </div>
    </section>

    <section class="right">
      <router-link :to="{ name: 'login' }" v-if="!user">
        <button class="login">로그인</button>
      </router-link>
      <div class="img-wrapper" v-if="user">
        <img src="@/assets/profile.png" @click="toggleProfileModal" />
        <ul v-if="isProfileOpen">
          <router-link :to="{ name: 'mypage' }">마이페이지</router-link>
          <a href="" @click.prevent="logout">로그아웃</a>
        </ul>
      </div>
      <div class="bell-wrapper" v-if="user && user.userType != 'volunteer'">
        <i class="fa-solid fa-bell" @click="toggleNoticeModal"
          ><div v-if="user.userType === 'student'" class="count">
            {{ notices.length }}
          </div>
          <div v-else class="count">
            {{ state.totalNotice }}
          </div></i
        >

        <article class="modal" v-if="isNoticeOpen">
          <div class="title-wrapper">
            <i class="fa-solid fa-bell"></i>
            알림창
          </div>
          <div class="row-wrapper" v-if="user.userType === 'parent'">
            <div v-for="(children, index) in notices" :key="index">
              <div v-for="notice in children" :key="notice.scid" class="row">
                <img src="@/assets/profile.png" />
                <label
                  ><p>
                    자녀
                    <span style="font-weight: bold">{{ notice.sname }}</span
                    >의 상담중 위험 용어
                    <span style="color: #ffadad; font-weight: bold">{{
                      notice.sccontent
                    }}</span
                    >이(가) 발생했습니다.
                  </p>
                </label>
                <i
                  class="fa-solid fa-xmark close"
                  @click="readAlaramParent(notice.scid)"
                ></i>
              </div>
            </div>
          </div>
          <div class="row-wrapper" v-else>
            <div class="row" v-for="(notice, index) in notices" :key="index">
              <label>
                <p>
                  요청글
                  <span class="notice-title">{{ notice.rtitle }}</span> 의
                  수업이 개설되었습니다.
                </p>
              </label>
              <router-link
                :to="{
                  name: 'classrequestdetail',
                  params: { rid: notice.rid },
                }"
                @click="checkAlarm(notice.rid)"
                ><i
                  class="fa-solid fa-circle-arrow-right"
                  style="font-size: 24px"
                ></i
              ></router-link>
            </div>
          </div>
        </article>
      </div>

      <button
        :class="{ consult: true, on: user.talkable, off: !user.talkable }"
        v-if="user && user.userType === 'volunteer'"
        @click="toggleTalkable"
      >
        <i class="fa-solid fa-circle"></i>&nbsp;상담
        {{ user.talkable ? "ON" : "OFF" }}
      </button>
    </section>

    <div v-if="state.isMatchingModal" class="matching-modal">
      <div class="modal-wrapper">
        <RequestPostCanvas
          v-if="state.characterColor"
          :characterColor="state.characterColor"
          :isMatchingProfile="true"
        ></RequestPostCanvas>
        <div class="matching-title">비밀친구 매칭이 되었습니다!</div>
        <div>
          <button class="accept-matching" @click="acceptMatching">수락</button>
          <button class="reject-matching" @click="rejectMatching">거절</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref, reactive, watch } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import RequestPostCanvas from "@/components/class/RequestPostCanvas.vue";
import { useRouter } from "vue-router";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  props: ["isMatchingBtnClick", "rid"],
  components: {
    RequestPostCanvas,
  },
  name: "HeaderNav",
  setup(props, { emit }) {
    const router = useRouter();
    const store = useStore();
    const user = ref(store.state.root.user);
    const state = reactive({
      isMatchingModal: false,
      characterColor: null,
      studentName: "",
      matchingSid: null,
      children: [],
      totalNotice: 0,
      stompClient: store.state.root.stompClient,
      socket: null,
      studentDto: null,
    });
    let isNoticeOpen = ref(false);

    let isProfileOpen = ref(false);

    const notices = ref("");
    const toggleProfileModal = () => {
      isNoticeOpen.value = false;
      isProfileOpen.value = !isProfileOpen.value;
    };

    const toggleNoticeModal = () => {
      isProfileOpen.value = false;
      isNoticeOpen.value = !isNoticeOpen.value;
    };

    const connectSocket = async (isVolunteer) => {
      console.log("connect socket", store.state.root.stompClient);
      if (
        store.state.root.stompClient &&
        store.state.root.stompClient.connected
      )
        return;

      const serverURL = `${process.env.VUE_APP_API_URL}/ws`;
      state.socket = await new SockJS(serverURL);
      store.commit("root/connectSocket", Stomp.over(state.socket));
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      state.stompClient = store.state.root.stompClient;
      await state.stompClient.connect(
        {},
        () => {
          console.log("연결됐다아~");
          if (isVolunteer) {
            // 봉사자일때
            state.stompClient.subscribe("/send/talkable", (res) => {
              if (
                JSON.parse(res.body).message === "이미 매칭이 완료되었습니다!"
              ) {
                console.log("이미매칭완료됐대!");
                state.isMatchingModal = false;
              } else {
                state.isMatchingModal = true;
                console.log(
                  "여기서계속.. 호출됨..",
                  JSON.parse(res.body).student,
                );
                // 캐릭터 컬러 넣어줌~
                state.characterColor = JSON.parse(res.body).student.character;
                state.matchingSid = JSON.parse(res.body).student.sid;
                state.studentDto = JSON.parse(res.body).student;
              }
            });
          } else {
            // 학생일때
            subscribeStudentAlarm();
          }
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
        },
      );
    };

    const subscribeStudentAlarm = () => {
      state.stompClient.subscribe(`/send/wait/${user.value.sid}`, (res) => {
        console.log("들어오니~", JSON.parse(res.body).message);
        if (JSON.parse(res.body).message === "success") {
          //수업등록 알림이온거다
          console.log("여기왔니?");
          getClassOpenAlarm();
        } else {
          // 봉사자가 비밀친구 매칭을 승인한거다
          const vid = JSON.parse(res.body).volunteer.vid;
          router.push({
            name: "secrettalk",
            params: { sid: user.value.sid, vid: vid },
          });
        }
      });
    };

    //학생이 버튼을 눌렀을 경우, (props로 전달받은 값이 true일 경우...?)
    watch(
      () => props.isMatchingBtnClick,
      (cur) => {
        if (cur) {
          sendMatchingStart();
        }
      },
    );

    // 학생이 매칭시작버튼을 눌렸음을 알린다.
    const sendMatchingStart = () => {
      const msg = {
        student: user.value,
      };
      console.log(state.stompClient);
      state.stompClient.send("/vreceive", JSON.stringify(msg), (res) => {
        console.log(res);
      });
      emit("sendAlarm");
    };

    const logout = () => {
      store.dispatch("root/inactiveKakaoToken", user.value.accessToken);
      store.commit("root/logoutUser");
      if (user.value.userType === "volunteer" && user.value.talkable)
        disconnectSocket();
      location.href = "/";
    };

    const toggleTalkable = () => {
      changeTalkableState();
    };

    //클래스 오픈 알람
    const getClassOpenAlarm = async () => {
      await axios
        .get(`${process.env.VUE_APP_API_URL}/likes/${user.value.sid}`)
        .then((response) => {
          notices.value = response.data.requsest;
        });
    };
    onMounted(() => {
      if (user.value && user.value.userType === "student") {
        getClassOpenAlarm();
      }
      if (user.value && user.value.userType === "parent") {
        getChildren();
      }
      console.log(user.value);
    });

    // 자식정보 불러오기
    const getChildren = async () => {
      await store.dispatch("root/getChildren", user.value.email).then((res) => {
        state.children = res.data.childs;
        getChildrenDangerAlarm();
      });
    };

    // 학생별 알람에 대한 읽기 완료
    const checkAlarm = async (rid) => {
      await axios
        .post(`${process.env.VUE_APP_API_URL}/likes/read`, {
          rid: rid,
          sid: user.value.sid,
        })
        .then((response) => {
          console.log(response);
        });
    };

    //자식들 위험단어 알림을 받는다.
    const getChildrenDangerAlarm = async () => {
      state.children.forEach((child) => {
        store
          .dispatch("root/getChildrenDangerAlarm", child.sid)
          .then((response) => {
            console.log(response.data.requsest);
            notices.value = [];
            // 자녀당 배열이 들어있음
            notices.value.push(response.data.requsest);
            state.totalNotice += response.data.requsest.length;
          });
      });
    };

    // 상담 on/off 상태 변경
    const changeTalkableState = async () => {
      console.log("시작한다아아");
      await axios
        .put(
          `${process.env.VUE_APP_API_URL}/volunteer/talkable/${user.value.vid}`,
          { id: user.value.vid },
        )
        .then((response) => {
          console.log(response);
          store.commit("root/toggleTalkable");
          console.log(user.value.talkable);
          if (user.value.talkable) {
            connectSocket(true);
          } else {
            console.log("소켓 끌게요");
            disconnectSocket();
          }
        });
    };

    const rejectMatching = () => {
      state.isMatchingModal = false;
      emit("sendAlarm");
    };
    console.log(user.value);

    const acceptMatching = () => {
      // 학생에게 봉사자 정보 넘겨주기
      sendAcceptMatching();
      sendCompleteMatching();
      router.push({
        name: "secrettalk",
        params: { sid: state.matchingSid, vid: user.value.vid },
      });
    };

    const sendCompleteMatching = () => {
      const msg = {
        volunteer: null,
        student: state.studentDto,
        message: "이미 매칭이 완료되었습니다!",
      };
      state.stompClient.send("/vreceive", JSON.stringify(msg), (res) => {
        console.log(res);
      });
    };

    const sendAcceptMatching = () => {
      const msg = {
        volunteer: user.value,
        student: state.studentDto,
      };
      state.stompClient.send("/sreceive", JSON.stringify(msg), (res) => {
        console.log(res);
      });
    };

    //부모님이 알람을 읽었따!
    const readAlaramParent = async (scid) => {
      store
        .dispatch("root/readAlaramParent", {
          scId: scid,
        })
        .then((res) => {
          console.log(res);
        });
    };

    const disconnectSocket = () => {
      if (state.socket) {
        state.socket.close();
        state.socket = null;
      }

      console.log("소켓 정상 종료");
      state.stompClient = null;
      store.commit("root/disconnectSocket");
    };

    const initSocket = () => {
      if (user.value && user.value.userType === "volunteer") {
        if (user.value.talkable) connectSocket(true);
        else disconnectSocket();
      }

      if (user.value && user.value.userType === "student") {
        connectSocket(false);
      }

      if (user.value && user.value.userType === "parent") {
        connectSocket(false);
      }
    };
    initSocket();

    return {
      user,
      isNoticeOpen,
      sendAcceptMatching,
      isProfileOpen,
      toggleProfileModal,
      getClassOpenAlarm,
      toggleNoticeModal,
      changeTalkableState,
      acceptMatching,
      readAlaramParent,
      rejectMatching,
      notices,
      getChildrenDangerAlarm,
      checkAlarm,
      logout,
      subscribeStudentAlarm,
      state,
      getChildren,
      toggleTalkable,
      connectSocket,
    };
  },
};
</script>

<style scoped src="@/css/header.scss" lang="scss"></style>
