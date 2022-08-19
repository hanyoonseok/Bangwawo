# Ⅰ. 서비스 소개

![image](/uploads/8cd73df23cd3641e54af3bedf9b0e110/image.png)

## 💡 개요

- 방과후에 혼자 있는 학생들의 곁을 지켜줄 비대면 방과후 활동 서비스<br>
- 서비스 명 : 방과워

## 💭 Background

코로나19로 인해 비대면 수업으로 전환되면서 방과후 수업 운영이 어려워졌습니다. 다시 대면으로 전환된 현재에도 여전히 방과후 수업을 운영하지 않는 학교가 대부분입니다. 이로 인해 아이들은 방과후에 혼자 집에 있는 시간이 많아졌습니다.

맞벌이 부부는 아이의 방과후 시간을 사교육으로만 관리하기에 부담이 커 방과후 수업을 선호하고 있지만 방과후 수업 운영이 어려워지면서 맞벌이 부부가 부담할 돌봄 공백이 커진 상태입니다.

또한 코로나19로 대면봉사의 기회가 줄어 비대면 봉사를 희망하는 봉사자들이 많아지게 됐습니다.

따라서 저희는 학생, 학부모, 봉사자를 연결해줄 비대면 방과후 플랫폼 방과워를 제작하게 됐습니다.

