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
</template>

<script>
export default {
  name: "ParticipantsList",
  props: ["state", "toggleParticipants"],
  setup() {},
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
