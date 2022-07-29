<template>
  <article
    v-show="state.isChatOpen"
    :class="{
      'top-article': true,
      bot: true,
      expand: state.isChatOpen && !state.isTopOpen,
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
</template>

<script>
export default {
  name: "ChatForm",
  props: ["state", "toggleChat"],
  setup() {},
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
