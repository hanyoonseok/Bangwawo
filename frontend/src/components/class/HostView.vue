<template>
  <section class="main-section">
    <section class="top-section">
      <article class="top-left host">
        <div class="user-card"></div>
        <div class="user-card"></div>
        <div class="user-card"></div>
        <div class="user-card"></div>
        <div class="user-card"></div>
        <div class="user-card"></div>
        <div class="user-card"></div>
        <div class="user-card"></div>
      </article>

      <article class="top-right">
        <div class="top-article top">
          <div class="title-wrapper">
            <label>참가자</label>
            <i class="fa-solid fa-minus"></i>
          </div>
          <div class="list-wrapper">
            <div class="list-item">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
              </div>
            </div>
            <div class="list-item">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
              </div>
            </div>
            <div class="list-item">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
              </div>
            </div>
            <div class="list-item">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
              </div>
            </div>
            <div class="list-item">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
              </div>
            </div>
            <div class="list-item">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
              </div>
            </div>
            <div class="list-item">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
              </div>
            </div>
          </div>
        </div>
        <div class="top-article bot">
          <div class="title-wrapper">
            <label>채팅</label>
            <i class="fa-solid fa-minus"></i>
          </div>
          <div class="list-wrapper test">
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat"
                  >채팅내용ㅇ하나둘셋넷다섯여a1 1 1 1 1 1 1 1 1 1 1 1 dfas dfsad
                  sfsdafasdasf섯</label
                >
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat"
                  >채팅내용ㅇ하나둘셋넷다섯여a1 1 1 1 1 1 1 1 1 1 1 1 dfas dfsad
                  sfsdafasdasf섯</label
                >
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat"
                  >채팅내용ㅇ하나둘셋넷다섯여a1 1 1 1 1 1 1 1 1 1 1 1 dfas dfsad
                  sfsdafasdasf섯</label
                >
              </div>
              <label class="list-item-time">4분전</label>
            </div>
          </div>
          <input
            type="text"
            class="chat-input"
            placeholder="채팅메시지를 입력하세요."
          />
        </div>
      </article>
    </section>

    <section class="bot-section">
      <article class="bot-left">
        <button class="option-btn red">
          <i class="fa-solid fa-microphone-slash"></i>
          &nbsp;음소거 해제
        </button>
        <button class="option-btn">
          <i class="fa-solid fa-video"></i>
          &nbsp;비디오 시작
        </button>
        <button class="option-btn">
          <i class="fa-solid fa-arrow-up-from-square"></i>
          &nbsp;화면 공유
        </button>
        <button class="option-btn">&nbsp;OX 퀴즈</button>
        <i class="fa-solid fa-xmark xmark"></i>
      </article>

      <article class="bot-right">
        <button class="option-btn">
          <i class="fa-solid fa-user"></i>
          &nbsp;참가자
        </button>
        <button class="option-btn">
          <i class="fa-solid fa-comments"></i>
          &nbsp;채팅
        </button>
      </article>
    </section>
  </section>
</template>

<script>
export default {
  name: "HostView",
  setup() {},
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
