<template>
  <form
    v-show="state.isChatOpen"
    :class="{
      'top-article': true,
      bot: true,
      expand: state.isChatOpen && !state.isTopOpen,
    }"
    @submit.prevent="sendMsg"
  >
    <div class="title-wrapper">
      <label>채팅</label>
      <i class="fa-solid fa-xmark xmark" @click="toggleChat()"></i>
    </div>
    <div class="list-wrapper test">
      <div class="list-item chat" v-for="(chat, i) in chats" :key="i">
        <img src="@/assets/profile.png" />
        <div class="list-item-center">
          <label class="list-item-nickname">{{ chat.sender }}</label>
          <label class="list-item-chat">{{ chat.msg }}</label>
        </div>
        <label class="list-item-time">{{ chat.date }}</label>
      </div>
    </div>
    <input
      type="text"
      class="chat-input"
      placeholder="채팅메시지를 입력하세요."
      v-model="currentMsg"
    />
  </form>
</template>

<script>
import { ref } from "vue";
export default {
  name: "ChatForm",
  props: ["state", "toggleChat", "session", "chats"],
  setup(props) {
    const currentMsg = ref("");
    const sendMsg = () => {
      console.log(currentMsg.value);
      props.session
        .signal({
          data: currentMsg.value, // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: "my-chat", // The type of message (optional)
        })
        .then(() => {
          console.log("Message successfully sent");
          currentMsg.value = "";
        })
        .catch((error) => {
          console.error(error);
        });
    };

    return {
      currentMsg,
      sendMsg,
    };
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
