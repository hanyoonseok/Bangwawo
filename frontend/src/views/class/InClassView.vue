<template>
  <section class="background">
    <HostView
      v-if="user && user.userType === 'volunteer' && state.session"
      :dataLen="dataLen"
      :currentUsers="currentUsers"
      :me="state.publisher"
      :subs="state.subscribers"
      @activeVideo="activeVideo"
      @activeMute="activeMute"
      @publishScreenShare="publishScreenShare"
      :session="state.session"
      :chats="state.chats"
      :screen="state.screenShareState"
      :cid="cid"
      @updateMainVideoStreamManager="updateMainVideoStreamManager"
      :volunteerNickname="volunteerNickname"
    />
    <UserView
      v-if="user && user.userType === 'student' && state.session"
      :dataLen="dataLen"
      :currentUsers="currentUsers"
      :state="state"
      :me="state.publisher"
      :subs="state.subscribers"
      :session="state.session"
      :chats="state.chats"
      :screen="state.screenShareState"
      @activeVideo="activeVideo"
      @activeMute="activeMute"
      @updateMainVideoStreamManager="updateMainVideoStreamManager"
      @leaveSession="leaveSession"
      :volunteerNickname="volunteerNickname"
    />
  </section>
</template>

<script>
import {
  reactive,
  onBeforeUnmount,
  computed,
  onMounted,
  watch,
  ref,
} from "vue";
import axios from "axios";
import moment from "moment";
// import { OpenVidu } from "openvidu-browser";
import { OpenVidu } from "openvidu-browser";
import HostView from "@/components/class/HostView.vue";
import UserView from "@/components/class/UserView.vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "InClassView",
  components: {
    HostView,
    UserView,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const OPENVIDU_SERVER_URL = process.env.VUE_APP_OV_DOMAIN;
    const OPENVIDU_SERVER_SECRET = process.env.VUE_APP_OV_SECRET;
    const OV = new OpenVidu();
    const OVScreen = new OpenVidu(); // 화면 공유

    const route = useRoute();

    const user = ref(store.state.root.user);

    const sessionId = route.params.mySessionId;
    console.log("mySessionId", sessionId);
    const userType = route.params.userType === "volunteer" ? true : false;
    console.log("userType", userType);
    const nickname = route.params.nickname;
    console.log("nickname", nickname);
    const cid = route.params.cid;
    const vid = route.params.vid;
    const sid = route.params.sid;
    const volunteerNickname = route.params.volunteerNickname;

    const state = reactive({
      OV: OV,
      OVScreen: OVScreen,
      sessionScreen: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: OV.initPublisher(undefined, {
        audioSource: undefined, // The source of audio. If undefined default microphone
        videoSource: undefined, // The source of video. If undefined default webcam
        publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
        publishVideo: true, // Whether you want to start publishing with your video enabled or not
        resolution: "1280x720", // The resolution of your video
        frameRate: 30, // The frame rate of your video
        insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
        mirror: false, // Whether to mirror your local video or not
      }),
      subscribers: [],
      mySessionId: sessionId,
      myUserName: nickname,
      joinedPlayerNumbers: 0,
      chats: [],

      currentUsers: computed(() => {
        return state.subscribers.slice(
          state.dataIdx,
          Math.min(state.dataIdx + state.dataLen, state.subscribers.length + 1),
        );
      }),

      isHost: userType,
      dataLen: computed(() => {
        return user.value.userType === "volunteer" ? 12 : 4;
      }),
      dataIdx: 0,

      screenShareState: false, //화면공유 여부
    });

    // 화면 공유 상태 변화했는지 감지
    watch(
      () => state.screenShareState,
      (cur) => {
        console.log("watch야 일을 하고 있니?????????????");
        screenShare(cur);
      },
      { deep: true },
    );

    const screenShare = (cur) => {
      console.log("상태", cur);
      const screenShareStart = document.getElementById("screenShareStart");
      const volunteerVideo = document.getElementById("volunteerVideo");
      const screenContainer = document.getElementById("container-screens");
      if (cur) {
        // true일때는 화면공유 창 보여주기
        if (state.isHost) {
          screenShareStart.style.display = "none"; //봉사자는 화면공유 시작 버튼 안보여야 함
        } else {
          // 학생은 화면공유창 보여야하고 기존 봉사자 화면 안보여야함..?
          screenContainer.style.display = "block";
          volunteerVideo.style.display = "none";
        }
      } else {
        if (state.isHost) {
          screenShareStart.style.display = "block";
        } else {
          screenContainer.style.display = "none";
          volunteerVideo.style.display = "block";
        }
        state.session.signal({
          data: "0",
          to: [],
          type: "screen-off",
        });
      }
    };

    const joinSession = () => {
      console.log("join session");
      // 초기화
      state.session = state.OV.initSession();
      state.sessionScreen = state.OVScreen.initSession();

      // 새로운 Stream을 구독하고 subscribers배열에 저장
      state.session.on("streamCreated", ({ stream }) => {
        console.log(
          "현재 퍼블리셔가 누구인가???????????",
          state.isHost ? "봉사자다" : "학생이다",
        );
        console.log("봉사자인지 학생인지 파악하기 위해 도움이 될까..?", stream);
        // 웹 캠 사용하면서 사용자가 학생일때만 sub에 들어감
        if (stream.typeOfVideo == "CAMERA") {
          const subscriber = state.session.subscribe(stream);
          state.subscribers.push(subscriber);
          if (subscriber.videos !== []) state.joinedPlayerNumbers++;
        }
      });
      // 사용자가 화상 회의에서 나갔을때 나간 사용자 제거
      state.session.on("streamDestroyed", ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          const subscriber = state.subscribers[index];
          if (subscriber.videos !== []) state.joinedPlayerNumbers--;
          state.subscribers.splice(index, 1);
        }
      });
      // On every asynchronous exception...
      state.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // 화면 공유
      state.sessionScreen.on("streamCreated", (event) => {
        if (event.stream.typeOfVideo == "SCREEN") {
          state.screenShareState = true;
          // Subscribe to the Stream to receive it. HTML video will be appended to element with 'container-screens' id
          var subscriberScreen = state.sessionScreen.subscribe(
            event.stream,
            "container-screens",
          );
          // When the HTML video has been appended to DOM...
          subscriberScreen.on("videoElementCreated", () => {
            // Add a new <p> element for the user's nickname just below its video
            // appendUserData(event.element, subscriberScreen.stream.connection);
          });
        }
      });

      state.session.on("signal:screen-off", () => {
        // console.log("화면공유 끝났다고", e);
        state.screenShareState = false;
        const screenShare = document.getElementById("screenShareStart");
        // console.log("화면공유버튼", screenShare);
        if (screenShare) {
          screenShare.style.display = "block";
        }
        const containerScreens = document.getElementById("container-screens");
        // console.log("화면공유 컨테이너", containerScreens);
        if (containerScreens) {
          containerScreens.style.display = "none";
        }
        // console.log("공유상태", state.screenShareState);
      });

      // 채팅
      state.session.on("signal:my-chat", (e) => {
        console.log(e.data); // Message
        console.log(e.from); // Connection object of the sender
        console.log(e.type); // The type of message ("my-chat")
        const parsedDate = moment(new Date()).format("HH:mm");
        const hour = parsedDate.split(":")[0];
        const finalDate =
          hour < 12 ? "오전 " + parsedDate : "오후 " + parsedDate;
        state.chats.push({
          sender: JSON.parse(e.from.data).clientData,
          msg: e.data,
          date: finalDate,
        });
      });

      // 봉사자가 세션 종료하면 학생도 자동으로 종료시켜야 함
      state.session.on("signal:leave-session", (e) => {
        console.log("봉사자가 세션 종료???", e);
        leaveSession();
      });

      console.log("sessionid", state.mySessionId);
      console.log("user name", state.myUserName);
      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      // 세션에 연결하려면 서버에 토큰 요청 => 백엔드가 처리해줘야 할 부분, 일단 임시 테스트
      getToken(state.mySessionId).then((token) => {
        state.session
          .connect(token, { clientData: state.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---
            let publisher = state.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "600x320", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            state.mainStreamManager = publisher;
            state.publisher = publisher;
            state.joinedPlayerNumbers++;
            state.session.publish(publisher);
            console.log("######################joinSession", state.session);
            if (!state.isHost) {
              console.log("학생이니까 녹화를 시작하겠다ㅏㅏ");
              startRecording(); // 녹화 시작
            }
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message,
            );
          });
      });

      getToken(state.mySessionId).then((tokenScreen) => {
        // Create a token for screen share
        state.sessionScreen
          .connect(tokenScreen, { clientData: state.myUserName })
          .then(() => {
            // console.log("Session screen connected");
          })
          .catch((error) => {
            console.warn(
              "There was an error connecting to the session for screen share:",
              error.code,
              error.message,
            );
          });
      });

      window.addEventListener("beforeunload", leaveSession);
    };

    const recordId = ref(null);

    const startRecording = () => {
      const recordings = {
        session: state.mySessionId,
        name: sid,
        hasAudio: true,
        hasVideo: true,
        outputMode: "INDIVIDUAL", //개별녹화?
        resolution: "1280x720",
        frameRate: 25,
        shmSize: 536870912,
        ignoreFailedStreams: false,
        mediaNode: {
          id: "media_i-0c58bcdd26l11d0sd",
        },
      };
      console.log(recordings);

      axios
        .post(
          OPENVIDU_SERVER_URL + "/openvidu/api/recordings/start",
          recordings,
          {
            headers: {
              Authorization:
                "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
              "Content-Type": "application/json",
            },
          },
        )
        .then((response) => {
          console.log("===== 녹화 시작 =====", response.data);
          recordId.value = response.data.id;
        })
        .catch((error) => console.error(error));
    };

    const stopRecording = () => {
      console.log("stop");
      console.log(recordId.value);
      axios
        .post(
          OPENVIDU_SERVER_URL +
            "/openvidu/api/recordings/stop/" +
            recordId.value,
          null,
          {
            headers: {
              Authorization:
                "Basic " + btoa("OPENVIDUAPP:" + OPENVIDU_SERVER_SECRET),
            },
          },
        )
        .then((response) => {
          console.log("===== 녹화 끝 =====", response);
        })
        .catch((error) => console.error(error));
    };

    const leaveSession = () => {
      console.log("세션을 종료시키고 싶슴다");
      if (state.session == undefined) return;

      console.log(state.publisher);
      state.publisher.off();

      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (state.session) {
        state.session.disconnect();
      }

      state.session = undefined;
      state.sessionScreen = undefined;
      state.mainStreamManager = undefined;
      state.publisher = undefined;
      state.subscribers = [];
      state.OV = undefined;

      if (state.isHost) {
        console.log("세션을 종료할건데 난 봉사자입니당");
        store
          .dispatch("root/endClass", { cid: cid, vid: vid })
          .then((response) => {
            console.log(response);
            window.removeEventListener("beforeunload", leaveSession);
            router.push({ name: "feedbackSubmit", params: { cid: cid } });
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        // 녹화 끝
        stopRecording();
        console.log("나는 학생!");
        window.removeEventListener("beforeunload", leaveSession);
        const emotionInfo = { ...store.state.root.emotions };
        const emotionCnt = store.state.root.emotionCnt;
        const keySet = Object.keys(emotionInfo);
        for (let i = 0; i < keySet.length; i++) {
          emotionInfo[keySet[i]] /= emotionCnt;
        }
        console.log(emotionInfo);
        console.log(cid);
        console.log(user.value.sid);
        const payload = {
          cid,
          sid: user.value.sid,
          emotion: emotionInfo,
        };

        store.dispatch("root/storeEmotion", payload).then(() => {
          store.commit("root/initEmotion");
        });
        router.push({ name: "mypage" });
      }
    };

    const getToken = (mySessionId) => {
      console.log("getToken");
      return createSession(mySessionId).then((sessionId) =>
        createToken(sessionId),
      );
    };

    const createSession = (sessionId) => {
      console.log("createSession");
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            },
          )
          .then((response) => {
            // console.log(response);
            response.data;
            return response.data;
          })
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`,
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`,
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    };

    const createToken = (sessionId) => {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {
              type: "WEBRTC",
              role: "PUBLISHER",
              kurentoOptions: {
                videoMaxRecvBandwidth: 1000,
                videoMinRecvBandwidth: 300,
                videoMaxSendBandwidth: 1000,
                videoMinSendBandwidth: 300,
                allowedFilters: ["GStreamerFilter", "FaceOverlayFilter"],
              },
            },
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            },
          )
          .then((response) => {
            // console.log(response.data);
            return response.data;
          })
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    };

    // 화면공유 활성화 함수
    const publishScreenShare = () => {
      var publisherScreen = state.OVScreen.initPublisher("container-screens", {
        audioSource: false,
        videoSource: "screen",
      });

      publisherScreen.once("accessAllowed", () => {
        console.log("화면공유시작????");
        console.log("시작", state.session);

        console.log("ovscreen", state.OVScreen);
        console.log("sessionscreen", state.sessionScreen);
        state.screenShareState = true;
        console.log(state.screenShareState);
        publisherScreen.stream
          .getMediaStream({
            video: true,
            audio: false,
          })
          .getVideoTracks()[0]
          .addEventListener("ended", () => {
            // document.getElementById("screenShareStart").style.display = "block";
            // document.getElementById("container-screens").style.display = "none";
            console.log(
              "화면 공유를 멈췄ㄸ따ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ",
            );
            state.sessionScreen.unpublish(publisherScreen);
            state.screenShareState = false;
            console.log(state.screenShareState);
            console.log("끝", state.session);

            console.log("ovscreen", state.OVScreen);
            console.log("sessionscreen", state.sessionScreen);
          });
        state.sessionScreen.publish(publisherScreen);
      });

      publisherScreen.on("videoElementCreated", function (event) {
        console.log(event.element);
        event.element.className += "screen-share";
        console.log(event.element);
        event.element["muted"] = true;
        console.log(event.element["muted"]);
      });

      publisherScreen.once("accessDenied", () => {
        console.error("Screen Share: Access Denied");
      });
    };

    joinSession();
    onBeforeUnmount(() => {
      state.joinedPlayerNumbers = 0;
      leaveSession();
    });

    // const nextClick = () => {
    //   if (dataIdx.value + dataLen.value >= students.value.length) return;
    //   dataIdx.value += dataLen.value;
    //   const currentTempArr = [];
    //   for (
    //     let i = dataIdx.value;
    //     i < Math.min(dataIdx.value + dataLen.value, students.value.length);
    //     i++
    //   ) {
    //     currentTempArr.push(students.value[i]);
    //   }
    //   currentUsers.value = currentTempArr;
    // };

    // const prevClick = () => {
    //   if (dataIdx.value - dataLen.value < 0) return;
    //   dataIdx.value -= dataLen.value;
    //   const currentTempArr = [];
    //   for (
    //     let i = Math.max(0, dataIdx.value);
    //     i < dataIdx.value + dataLen.value;
    //     i++
    //   ) {
    //     currentTempArr.push(students.value[i]);
    //   }
    //   currentUsers.value = currentTempArr;
    // };

    // const changeDataLen = (param) => {
    //   dataLen.value = param;
    // };

    const activeVideo = (videoState) => {
      state.publisher.publishVideo(videoState); // true to enable the video track, false to disable it
    };
    const activeMute = (audioState) => {
      state.publisher.publishAudio(audioState);
    };

    onMounted(() => {
      const screenShare = document.getElementById("screenShareStart");
      if (screenShare) {
        screenShare.style.display = "block";
      }
      const containerScreens = document.getElementById("container-screens");
      if (containerScreens) {
        containerScreens.style.display = "none";
      }
    });

    const updateMainVideoStreamManager = (stream) => {
      if (state.mainStreamManager === stream) return;
      state.mainStreamManager = stream;
    };

    return {
      state,
      cid,
      // dataLen,
      // currentUsers,
      // initCurrentUsers,
      // prevClick,
      // nextClick,
      // changeDataLen,
      activeVideo,
      activeMute,
      publishScreenShare,
      startRecording,
      stopRecording,
      updateMainVideoStreamManager,
      user,
      volunteerNickname,
    };
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
