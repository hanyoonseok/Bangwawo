<template>
  <video autoplay id="video" />
</template>

<script>
import * as faceapi from "face-api.js";
import { useStore } from "vuex";

export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
    onEmotion: Boolean,
  },

  data() {
    return {
      store: null,
    };
  },

  created() {
    this.store = useStore();
  },

  mounted() {
    this.streamManager.addVideoElement(this.$el);

    const video = document.getElementById("video");

    Promise.all([
      faceapi.nets.tinyFaceDetector.loadFromUri("/models"),
      faceapi.nets.faceLandmark68Net.loadFromUri("/models"),
      faceapi.nets.faceRecognitionNet.loadFromUri("/models"),
      faceapi.nets.faceExpressionNet.loadFromUri("/models"),
    ]).then(startVideo);

    function startVideo() {
      navigator.getUserMedia(
        { video: {} },
        (stream) => (video.srcObject = stream),
        (err) => console.error(err),
      );
    }

    video.addEventListener("play", () => {
      if (!this.onEmotion) return;
      const canvas = faceapi.createCanvasFromMedia(video);

      //document.body.append(canvas);
      // const displaySize = { width: video.width, height: video.height };
      const displaySize = { width: 300, height: 200 };
      faceapi.matchDimensions(canvas, displaySize);
      setInterval(async () => {
        const detections = await faceapi
          .detectAllFaces(video, new faceapi.TinyFaceDetectorOptions())
          .withFaceLandmarks()
          .withFaceExpressions();
        const resizedDetections = faceapi.resizeResults(
          detections,
          displaySize,
        );
        if (resizedDetections.length > 0) {
          // console.log(resizedDetections[0].expressions);
          console.log(resizedDetections[0].expressions);
          this.store.commit(
            "root/addEmotion",
            resizedDetections[0].expressions,
          );
        }
        //canvas.getContext("2d").clearRect(0, 0, canvas.width, canvas.height);
        faceapi.draw.drawFaceExpressions(canvas, resizedDetections); // 감정 상태
      }, 10000);
    });
  },
};
</script>
<style scoped>
video {
  object-fit: cover;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 100%;
  border-radius: 20px;
}
canvas {
  position: absolute;
  top: 0;
  left: 0;
}
</style>
