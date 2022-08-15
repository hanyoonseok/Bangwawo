<template>
  <div id="canvas_main_div">
    <div class="bg-game"><img src="@/assets/gameBg.gif" /></div>
    <canvas id="canvas" width="300" height="700"></canvas>
  </div>
</template>

<script>
import { onMounted, reactive } from "vue";

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
    });

    const img1 = new Image();
    img1.src = "./ongduckYellowdot.png";

    const img2 = new Image();
    img2.src = "./cactus.png";

    const ongduck = {
      x: 80,
      y: 190,
      width: 78,
      height: 70,
      draw() {
        state.ctx.fillStyle = "green";
        // ctx.fillRect(this.x, this.y, this.width, this.height);
        state.ctx.drawImage(img1, this.x, this.y, this.width, this.height);
      },
    };

    class Cactus {
      constructor() {
        this.x = 700;
        this.y = 225;
        this.width = 20;
        this.height = 25;
      }
      draw() {
        state.ctx.drawImage(img2, this.x, this.y, this.width, this.height);
      }
    }
    const frame = () => {
      state.animation = requestAnimationFrame(frame);
      state.timer++;
      state.ctx.clearRect(0, 0, state.canvas.width, state.canvas.height);

      if (state.timer % 200 === 0) {
        var cactus = new Cactus();
        state.cactusarr.push(cactus);
      }
      // 장애물 이동 기능
      state.cactusarr.forEach((a, i, o) => {
        //장애물의 x좌표가 0미만이면 제거
        if (a.x < 0) {
          o.splice(i, 1);
        }
        a.x -= 2;

        //충돌 감지
        crash(ongduck, a);

        a.draw();
      });

      // 점푸 기능
      if (state.jump) {
        ongduck.y -= 3;
        state.jumptimer++;
      }
      if (state.jumptimer > 40) {
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
      var xdist = cactus.x - (ongduck.x + ongduck.width);
      var nextcrash = true;
      if (cactus.x + cactus.width < ongduck.x) nextcrash = false;

      var ydist = cactus.y - (ongduck.y + ongduck.height);
      if (xdist < 1 && nextcrash && ydist < 1) {
        console.log("xdist=" + xdist);
        console.log("ydist=" + ydist);
        //게임 종료
        cancelAnimationFrame(state.animation);
        state.ctx.clearRect(0, 0, state.canvas.width, state.canvas.height);

        const element = document.getElementById("canvas_main_div");
        element.innerHTML = "<div>Space를 누르면 다시시작 됩니다.!<div>";
        //다시시작
        document.addEventListener("keydown", function (e) {
          if (e.code === "Space") {
            window.location.reload();
          }
        });
      }
    };
    onMounted(() => {
      state.canvas = document.getElementById("canvas");
      state.ctx = state.canvas.getContext("2d");

      state.canvas.width = window.innerWidth - 100;
      state.canvas.height = window.innerHeight - 100;

      frame();
    });
    document.addEventListener("keydown", function (e) {
      if (e.code === "Space" && state.spacecnt == 0) {
        state.spacecnt = 1;
        state.jump = true;
      }
    });

    return {
      frame,
      state,
      ongduck,
    };
  },
};
</script>

<style scoped src="@/css/secret-friend.scss" lang="scss"></style>
