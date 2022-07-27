<template>
  <section class="main-section">
    <section class="top-section">
      <article
        :class="{
          'top-left': true,
          user: true,
          expand: !state.isParticipantsOpen && !state.isChatOpen,
        }"
      >
        <article class="top-article-left top">
          <div class="next-btn-wrapper"><button class="next-btn"></button></div>
          <div class="user-card-wrapper">
            <div class="user-card">s</div>
            <label>김수빈수빈</label>
          </div>
          <div class="user-card-wrapper">
            <div class="user-card"></div>
            <label>김수빈수빈</label>
          </div>
          <div class="user-card-wrapper">
            <div class="user-card"></div>
            <label>김수빈수빈</label>
          </div>
          <div class="user-card-wrapper">
            <div class="user-card"></div>
            <label>김수빈수빈</label>
          </div>
          <div class="user-card-wrapper">
            <div class="user-card"></div>
            <label>김수빈수빈</label>
          </div>
        </article>
        <article class="top-article-left bot">s</article>
      </article>

      <article
        class="top-right"
        v-if="state.isParticipantsOpen || state.isChatOpen"
      >
        <article class="top-article top" v-show="state.isParticipantsOpen">
          <div class="title-wrapper">
            <label>참가자</label>
            <i
              class="fa-solid fa-xmark xmark"
              @click="toggleParticipants()"
            ></i>
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
        </article>
        <article
          v-show="state.isChatOpen"
          :class="{
            'top-article': true,
            bot: true,
            expand: state.isChatOpen && !state.isParticipantsOpen,
          }"
        >
          <div class="title-wrapper">
            <label>채팅</label>
            <i class="fa-solid fa-xmark xmark" @click="toggleChat()"></i>
          </div>
          <div class="list-wrapper test">
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat">채팅내용ㅇ하나둘셋넷다섯여</label>
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat">채팅내용ㅇ하나둘셋넷다섯여</label>
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat">채팅내용ㅇ하나둘셋넷다섯여</label>
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat">채팅내용ㅇ하나둘셋넷다섯여</label>
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat">채팅내용ㅇ하나둘셋넷다섯여</label>
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat">채팅내용ㅇ하나둘셋넷다섯여</label>
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat">채팅내용ㅇ하나둘셋넷다섯여</label>
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat">채팅내용ㅇ하나둘셋넷다섯여</label>
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat">채팅내용ㅇ하나둘셋넷다섯여</label>
              </div>
              <label class="list-item-time">4분전</label>
            </div>
            <div class="list-item chat">
              <img src="@/assets/profile.png" />
              <div class="list-item-center">
                <label class="list-item-nickname">닉네임임임임</label>
                <label class="list-item-chat">채팅내용ㅇ하나둘셋넷다섯여</label>
              </div>
              <label class="list-item-time">4분전</label>
            </div>
          </div>
          <input
            type="text"
            class="chat-input"
            placeholder="채팅메시지를 입력하세요."
          />
        </article>
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
        <i class="fa-solid fa-xmark xmark"></i>
      </article>

      <article class="bot-right">
        <button class="option-btn" @click="toggleParticipants()">
          <i class="fa-solid fa-user"></i>
          &nbsp;참가자
        </button>
        <button class="option-btn" @click="toggleChat()">
          <i class="fa-solid fa-comments"></i>
          &nbsp;채팅
        </button>
      </article>
    </section>
  </section>
</template>

<script>
import { reactive } from "vue";
export default {
  name: "UserView",
  setup() {
    const state = reactive({
      isParticipantsOpen: false,
      isChatOpen: false,
    });

    const toggleParticipants = () => {
      state.isParticipantsOpen = !state.isParticipantsOpen;
    };

    const toggleChat = () => {
      state.isChatOpen = !state.isChatOpen;
    };

    return {
      state,
      toggleParticipants,
      toggleChat,
    };
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
