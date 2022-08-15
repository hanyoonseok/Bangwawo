<template>
  <div id="canvas_main_div">
    <div class="bg-game"><img src="@/assets/gameBg.gif" /></div>
    <canvas id="canvas" width="300" height="700"></canvas>
    <div class="notice-wrapper" v-if="state.isGameOver">
      <div class="game-over">
        <img src="@/assets/crying-duck.png" />
        Game Over
        <div class="user-btn" @click="restartGame">
          <i class="fa-solid fa-arrows-rotate" style="font-size: 25px"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, reactive } from "vue";
// import { useEventListener } from "@vueuse/core";

export default {
  setup() {
    const state = reactive({
      timer: 0,
      cactusarr: [],
      jumptimer: 0,
      animation: null,
      ctx: null,
      canvas: null,
      jump: false,
      spacecnt: 0,
      isGameOver: false,
      score: 0,
    });

    const img1 = new Image();
    img1.src = "./ongduckYellowdot.png";

    const img2 = new Image();
    img2.src = "./cactus.png";

    const ongduck = {
      x: 80,
      y: 200,
      width: 70,
      height: 60,
      draw() {
        // ctx.fillRect(this.x, this.y, this.width, this.height);
        state.ctx.drawImage(img1, this.x, this.y, this.width, this.height);
      },
    };

    class Cactus {
      constructor() {
        this.x = 750;
        this.y = 235;
        this.width = 20;
        this.height = 25;
      }
      draw() {
        state.ctx.drawImage(img2, this.x, this.y, this.width, this.height);
      }
    }

    const jump = () => {
      state.spacecnt = 1;
      state.jump = true;
    };
    onMounted(() => {
      start();
    });
    const start = () => {
      state.canvas = document.getElementById("canvas");
      state.ctx = state.canvas.getContext("2d");

      state.canvas.width = window.innerWidth - 100;
      state.canvas.height = window.innerHeight - 100;
      state.isGameGoing = true;
      frame();
    };

    const frame = () => {
      state.animation = requestAnimationFrame(frame);
      state.timer++;
      state.ctx.clearRect(0, 0, state.canvas.width, state.canvas.height);
      if (state.timer % 110 === 0) {
        var cactus = new Cactus();
        state.cactusarr.push(cactus);
      }
      // 장애물 이동 기능
      state.cactusarr.forEach((a, i, o) => {
        //장애물의 x좌표가 60미만이면 제거

        if (a.x < 60) {
          o.splice(i, 1);
        }
        a.x -= 3;

        //충돌 감지
        crash(ongduck, a);

        a.draw();
      });

      // 점푸 기능
      if (state.jump) {
        ongduck.y -= 3;
        state.jumptimer++;
      }
      console.log(state.jumptimer);

      if (state.jumptimer > 35) {
        state.jump = false;
        state.jumptimer = 0;
      }
      if (ongduck.y == 200) {
        state.spacecnt = 0;
      }
      if (!state.jump && ongduck.y < 200) {
        if (ongduck.y < 200) ongduck.y += 3;
      }

      // 온덕이  그려주기
      ongduck.draw();
    };

    //충돌 확인
    const crash = (ongduck, cactus) => {
      const xdist = cactus.x - (ongduck.x + ongduck.width);
      let nextcrash = true;
      if (cactus.x + cactus.width < ongduck.x + 15) nextcrash = false;

      const ydist = cactus.y - (ongduck.y + ongduck.height);
      if (xdist < 1 && nextcrash && ydist < 1) {
        console.log("xdist=" + xdist);
        console.log("ydist=" + ydist);
        //게임 종료
        cancelAnimationFrame(state.animation);

        state.isGameOver = true;
      }
    };
    document.addEventListener("keydown", function (e) {
      if (e.code === "Space" && state.spacecnt == 0) {
        state.spacecnt = 1;
        state.jump = true;
      }
    });

    const restartGame = () => {
      state.isGameOver = false;
      state.ctx.clearRect(0, 0, state.canvas.width, state.canvas.height);
      state.ctx.beginPath();
      state.cactusarr = [];
      frame();
      // 하하 재시작을 어떻게 해줘야한담??
      // 데이터를 초기화해주자.
    };

    return {
      restartGame,
      frame,
      state,
      crash,
      ongduck,
      jump,
      start,
    };
  },
};
</script>

<style scoped src="@/css/secret-friend.scss" lang="scss"></style>