- 기사 링크

  ["초등생 3분의 1 방과후 방치 '나홀로 아동'"(종합) | 연합뉴스](https://www.yna.co.kr/view/AKR20160204109551005)

  [방과후학교 코로나에 2년째 스톱...강사도, 학생도 '낙동강 오리알'](https://www.newspim.com/news/view/20210825000945)

  [[출근길 인터뷰] 코로나 시대, 자원봉사 활동 변화...비대면·온라인 강화](https://www.yonhapnewstv.co.kr/news/MYH20211228005000038?did=1825m)

## 🎯 Target

- 아이가 있는 맞벌이 및 저소득층 가구
- 재능기부 및 봉사활동을 원하는 자원봉사자

## 🔑 Key benefits

1. 학생 혼자 있는 문제 해결 가능
2. 방과후 활동의 체계적인 관리

   → 활동이 끝나면 결과를 학부모에게 전송해 수업에 대한 피드백 가능

3. 봉사자에게 재능기부 및 봉사활동 기회 제공
4. 자원봉사포털과 연계를 통해 봉사시간 지급을 통한 봉사자 확보 가능
5. 봉사의 선순환

   → 도움을 받은 학생들이 재능나눔을 통해 사회에 환원하는 나눔의 선순환 유도 가능

6. 시간과 공간의 제약을 없애 봉사에 대한 접근성 향상

# II. 기술 스택

## 📋Management Tool

- 형상 관리 : Gitlab
- 이슈 관리 : Jira
- 커뮤니케이션 : Mattermost, Webex, Notion
- 디자인 : Figma, Adobe Illustrator, Adobe Photoshop, Adobe After Effect, 3D Maya

## 🦊 Front End

- lang: HTML5, CSS3, JAVASCRIPT, Node.js `16.16.0`
- framework: Vue.js (Vue3)
- style : Sass
- http: axios
- formatter : eslint + prettier
- router : vue-router
- state management tool : Vuex4
- Library
  ```jsx
  "@kyvg/vue3-notification": "^2.3.6",
  "@tensorflow/tfjs-node": "^3.19.0",
  "@vue/cli-plugin-router": "^5.0.8",
  "@vue/cli-plugin-vuex": "^5.0.8",
  "@vueuse/core": "^9.1.0",
  "aos": "^3.0.0-beta.6",
  "axios": "^0.27.2",
  "canvas": "^2.9.3",
  "chart.js": "^3.9.1",
  "core-js": "^3.8.3",
  "eslint-config-prettier": "^8.5.0",
  "eslint-plugin-prettier": "^4.2.1",
  "face-api.js": "^0.22.2",
  "jwt-decode": "^3.1.2",
  "moment": "^2.29.4",
  "openvidu-browser": "^2.22.0",
  "sass-loader": "^13.0.2",
  "socket.io-client": "^4.5.1",
  "sockjs-client": "^1.6.1",
  "swiper": "^8.3.1",
  "three": "^0.143.0",
  "v-calendar": "^3.0.0-alpha.8",
  "vue": "^3.2.13",
  "vue-accessible-color-picker": "^4.0.3",
  "vue-awesome-swiper": "^5.0.1",
  "vue-calendar-3": "^1.3.52",
  "vue-carousel": "^0.18.0",
  "vue-chartjs": "^4.1.1",
  "vue-router": "^4.0.13",
  "[vue-socket.io](http://vue-socket.io/)": "^3.0.10",
  "vuex": "^4.0.2",
  "vuex-persistedstate": "^4.1.0",
  "webstomp-client": "^1.2.6"
  "eslint": "^7.32.0",
  "eslint-plugin-vue": "^8.0.3",
  "node-sass": "^7.0.1"
  ```
- API
  - [카카오 로그인](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)
  - [face-api.js(실시간 얼굴 감정상태 인식)](https://justadudewhohacks.github.io/face-api.js/docs/index.html)
  - Web Speech API(STT)
    [GitHub - fresh-web/web-speech: Web Speech API Example](https://github.com/fresh-web/web-speech)
    [Web Speech API - Web APIs | MDN](https://developer.mozilla.org/en-US/docs/Web/API/Web_Speech_API)
  - [three.js](https://threejs.org/)

## 🐳 Back End

- Java open-JDK zulu `8.33.0.1`
- SpringBoot
- Gradle `7.5`
- Lombok `1.18.24`
- JPA `1.0`
- Hibernate (ORM프레임워크)
- Swagger

## 🗂️ DB

- MySQL `8.0.29`

## 🌐 Server

- https (TLS `1.0`)
- Ubuntu `20.0.4`
- Nginx `1.18.0`
- PuTTY `0.77`
- Docker `20.10.17`

## 🔨 IDE

- IntelliJ `2022.1.3`
- MySQL Workbench `8.0.29`
- VSCode `1.69.2`

## 기술 소개
### 1. WebRTC

![image](/uploads/44f0d65568601a7a5684c0b3444355d1/image.png)

#### WebRTC 
WebRTC(Web Real-Time Communication)는 웹 브라우저 간에 플러그인의 도움 없이 서로 통신할 수 있도록 설계된 API이다. <br>
W3C에서 제시된 초안이며, 음성 통화, 영상 통화, P2P 파일 공유 등으로 활용될 수 있다.

![image](/uploads/578aea9254e8f29cbccb649ff6277bc4/image.png)

#### Openvidu

WebRTC를 보다 간단하게 적용할 수 있고, 다양한 프레임워크와 호환성이 높은 openvidu를 사용하여 프로젝트를 진행했습니다.

### 2.Web Socket

![image](/uploads/9efa44ec12633c1b63b8c68b73f12ca2/image.png)

#### 웹소켓(WebSocket)
웹소켓은 하나의 TCP 접속에 전이중 통신 채널을 제공하는 컴퓨터 통신 프로토콜이다. 웹소켓 프로토콜은 2011년 IETF에 의해 RFC 6455로 표준화되었으며 웹 IDL의 웹소켓 API는 W3C에 의해 표준화되고 있다. (이미지 출처 : http://www.secmem.org/blog/2019/08/17/websocket-socketio/)

일반적인 Ajax 통신과 다르게, 특정 end point를 구독하면 종단지점과 연결된 모든 통신을 수신할 수 있으며, 실시간 소통이 가능합니다. 그래서 실시간 채팅등에 많이 활용되고 있습니다.

#### stomp.js 
이 라이브러리를 사용하면 WebSocket을 통해 STOMP 브로커에 연결할 수 있습니다. 이 라이브러리는 모든 현재 프로토콜 변형을 포함하여 완전한 STOMP 사양을 지원합니다. 가장 인기 있는 메시징 브로커는 기본적으로 또는 플러그인을 사용하여 WebSocket을 통해 STOMP 및 STOMP를 지원합니다.

### 3. face API
Face API는 감정 감지를 수행하여 인간 코더의 인식된 주석에 따라 얼굴 표정에서 분노, 경멸, 혐오, 두려움, 행복, 중립, 슬픔 및 놀라움을 감지할 수 있습니다.

![image](/uploads/cb16c8a4139d2ef001fe2af9f6bfdd30/image.png)


### 4. Web Speech API
웹 음성 API는 사용자가 웹 애플리케이션에 음성 데이터를 통합 할 수 있습니다. Web Speech API는 SpeechSynthesis(텍스트 음성 변환) 및 SpeechRecognition(비동기 음성 인식 )의 두 부분으로 구성 됩니다.

### 5. three.js
3차원 그래픽 및 애니메이션을 올리기 위한 js library입니다. <br>
https://threejs.org/docs/index.html#manual/en/introduction/Creating-a-scene


# III. 주요 서비스 화면

##### 자세한 기능은 [시연 시나리오](https://lab.ssafy.com/s07-webmobile1-sub2/S07P12B201/-/blob/master/exec/Banggwawo_4_%EC%8B%9C%EC%97%B0%20%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4.md)를 참고해주세요 🥰



- 온보딩 페이지

  ![온보딩](https://user-images.githubusercontent.com/33210124/185527172-2aad77d5-bb5a-4e9e-b26b-a61719f9332f.png)

- 수업

  ![Untitled](https://user-images.githubusercontent.com/33210124/185527177-8999d47b-3d3b-4033-a7d4-117fd4fd7082.png)

- 비밀친구 (상담)

  ![Untitled](https://user-images.githubusercontent.com/33210124/185527430-07894f36-65f8-4d37-956a-80e031e1d6b5.png)

- 마이페이지

  ![마이페이지캡](https://user-images.githubusercontent.com/33210124/185527439-9689d2f3-94a0-4831-a591-c1e390e35180.png)
  ![마이페이지캡쳐](https://user-images.githubusercontent.com/33210124/185527441-6e8c0a6e-b2f8-4976-81e8-fc86a7c0f859.png)

# IV. 프로젝트 진행

프로젝트는 온+오프라인 병행되었습니다. 프로젝트 진행 방식은 `agail` 이고 jira를 이용하여 스프린트 관리, Notion을 이용하여 자료 공유 및 아카이브를 하였습니다.

## Git Flow

![Untitled 3](https://user-images.githubusercontent.com/33210124/185528436-132b97d1-1c38-4625-8f17-e9364d294b99.png)


## Jira

- 1주차

  <img src="https://user-images.githubusercontent.com/33210124/185528195-e4d3e6b1-d963-4fcb-89c2-3af8e294bb02.png" width="50%">

- 2주차

  <img src="https://user-images.githubusercontent.com/33210124/185528197-a35566d3-e7d0-49ca-b404-7b03d29a565c.png" width="50%">

- 3주차

  <img src="https://user-images.githubusercontent.com/33210124/185528200-7783bcff-bade-49f5-b3ee-f12c2a02e3c5.png" width="50%">

- 4주차

  <img src="https://user-images.githubusercontent.com/33210124/185528202-082bcdae-3a3e-4b6c-ac93-e41e377ea4e0.png" width="50%">

- 5주차

  <img src="https://user-images.githubusercontent.com/33210124/185528432-8df67eef-ffac-41b6-b641-7be4e2c18190.png" width="50%">

- 6주차

  <img src="https://cdn.discordapp.com/attachments/996205976493502554/1010014243141193778/unknown.png" width="50%">


### Notion

![Untitled 9](https://user-images.githubusercontent.com/33210124/185528605-bb4f094b-c54e-4e6d-b9fb-fbb742e139ce.png)

# V. 배포

서버는 AWS EC2 ubuntu를 사용하였습니다.

## 1. docker

- openvidu 서버 : 8443
- 서비스 포트 번호 : 443
- 백엔드 포트 번호 : 8081

## 2. How to

1. 프로젝트 빌드 후 서버에 이동
2. 실행
   1. (백엔드) Docker파일을 이용하여 docker build후 start
   2. (프론트) dist파일을 경로 (/var/www)에 이동 후 nginx restart

서버는 letsencrypt를 사용하여 ssl 설정했습니다.

# VI. UI/UX

## 1. figma

figma를 사용해 홈페이지의 초안을 작성합니다. 관련 레퍼런스를 참고하고, 서비스 특성에 맞게 제작했습니다.

![Untitled 10](https://user-images.githubusercontent.com/33210124/185527887-99970a2f-882d-4376-b82d-8a78f3e59d6b.png)

## 2. design

컴포넌트 단위로 디자인하였고, 마스코트 캐릭터 `옹덕` 도 그렸습니다.

![Untitled 11](https://user-images.githubusercontent.com/33210124/185527892-91ff35e2-7752-43ef-b9da-f4229143a446.png)

![Untitled 12](https://user-images.githubusercontent.com/33210124/185527894-e47b3392-b4e5-4d9d-9f74-2a968cd6406a.png)

# VII. 소감 및 배운점
- 팀 소개
  ![팀소개](https://user-images.githubusercontent.com/33210124/185528250-4866c872-d5aa-495b-8f42-fce73ca8572c.png)

- 김수빈

  막연하게 생각했던 것들이 실제로 구현되는 것을 보니 어떤 주제를 해도 잘했을것같다는 생각이 듭니다. 부족한 저를 이끌어주는 팀원들이 있어서 해낼 수 있었던 것같습니다. 부족한점이 많았는데 팀원들한테 정말 많이 배운 것 같습니다. 특화때도 잘해봅시당~

- 김진호

  처음으로 많은 친구들과 프로젝트를 진행하면서 우리만의 생각으로 서비스를 만든다는게 너무 재미있었습니다. 하지만 7주라는 시간이 너무나 짧아 저스스로가 아직까지 배울점이 너무 많다는점을 느겼고 나중에 기회가 된다면 지금 팀원들가 다시한번 프로젝트를 하고 싶습니다.

- 육다빈

  많은 사람들과 역할을 나눠 제대로 해본 첫 프로젝트라 새롭게 알게 된 게 많았고 재밌었습니다. 유저 관련 로직을 맡았는데 security 관련해서 더 꼼꼼하게 공부해서 다음프로젝트 보완해보고 싶습니다. 7주 사이에 얻어간 것이 많아 좋았고 다들 고생 많으셨습니다 특화때도 화이팅 !

- 이화연

  처음 시작할때는 7주안에 과연 다 완성할 수 있을까 싶었는데 팀원들과 같이 도와가며 개발해서 잘 마무리할 수 있었던 것 같습니다. 깃 플로우, 지라 등 협업툴 사용에 익숙해질 수 있었고 팀원들에게 많이 배웠습니다! 특화때도 열심히 하자 💪

- 정수빈

  많은 것을 배우고 느꼈던 프로젝트였습니다. 처음 해보는 것이 많아서 어려웠지만 재미있었습니다. 모두와 함께 협업해서 즐거웠습니다. 더 배울 수 있어서 좋았습니다. 다들 화이팅!

- 한윤석

  좋은 팀원들과 좋은 서비스를 만들 수 있어서 뜻 깊은 7주였습니다. 팀장으로써 부족한 점이 많았지만 잘 따라와줘서 프로젝트를 잘 마무리할 수 있던 것 같습니다. 이 느낌 그대로 특화 프로젝트때도 같이 힘내보자

