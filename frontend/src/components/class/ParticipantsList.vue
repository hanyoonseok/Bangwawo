<template>
  <article class="top-article top" v-show="state.isParticipantsOpen">
    <div class="title-wrapper">
      <label>참가자</label>
      <i class="fa-solid fa-xmark xmark" @click="toggleParticipants()"></i>
    </div>
    <div class="list-wrapper">
      <div class="list-item">
        <img src="@/assets/profile.png" />
        <div class="list-item-center">
          <label class="list-item-nickname">{{ me }} (나)</label>
        </div>
      </div>
      <div v-for="(item, index) in subs" :key="index">
        <div class="list-item">
          <img src="@/assets/profile.png" />
          <div class="list-item-center">
            <label class="list-item-nickname">{{ item }}</label>
          </div>
        </div>
      </div>
    </div>
  </article>
</template>

<script>
export default {
  name: "ParticipantsList",
  props: ["state", "toggleParticipants", "me", "subs"],
  setup(props) {
    console.log("me", props.me);
    console.log("subs", props.subs);
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
