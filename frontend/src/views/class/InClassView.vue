<template>
  <section class="background">
    <HostView
      v-if="state.isHost && state.session"
      :dataLen="dataLen"
      :currentUsers="currentUsers"
      :leaveSession="leaveSession"
      :me="state.publisher"
      :subs="state.subscribers"
      @activeVideo="activeVideo"
      @activeMute="activeMute"
      @publishScreenShare="publishScreenShare"
      :session="state.session"
      :chats="state.chats"
      :screen="state.screenShareState"
    />
    <UserView
      v-if="!state.isHost && state.session"
      :dataLen="dataLen"
      :currentUsers="currentUsers"
      :leaveSession="leaveSession"
      :state="state"
      :me="state.publisher"
      :subs="state.subscribers"
      @activeVideo="activeVideo"
      @activeMute="activeMute"
      @publishScreenShare="publishScreenShare"
    />
    <!-- <div id="session" v-if="state.session">
      <div id="session-header">
        <h1 id="session-title">{{ state.mySessionId }}</h1>
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>
      <div id="main-video" class="col-md-6">
        <user-video
          :stream-manager="state.mainStreamManager"
          v-if="state.isHost && state.session"
          :dataLen="dataLen"
          :currentUsers="currentUsers"
          :leaveSession="leaveSession"
          :me="state.publisher"
          :subs="state.subscribers"
          @activeVideo="activeVideo"
          @activeMute="activeMute"
          @click="updateMainVideoStreamManager(state.publisher)"
        />
      </div>
      <div id="video-container" class="col-md-6">
        <user-video
          :stream-manager="state.publisher"
          @click="updateMainVideoStreamManager(state.publisher)"
        />
        <user-video
          v-for="sub in state.subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
        />
      </div>
    </div> -->
  </section>
</template>

<script>
import { reactive, onBeforeUnmount, computed, onMounted, watch } from "vue";
import axios from "axios";
import moment from "moment";
import { OpenVidu } from "openvidu-browser";
import HostView from "@/components/class/HostView.vue";
import UserView from "@/components/class/UserView.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "InClassView",
  components: {
    HostView,
    UserView,
  },
  setup() {
    // 테스트용
    const OPENVIDU_SERVER_URL = process.env.VUE_APP_OV_DOMAIN;
    const OPENVIDU_SERVER_SECRET = process.env.VUE_APP_OV_SECRET;
    const OV = new OpenVidu();
    const OVScreen = new OpenVidu(); // 화면 공유

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
        resolution: "600x320", // The resolution of your video
        frameRate: 30, // The frame rate of your video
        insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
        mirror: false, // Whether to mirror your local video or not
      }),
      subscribers: [],
      mySessionId: "SessionAAA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      joinedPlayerNumbers: 0,
      chats: [],

      currentUsers: computed(() => {
        return state.subscribers.slice(
          state.dataIdx,
          Math.min(state.dataIdx + state.dataLen, state.subscribers.length + 1),
        );
      }),
      isHost: true,
      dataLen: computed(() => {
        return state.isHost ? 12 : 4;
      }),
      dataIdx: 0,

      screenShareState: false, //화면공유 여부
    });

    watch(
      () => state.screenShareState,
      (cur) => {
        screenShare(cur);
      },
      { deep: true },
    );

    const screenShare = (cur) => {
      console.log("싱태", cur);
      if (cur) {
        document.getElementById("screenShareStart").style.display = "none";
        document.getElementById("container-screens").style.display = "block";
      } else {
        document.getElementById("screenShareStart").style.display = "block";
        document.getElementById("container-screens").style.display = "none";
      }
    };

    /*
  닉네임:사용자
  sessionName : 방 이름?
  token : 토큰 들어오는데 이건 입장 할때마다 바뀌는 값
  userName : 아이디인데 아마 로그인할대 아이디로 쓸듯?
*/

    // 사용자가 방에 참여하겠다는 버튼 누를때마다 호출
    const joinSession = () => {
      console.log("join session");
      // 초기화
      state.session = state.OV.initSession();
      state.sessionScreen = state.OVScreen.initSession();

      // 새로운 Stream을 구독하고 subscribers배열에 저장
      state.session.on("streamCreated", ({ stream }) => {
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
            console.log("Session screen connected");
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

    const leaveSession = () => {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (state.session) {
        state.session.disconnect();
      }

      if (state.sessionScreen) {
        state.sessionScreen.disconnect();
      }
      state.session = undefined;
      state.sessionScreen = undefined;
      state.mainStreamManager = undefined;
      state.publisher = undefined;
      state.subscribers = [];
      state.OV = undefined;
      window.removeEventListener("beforeunload", leaveSession);
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
            console.log(response);
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
                // location.assign(`https://i7b201.p.ssafy.io`);
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
            console.log(response.data);
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
      document.getElementById("screenShareStart").style.display = "block";
      document.getElementById("container-screens").style.display = "none";
    });

    return {
      state,
      // dataLen,
      // currentUsers,
      // initCurrentUsers,
      // prevClick,
      // nextClick,
      // changeDataLen,
      activeVideo,
      activeMute,
      publishScreenShare,
    };
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
