<template>
  <section class="main-section">
    <article class="title-wrapper">
      <h2>비밀친구 대화중</h2>
    </article>
    <article class="user-wrapper" v-if="state.session">
      <SecretCanvas
        v-if="publisher"
        :parts="publisher"
        :class="{
          'user-card': true,
          'talking-active': state.isPublisherTalking,
        }"
        :backgroundPubImg="state.backgroundPubImg"
        user="publisher"
        id="publisher"
        :isPublisherTalking="state.isPublisherTalking"
        :isSubscribeTalking="state.isSubscribeTalking"
        :isPublisher="true"
      />
      <SecretCanvas
        v-if="subscriber && state.subscribers.length > 0"
        :parts="subscriber"
        :class="{
          'user-card': true,
          'talking-active': state.isSubscribeTalking,
        }"
        user="subscriber"
        id="subscriber"
        :backgroundSubImg="state.backgroundSubImg"
        :isPublisherTalking="state.isPublisherTalking"
        :isSubscribeTalking="state.isSubscribeTalking"
        :isPublisher="false"
      />
      <button @click="activeBackgroudSelect" class="changeBg-btn">
        배경바꾸기
      </button>
      <div class="background-area">
        <div v-if="state.showBackgroundSelection" class="selection-area">
          <div class="bg-wrapper">
            <div v-for="(bg, index) in backgrounds" :key="index">
              <img
                class="selection-background"
                :src="bg"
                @click="changeBgImg(bg)"
              />
            </div>
          </div>
        </div>
      </div>
    </article>

    <div id="session" v-if="state.session">
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
import { reactive, onBeforeUnmount, ref, watch } from "vue";
import { useStore } from "vuex";
import { useRouter, useRoute } from "vue-router";
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
    const route = useRoute();
    const vid = route.params.vid;
    const store = useStore();
    const user = ref(store.state.root.user);
    const sid = route.params.sid;
    let model = store.state.root.user.model;

    //초기 캐릭터 색 : 백엔드에 저장한 db에서 받아올것임

    let publisher = ref(null);
    let subscriber = ref(null);
    // 테스트용
    const getStudentInfo = async () => {
      store.dispatch("root/getStudentInfo", sid).then((res) => {
        if (user.value.userType === "volunteer")
          subscriber.value = res.data.user.character;
        else {
          publisher.value = res.data.user.character;
        }
        state.studentName = res.data.user.nickname;
      });
    };
    const getVolunteerInfo = async () => {
      store.dispatch("root/getVolunteerInfo", vid).then((res) => {
        if (user.value.userType === "volunteer")
          publisher.value = res.data.user.character;
        else {
          subscriber.value = res.data.user.character;
        }
      });
    };

    getStudentInfo();
    getVolunteerInfo();

    const OPENVIDU_SERVER_URL = process.env.VUE_APP_OV_DOMAIN;
    const OPENVIDU_SERVER_SECRET = process.env.VUE_APP_OV_SECRET;
    const OV = new OpenVidu();

    const state = reactive({
      OV: OV,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscriber: undefined,
      subscribers: [],
      mySessionId: `${vid}with${sid}`,
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      joinedPlayerNumbers: 0,
      audioState: true,
      isHost: true,
      danger: 0,
      isSubscribeTalking: false,
      isPublisherTalking: false,
      backgroundPubImg: "./../../secretBg.png",
      backgroundSubImg: "./../../secretBg.png",
      showBackgroundSelection: false,
      studentName: "",
    });

    // 배경리스트
    const backgrounds = [
      "./../../secretBg.png",
      "./../../secretBg2.png",
      "./../../secretBg3.png",
      "./../../secretBg4.png",
      "./../../secretBg5.png",
      "./../../secretBg6.png",
    ];

    const changeBgImg = (bg) => {
      state.backgroundPubImg = bg;
      console.log("변경하자.", state.backgroundPubImg);
    };

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
        console.log(state.publisher.stream.connection.connectionId);
        voiceDetection();
        console.log(state.session);
        console.log("event찍어본다", event);
        if (
          state.publisher.stream.connection.connectionId ===
          event.connection.connectionId
        ) {
          state.isPublisherTalking = true;
          console.log("이건내목소리다.");
        } else {
          console.log("내목소리아니얌!!");
          state.isSubscribeTalking = true;
        }
      });
      // publisher.on("publisherStartSpeaking", (event) => {
      //   console.log(
      //     "User " + event.connection.connectionId + " start speaking",
      //   );
      //   state.isPublisherTalking = true;
      //   console.log("이건내목소리다.");
      //   voiceDetection();
      // });
      // publisher.on("publisherStopSpeaking", (event) => {
      //   console.log(
      //     "User " + event.connection.connectionId + " start speaking",
      //   );
      //   state.isPublisherTalking = false;
      // });
      // state.subscriber.on("publisherStartSpeaking", (event) => {
      //   console.log(
      //     "User " + event.connection.connectionId + " start speaking",
      //   );
      //   state.isSubscribeTalking = true;
      //   console.log("상대목소리다!.");
      // });

      // state.subscriber.on("publisherStopSpeaking", (event) => {
      //   console.log(
      //     "User " + event.connection.connectionId + " start speaking",
      //   );
      //   state.isSubscribeTalking = false;
      // });

      // 음성 감지 종료
      state.session.on("publisherStopSpeaking", (event) => {
        console.log("User " + event.connection.connectionId + " stop speaking");
        if (
          state.publisher.stream.connection.connectionId ===
          event.connection.connectionId
        ) {
          state.isPublisherTalking = false;
        } else {
          state.isSubscribeTalking = false;
        }
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

    const activeBackgroudSelect = () => {
      state.showBackgroundSelection = !state.showBackgroundSelection;
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

      if (user.value.userType === "student") {
        if (dangerCnt[0] > 0) {
          // 자녀의 상담중 ${word}이(가) 감지되었습니다.
          studentDangerWord("욕설");
        }
        if (dangerCnt[1] > 0) {
          studentDangerWord("학교 생활 관련 위험용어");
        }
        if (dangerCnt[2] > 0) {
          studentDangerWord("부적절 용어");
        }
        if (dangerCnt[3] > 0) {
          studentDangerWord("심각 상태임을 짐작할 수 있는 위험용어");
        }
      }
      if (user.value.userType === "student") {
        router.push({ name: "secret" });
      } else {
        router.push({ name: "classlist" });
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
            console.log(response);
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

    const clickMute = () => {
      state.audioState = !state.audioState;
      state.publisher.publishAudio(state.audioState);
    };

    joinSession();
    onBeforeUnmount(() => {
      state.joinedPlayerNumbers = 0;
      leaveSession();
    });

    if (!Recognition) {
      alert(
        "Speech Recognition API is not supported in this browser, try chrome",
      );
    }

    watch(
      () => state.backgroundPubImg,
      (cur) => {
        console.log(cur);
        // 만약 배경이미지를 바꾼다면 메세지를보내준다.
        state.session
          .signal({
            data: cur, // Any string (optional)
            to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
            type: "bg-change", // The type of message (optional)
          })
          .then(() => {
            console.log("Message successfully sent");
          })
          .catch((error) => {
            console.error(error);
          });
      },
    );
    // 상대가 배경을 변경했다는 메세지 받기
    state.session.on("signal:bg-change", (event) => {
      if (
        state.publisher.stream.connection.connectionId !==
        event.from.connectionId
      )
        state.backgroundSubImg = event.data;
    });

    //위험 단어 리스트

    const dangerWord = [
      ["시발", "새끼", "병신", "지랄", "좆까", "꺼져", "닥쳐"],
      ["괴롭힘", "왕따", "따돌림", "일진"],
      ["담배", "술", "마약"],
      ["자살", "폭력", "성폭행", "성폭력", "성희롱"],
    ];

    const dangerCnt = reactive([0, 0, 0, 0]);

    recognition.lang = "ko-KR"; // 한국어 지정
    recognition.onresult = (event) => {
      const text = event.results[0][0].transcript;
      console.log("transcript", text);
      for (let i = 0; i < dangerWord.length; ++i) {
        for (const item of dangerWord[i]) {
          if (text.indexOf(item) >= 0) {
            dangerCnt[i]++;
            console.log("i번째 위험용어 감지했다아아" + i);
          }
        }
      }
    };
    const studentDangerWord = async (item) => {
      const secretChatDto = {
        sname: state.studentName,
        sccontent: item,
        sid: sid,
      };
      await store
        .dispatch("root/studentDangerWord", secretChatDto)
        .then((res) => console.log(res));
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
      recognition.start();
    };

    return {
      state,
      publisher,
      subscriber,
      model,
      // applyVoiceFilter,
      leaveSession,
      dangerWord,
      clickMute,
      activeBackgroudSelect,
      studentDangerWord,
      dangerCnt,
      getVolunteerInfo,
      getStudentInfo,
      backgrounds,
      changeBgImg,
      vid,
      sid,
    };
  },
};
</script>

<style scoped src="@/css/secret-friend.scss" lang="scss"></style>
