<template>
  <section class="main-section">
    <article class="title-wrapper">
      <h2>비밀친구 대화중</h2>
    </article>
    <article class="user-wrapper" v-if="state.session">
      <SecretCanvas
        :parts="student"
        class="user-card"
        :user="stu"
        id="stu"
        :isTalking="state.isTalking"
      />
      <SecretCanvas
        :parts="volunteer"
        class="user-card"
        :user="vol"
        id="vol"
        :isTalking="state.isTalking"
      />
    </article>
    <div id="session" v-if="state.session">
      <div id="session-header">
        <h1 id="session-title">{{ state.mySessionId }}</h1>
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
    <article class="btn-wrapper">
      <button class="option-btn" @click="clickMute" v-if="state.audioState">
        <i class="fa-solid fa-microphone-slash"></i>
        &nbsp;음소거
      </button>
      <button class="option-btn" @click="clickMute" v-else>
        <i class="fa-solid fa-microphone-slash"></i>
        &nbsp;음소거 해제
      </button>
      <button class="option-btn" @click="leaveSession">
        <i class="fa-solid fa-xmark"></i> &nbsp;대화 종료
      </button>
    </article>
  </section>
</template>

<script>
import SecretCanvas from "@/components/secret/SecretCanvas.vue";
import { reactive, onBeforeUnmount, onMounted } from "vue";
import { useStore } from "vuex";

import { useRouter } from "vue-router";

import axios from "axios";
import { OpenVidu } from "openvidu-browser";
axios.defaults.headers.post["Content-Type"] = "application/json";
import UserVideo from "@/components/secret/UserVideo";

// SpeechRecognition 인터페이스 초기화
const Recognition = window.SpeechRecognition || window.webkitSpeechRecognition;
const recognition = new Recognition();

export default {
  name: "SecretTalkView",
  components: {
    SecretCanvas,
    UserVideo,

    // SCanvas,
  },
  setup() {
    const router = useRouter();

    const store = useStore();
    let model = store.state.root.user.model;

    //초기 캐릭터 색 : 백엔드에 저장한 db에서 받아올것임
    let student = reactive(store.state.root.user.characterColors);
    let volunteer = reactive(store.state.root.user.characterColors);

    let stu = "stu";
    let vol = "vol";

    // 테스트용
    // const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
    // const OPENVIDU_SERVER_SECRET = "MY_SECRET";

    const OPENVIDU_SERVER_URL = process.env.VUE_APP_API_URL;
    const OPENVIDU_SERVER_SECRET = process.env.VUE_APP_OV_SECRET;
    const OV = new OpenVidu();

    const state = reactive({
      OV: OV,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      mySessionId: "SessionX",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      joinedPlayerNumbers: 0,
      audioState: true,
      isHost: true,
      danger: 0,
      isTalking: false,
    });

    //위험 단어 리스트
    const dangerWord = ["안녕", "자살", "타살", "괴롭힘", "왕따", "따돌림"];

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
        console.log("subscriber", subscriber);
        // subscriber.subscribeToVideo(false); // true to enable the video, false to disable it
        state.subscribers.push(subscriber);
        console.log("subscribers", state.subscribers);
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

      //음성 감지
      state.session.on("publisherStartSpeaking", (event) => {
        console.log(
          "User " + event.connection.connectionId + " start speaking",
        );
        voiceDetection();
      });

      //음성 감지 종료
      state.session.on("publisherStopSpeaking", (event) => {
        console.log("User " + event.connection.connectionId + " stop speaking");
        state.isTalking = false;
        recognition.stop();
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
              publishVideo: false, // Whether you want to start publishing with your video enabled or not
              resolution: "600x320", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
              filter: {
                type: "GStreamerFilter",
                options: {
                  command: "pitch pitch=1.4",
                },
              },
            });
            console.log("p", publisher);
            // publisher.publishVideo(false); // true to enable the video track, false to disable it
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
      console.log("state", state);
      window.removeEventListener("beforeunload", leaveSession);
      console.log("대화종료 후 위험단어 수 : ", state.danger);
      router.push({ name: "secret" });
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

    const clickMute = () => {
      state.audioState = !state.audioState;
      state.publisher.publishAudio(state.audioState);
    };

    joinSession();
    onBeforeUnmount(() => {
      state.joinedPlayerNumbers = 0;
      leaveSession();
    });
    onMounted(() => {
      // applyVoiceFilter();
    });

    if (!Recognition) {
      alert(
        "Speech Recognition API is not supported in this browser, try chrome",
      );
    }

    recognition.lang = "ko-KR"; // 한국어 지정
    recognition.onresult = (event) => {
      const text = event.results[0][0].transcript;
      console.log("transcript", text);
      for (const item of dangerWord) {
        if (text.indexOf(item) >= 0) {
          state.danger++;
          console.log("위험하다아ㅏㅏ");
        }
      }
    };

    recognition.onspeechend = () => {
      console.log("stopped");
    };

    recognition.onnomatch = (event) => {
      console.log(event, "no match");
    };

    recognition.onstart = () => {};

    recognition.onend = () => {
      console.log("end");
      recognition.stop();
    };

    recognition.onerror = (event) => {
      console.log("error", event);
    };

    const voiceDetection = () => {
      console.log("start");
      state.isTalking = true;
      recognition.start();
    };
    const applyVoiceFilter = () => {
      state.publisher.stream
        .applyFilter("GStreamerFilter", { command: "pitch pitch=1.4" })
        .then((f) => {
          if (f.type === "FaceOverlayFilter") {
            f.execMethod("setOverlayedImage", {
              uri: "https://cdn.pixabay.com/photo/2017/09/30/09/29/cowboy-hat-2801582_960_720.png",
              offsetXPercent: "-0.1F",
              offsetYPercent: "-0.8F",
              widthPercent: "1.5F",
              heightPercent: "1.0F",
            });
          }
        });
    };

    return {
      state,
      student,
      volunteer,
      stu,
      vol,
      model,
      applyVoiceFilter,
      leaveSession,
      clickMute,
    };
  },
};
</script>

<style scoped src="@/css/secret-friend.scss" lang="scss"></style>
