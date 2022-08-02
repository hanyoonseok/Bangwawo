<template>
  <div id="canvas"></div>
</template>

<script>
import * as THREE from "three";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { onMounted } from "vue";
// import MODEL_PATH from "../../assets/custom.glb?url"; // 오리

export default {
  name: "TheCanvas",
  setup() {
    const BACKGROUND_COLOR = 0xfff9ef;

    // 1. 장면 설정
    const scene = new THREE.Scene();

    // 2. renderer 설정
    const renderer = new THREE.WebGLRenderer({
      //   canvas,
      //   antialias: true,
      //   alpha: true,
    });

    // 3. 카메라 설정
    const camera = new THREE.PerspectiveCamera(
      50, // field of view : FOV 주어진 순간에 디스플레이에 보이는 장면의 범위(카메라 화각)
      360 / 420, // aspect ratio : 거의 너비를 높이로 나눈 값 사용, 그렇지 않으면 이미지 찌그러져 보임(화면 비율)
      0.1, // near : 카메라 시작점
      1000, // far : 카메라 끝 점
    );

    // Initial material
    const INITIAL_MTL = new THREE.MeshPhongMaterial({
      color: 0xffcb57,
      shininess: 10,
    });

    const INITIAL_MAP = [
      { childID: "body", mtl: INITIAL_MTL },
      { childID: "mouse", mtl: INITIAL_MTL },
      { childID: "lhand", mtl: INITIAL_MTL },
      { childID: "rhand", mtl: INITIAL_MTL },
    ];

    onMounted(() => {
      document.getElementById("canvas").appendChild(renderer.domElement);
      animate();
    });

    const animate = () => {
      //   controls.update();
      renderer.render(scene, camera);
      requestAnimationFrame(animate);

      if (resizeRendererToDisplaySize(renderer)) {
        const canvas = renderer.domElement;
        camera.aspect = canvas.clientWidth / canvas.clientHeight;
        camera.updateProjectionMatrix();
      }

      //   if (theModel != null && loaded == false) {
      //     initialRotation();
      //     DRAG_NOTICE.classList.add("start");
      //   }
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

    return {
      scene,
      renderer,
      camera,
      BACKGROUND_COLOR,
      INITIAL_MAP,
      animate,
      initColor,
    };
  },
  created() {
    // Set background
    this.scene.background = new THREE.Color(this.BACKGROUND_COLOR);

    this.renderer.shadowMap.enabled = false; // 그림자
    this.renderer.setPixelRatio(window.devicePixelRatio); // 픽셀 비율
    this.renderer.setSize(360, 420);

    this.camera.position.z = -3;
    this.camera.position.x = 15; // 화면에 보여지는 위치인것같음
    this.camera.position.y = 3;

    // 조명
    // HemisphereLight : 전 방향에서 조명을 비춰줌
    // 첫번째 인자 : 위쪽으로 비추는 빛의 컬러, 두번째 인자 : 아래를 비추는 빛의 컬러
    var hemiLight = new THREE.HemisphereLight(0xffffff, 0xffffff, 0.61);
    hemiLight.position.set(50, 50, 0);
    // Add hemisphere light to scene
    this.scene.add(hemiLight);

    var dirLight = new THREE.DirectionalLight(0xffffff, 0.54);
    dirLight.position.set(1100, 1100, 1100);
    dirLight.castShadow = true;
    dirLight.shadow.mapSize = new THREE.Vector2(1024, 1024);
    // Add directional Light to scene
    this.scene.add(dirLight);

    // Floor
    var floorGeometry = new THREE.PlaneGeometry(300, 300, 1, 1);
    var floorMaterial = new THREE.MeshPhongMaterial({
      color: 0xeeeeee, // This color is manually dialed in to match the background color
      shininess: 0,
    });

    var floor = new THREE.Mesh(floorGeometry, floorMaterial);
    floor.rotation.x = -0.5 * Math.PI;
    floor.receiveShadow = true;
    floor.position.y = -1;
    // this.scene.add(floor);

    // Add controls
    var controls = new OrbitControls(this.camera, this.renderer.domElement);
    controls.maxPolarAngle = Math.PI / 2;
    controls.minPolarAngle = Math.PI / 3;
    controls.enableDamping = true;
    controls.enablePan = false;
    controls.dampingFactor = 0.1;
    controls.autoRotate = false; // Toggle this if you'd like the chair to automatically rotate
    controls.autoRotateSpeed = 0.2; // 30

    const MODEL_PATH = "./custom.glb"; // 오리
    let theModel = null;

    // Init the object loader
    const loader = new GLTFLoader();
    const _this = this;
    loader.load(
      MODEL_PATH,
      function (gltf) {
        theModel = gltf.scene;
        console.log(theModel);

        theModel.traverse((o) => {
          if (o.isMesh) {
            o.castShadow = true;
            o.receiveShadow = true;
          }
        });

        // Set the models initial scale
        theModel.scale.set(2.5, 2.5, 2.5);
        theModel.rotation.y = Math.PI;

        // Add the model to the scene
        theModel.position.y = -4;

        // Set initial textures
        for (let object of _this.INITIAL_MAP) {
          _this.initColor(theModel, object.childID, object.mtl);
        }

        // console.log(s);
        _this.scene.add(theModel);

        // Remove the loader
        // LOADER.remove();
      },
      undefined,
      function (error) {
        console.error(error);
      },
    );
  },
};
</script>
<style lang="scss">
#canvas {
  width: 360px;
  height: 420px;
}
</style>
