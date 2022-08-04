<template>
  <div id="canvas"></div>
</template>

<script>
import { useStore } from "vuex";
import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { onMounted, toRaw } from "vue";
import { watch } from "vue";
// import MODEL_PATH from "../../assets/custom.glb?url"; // 오리

export default {
  name: "TheCanvas",
  props: ["parts"],
  setup(props) {
    // Initial material
    const store = useStore();
    const theModel = store.state.root.user.model;
    const INITIAL_MTL = new THREE.MeshPhongMaterial({
      color: 0xffcb57,
      shininess: 10,
    });

    const INITIAL_MAP = [
      { childID: "body", mtl: INITIAL_MTL },
      { childID: "foot", mtl: INITIAL_MTL },
      { childID: "hat", mtl: INITIAL_MTL },
      { childID: "bag", mtl: INITIAL_MTL },
      { childID: "glasses", mtl: INITIAL_MTL },
      { childID: "clothes", mtl: INITIAL_MTL },
    ];

    watch(
      () => props.parts,
      (cur) => {
        console.log("cur", cur);
        colorChange(cur);
      },
      { deep: true },
    );

    const BACKGROUND_COLOR = 0xfff9ef; // 배경 색

    // 1. 장면 설정
    const scene = new THREE.Scene();

    // 2. renderer 설정
    const renderer = new THREE.WebGLRenderer();

    // 3. 카메라 설정
    const camera = new THREE.PerspectiveCamera(
      50, // field of view : FOV 주어진 순간에 디스플레이에 보이는 장면의 범위(카메라 화각), 보통50
      360 / 420, // aspect ratio : 거의 너비를 높이로 나눈 값 사용, 그렇지 않으면 이미지 찌그러져 보임(화면 비율)
      0.1, // near : 카메라 시작점
      1000, // far : 카메라 끝 점
    );

    scene.background = new THREE.Color(BACKGROUND_COLOR);

    renderer.shadowMap.enabled = false; // 그림자
    renderer.setPixelRatio(window.devicePixelRatio); // 픽셀 비율
    renderer.setSize(360, 420);

    camera.position.z = -2;
    camera.position.x = 25; // 화면에 보여지는 위치인것같음
    camera.position.y = -10;

    // 조명
    // HemisphereLight : 전 방향에서 조명을 비춰줌
    // 첫번째 인자 : 위쪽으로 비추는 빛의 컬러, 두번째 인자 : 아래를 비추는 빛의 컬러
    const hemiLight = new THREE.HemisphereLight(0xffffff, 0xffffff, 0.54);
    hemiLight.position.set(50, 50, 0);
    // Add hemisphere light to scene
    scene.add(toRaw(hemiLight));

    // 특정 방향으로 빛 방출
    // 빛 색상, 빛 강도
    const dirLight = new THREE.DirectionalLight(0xffffff, 0.54);
    dirLight.position.set(1100, 1100, 500);
    dirLight.castShadow = true; //광원이 그림자 생성
    dirLight.shadow.mapSize = new THREE.Vector2(1024, 1024);
    // Add directional Light to scene
    scene.add(toRaw(dirLight));

    // Add controls
    const controls = new OrbitControls(camera, renderer.domElement);
    controls.maxPolarAngle = Math.PI / 2;
    controls.minPolarAngle = Math.PI / 3;
    controls.enableDamping = true;
    controls.enableZoom = false;
    controls.enablePan = false;
    controls.dampingFactor = 0.1;
    controls.autoRotate = false; // Toggle this if you'd like the chair to automatically rotate
    controls.autoRotateSpeed = 0.2; // 30

    const init = () => {
      // Set initial textures
      for (let object of INITIAL_MAP) {
        let init_mtl = null;
        props.parts.forEach((item) => {
          if (item.id === object.childID) {
            init_mtl = new THREE.MeshPhongMaterial({
              color: parseInt("0x" + item.color),
              shininess: 10,
            });
          }
        });
        initColor(theModel, object.childID, init_mtl);
      }

      scene.add(toRaw(theModel));
    };

    const animate = () => {
      controls.update();
      renderer.render(toRaw(scene), camera);
      requestAnimationFrame(animate);

      if (resizeRendererToDisplaySize(renderer)) {
        const canvas = renderer.domElement;
        camera.aspect = canvas.clientWidth / canvas.clientHeight;
        camera.updateProjectionMatrix();
      }
    };

    //사이즈 조정
    const resizeRendererToDisplaySize = (renderer) => {
      const canvas = renderer.domElement;
      var width = window.innerWidth;
      var height = window.innerHeight;
      var canvasPixelWidth = canvas.width / window.devicePixelRatio;
      var canvasPixelHeight = canvas.height / window.devicePixelRatio;

      const needResize =
        canvasPixelWidth !== width || canvasPixelHeight !== height;
      if (needResize) {
        renderer.setSize(width, height, false);
      }
      return needResize;
    };

    const initColor = (parent, type, mtl) => {
      parent.traverse((o) => {
        if (o.isMesh) {
          if (o.name.includes(type)) {
            o.material = mtl;
            o.nameID = type; // Set a new property to identify this object
          }
        }
      });
    };

    //색 변경
    const colorChange = (cur) => {
      for (const part of cur) {
        let color = part.color;
        // console.log("color", color);
        let new_mtl;

        new_mtl = new THREE.MeshPhongMaterial({
          color: parseInt("0x" + color),
          shininess: 10,
        });

        setMaterial(theModel, part.id, new_mtl);
      }
    };

    const setMaterial = (parent, type, mtl) => {
      parent.traverse((o) => {
        if (o.isMesh && o.nameID != null) {
          if (o.nameID == type) {
            o.material = mtl;
          }
        }
      });
    };

    onMounted(() => {
      init();
      document.getElementById("canvas").appendChild(renderer.domElement);
      animate();
    });

    return {
      scene,
      renderer,
      camera,
      BACKGROUND_COLOR,
      INITIAL_MAP,
      animate,
      initColor,
      colorChange,
    };
  },
};
</script>
<style lang="scss" scoped>
#canvas {
  width: 360px;
  height: 420px;
}
</style>
