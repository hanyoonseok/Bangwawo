<template>
  <div class="character-modal" v-if="character === true">
    <div class="content-wrapper">
      <button class="back-btn">
        <i class="fa-solid fa-xmark"></i>
      </button>
      <div class="box">
        <div class="left-box">
          <div class="color-box">
            <h4 class="color-title">몸</h4>
            <div class="color-palette">
              <span class="color red"></span>
              <span class="color orange"></span>
              <span class="color yellow"></span>
              <span class="color green"></span>
              <span class="color blue"></span>
              <span class="color purple"></span>
              <span class="color pink"></span>
            </div>
          </div>
          <div class="color-box">
            <h4 class="color-title">옷</h4>
            <div class="color-palette">
              <span class="color red"></span>
              <span class="color orange"></span>
              <span class="color yellow"></span>
              <span class="color green"></span>
              <span class="color blue"></span>
              <span class="color purple"></span>
              <span class="color pink"></span>
            </div>
          </div>
          <div class="color-box">
            <h4 class="color-title">머리</h4>
            <div class="color-palette">
              <span class="color red"></span>
              <span class="color orange"></span>
              <span class="color yellow"></span>
              <span class="color green"></span>
              <span class="color blue"></span>
              <span class="color purple"></span>
              <span class="color pink"></span>
            </div>
          </div>
          <div class="color-box">
            <h4 class="color-title">악세사리</h4>
            <div class="color-palette">
              <span class="color red"></span>
              <span class="color orange"></span>
              <span class="color yellow"></span>
              <span class="color green"></span>
              <span class="color blue"></span>
              <span class="color purple"></span>
              <span class="color pink"></span>
            </div>
          </div>
        </div>
        <div class="right-box" id="canvas">오리 캐릭터 들어가야할곳</div>
      </div>
      <button class="save-btn">저장하기</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "CharacterModal",
  props: ["character"],
};
</script>

<style lang="scss" scoped src="@/css/character.scss"></style>
