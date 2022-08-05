<template>
  <section class="background">
    <!-- <HostView
        v-if="state.isHost"
        :dataLen="dataLen"
        :currentStudents="currentStudents"
        :initCurrentStudents="initCurrentStudents"
        :prevClick="prevClick"
        :nextClick="nextClick"
        @changeDataLen="changeDataLen"
      />
    <UserView
      v-else
      :dataLen="dataLen"
      :currentStudents="currentStudents"
      :initCurrentStudents="initCurrentStudents"
      :prevClick="prevClick"
      :nextClick="nextClick"
      @changeDataLen="changeDataLen"
    /> -->
    <div id="session" v-if="state.session">
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
        <user-video :stream-manager="state.mainStreamManager" />
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
    </div>
  </section>
</template>

<script>
import { reactive, ref, onBeforeUnmount } from "vue";
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
// import HostView from "@/components/class/HostView.vue";
// import UserView from "@/components/class/UserView.vue";
import UserVideo from "@/components/class/UserVideo";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "InClassView",
  components: {
    // HostView,
    // UserView,
    UserVideo,
  },
  setup() {
    // const OPENVI00DU_SERVER_SECRET = process.env.VUE_APP_OV_SECRET;

    // 테스트용
    const OPENVIDU_SERVER_URL = "https://i7b201.p.ssafy.io";
    const OPENVIDU_SERVER_SECRET = "BANGGWAWO_SECRET";
    const OV = new OpenVidu();

    const state = reactive({
      OV: OV,
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
      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      joinedPlayerNumbers: 0,

      isHost: true,
    });

    /* 
  닉네임:사용자
  sessionName : 방 이름?
  token : 토큰 들어오는데 이건 입장 할때마다 바뀌는 값
  userName : 아이디인데 아마 로그인할대 아이디로 쓸듯?
*/

    // 사용자가 방에 참여하겠다는 버튼 누를때마다 호출
    const joinSession = () => {
      console.log("join session");
      // Opnevidu 객체 가져오기
      state.OV = new OpenVidu();
      // 초기화
      state.session = state.OV.initSession();
      // 새로운 Stream을 구독하고 subscribers배열에 저장
      state.session.on("streamCreated", ({ stream }) => {
        const subscriber = state.session.subscribe(stream);
        state.subscribers.push(subscriber);
        if (subscriber.videos !== []) state.joinedPlayerNumbers++;
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

      console.log("sessionid", state.mySessionId);
      console.log("user name", state.myUserName);
      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      // 세션에 연결하려면 서버에 토큰 요청 => 백엔드가 처리해줘야 할 부분, 일단 임시 테스트
      getToken(state.mySessionId).then((token) => {
        state.session
          .connect(token, { clientData: state.myUserName })
          .then(() => {
            console.log("getToken2222222222");
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
            console.log("p", publisher);
            state.mainStreamManager = publisher;
            state.publisher = publisher;
            console.log("state.pu", state.publisher);

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
      window.addEventListener("beforeunload", leaveSession);
    };

    const leaveSession = () => {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (state.session) {
        state.session.disconnect();
      }
      state.session = undefined;
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

    joinSession();
    onBeforeUnmount(() => {
      state.joinedPlayerNumbers = 0;
      leaveSession();
    });

    const dataLen = ref(state.isHost ? 12 : 4);
    const dataIdx = ref(0);
    const students = ref([
      {
        id: 1,
        name: "김수빈수빈1",
      },
      {
        id: 2,
        name: "김수빈수빈2",
      },
      {
        id: 3,
        name: "김수빈수빈3",
      },
      {
        id: 4,
        name: "김수빈수빈4",
      },
      {
        id: 5,
        name: "김수빈수빈5",
      },
      {
        id: 6,
        name: "김수빈수빈6",
      },
      {
        id: 7,
        name: "김수빈수빈7",
      },
      {
        id: 8,
        name: "김수빈수빈8",
      },
      {
        id: 9,
        name: "김수빈수빈9",
      },
      {
        id: 10,
        name: "김수빈수빈10",
      },
      {
        id: 11,
        name: "김수빈수빈11",
      },
      {
        id: 12,
        name: "김수빈수빈12",
      },
      {
        id: 13,
        name: "김수빈수빈13",
      },
      {
        id: 14,
        name: "김수빈수빈14",
      },
      {
        id: 15,
        name: "김수빈수빈15",
      },
    ]);

    const currentStudents = ref([]);

    const initCurrentStudents = () => {
      const tempArr = [];
      for (
        let i = dataIdx.value;
        i < Math.min(dataIdx.value + dataLen.value, students.value.length);
        i++
      ) {
        tempArr.push(students.value[i]);
      }
      currentStudents.value = tempArr;
    };

    const nextClick = () => {
      if (dataIdx.value + dataLen.value >= students.value.length) return;
      dataIdx.value += dataLen.value;
      const currentTempArr = [];
      for (
        let i = dataIdx.value;
        i < Math.min(dataIdx.value + dataLen.value, students.value.length);
        i++
      ) {
        currentTempArr.push(students.value[i]);
      }
      currentStudents.value = currentTempArr;
    };

    const prevClick = () => {
      if (dataIdx.value - dataLen.value < 0) return;
      dataIdx.value -= dataLen.value;
      const currentTempArr = [];
      for (
        let i = Math.max(0, dataIdx.value);
        i < dataIdx.value + dataLen.value;
        i++
      ) {
        currentTempArr.push(students.value[i]);
      }
      currentStudents.value = currentTempArr;
    };

    const changeDataLen = (param) => {
      dataLen.value = param;
    };

    return {
      state,
      dataLen,
      currentStudents,
      initCurrentStudents,
      prevClick,
      nextClick,
      changeDataLen,
    };
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
