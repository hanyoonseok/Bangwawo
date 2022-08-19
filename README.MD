# gitlab Readme 작성

- 목차

# Ⅰ. 서비스 소개

<img src="https://user-images.githubusercontent.com/33210124/185524915-f7fd4890-ee7b-4624-aa0a-43f949a21224.png" width="40%">

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
  [출근길 인터뷰] 코로나 시대, 자원봉사 활동 변화...비대면·온라인 강화](https://www.yonhapnewstv.co.kr/news/MYH20211228005000038?did=1825m)

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
- ~~JPA `1.0`~~
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

# III. 주요 서비스 화면

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


## Gira

- 1주차
<img src="https://user-images.githubusercontent.com/33210124/185528195-e4d3e6b1-d963-4fcb-89c2-3af8e294bb02.png" width="40%">

- 2주차

![Untitled 5](https://user-images.githubusercontent.com/33210124/185528197-a35566d3-e7d0-49ca-b404-7b03d29a565c.png)

- 3주차

![Untitled 6](https://user-images.githubusercontent.com/33210124/185528200-7783bcff-bade-49f5-b3ee-f12c2a02e3c5.png)

- 4주차

![Untitled 7](https://user-images.githubusercontent.com/33210124/185528202-082bcdae-3a3e-4b6c-ac93-e41e377ea4e0.png)

- 5주차

![Untitled 8](https://user-images.githubusercontent.com/33210124/185528432-8df67eef-ffac-41b6-b641-7be4e2c18190.png)

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

- 김진호

- 육다빈

- 이화연

- 정수빈

- 한윤석

