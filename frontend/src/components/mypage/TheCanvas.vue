<template>
  <div id="canvas"></div>
</template>

<script>
import * as THREE from "three";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { onMounted, toRaw } from "vue";
import { watch } from "vue";
// import MODEL_PATH from "../../assets/custom.glb?url"; // 오리

export default {
  name: "TheCanvas",
  props: ["parts"],
  setup(props) {
    // Initial material
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

    renderer.shadowMap.enabled = true; // 그림자
    renderer.setPixelRatio(window.devicePixelRatio); // 픽셀 비율
    renderer.setSize(360, 420);

    camera.position.z = -5;
    camera.position.x = 25; // 화면에 보여지는 위치인것같음
    camera.position.y = -10;

    // 조명
    // 조명
    // HemisphereLight : 전 방향에서 조명을 비춰줌
    // 첫번째 인자 : 위쪽으로 비추는 빛의 컬러, 두번째 인자 : 아래를 비추는 빛의 컬러
    const hemiLight = new THREE.HemisphereLight(0xffffff, 0xffffff, 0.65);
    hemiLight.position.set(1, -20, 1);
    scene.add(hemiLight);
    // 특정 방향으로 빛 방출
    // 빛 색상, 빛 강도
    const dirLight = new THREE.DirectionalLight(0xffffff, 0.4);
    dirLight.position.set(10000, 3000, 0);
    // Add directional Light to scene
    dirLight.dispose();
    scene.add(dirLight);
    const dirLight2 = new THREE.DirectionalLight(0xffffff, 0.2);
    dirLight2.position.set(-10000, -3000, 0);
    // Add directional Light to scene
    scene.add(dirLight2);

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

    const MODEL_PATH = "./duckduck3.glb";

    let mixer = null;
    let clips = null;

    const loader = new GLTFLoader();
    // Init the object loader
    let theModel = null;

    loader.load(
      MODEL_PATH,
      function (gltf) {
        theModel = gltf.scene;

        theModel.traverse((o) => {
          if (o.isMesh) {
            o.castShadow = true;
            o.receiveShadow = true;
          }
        });

        // Set the models initial scale
        theModel.scale.set(5, 5, 5);

        // Add the model to the scene
        theModel.position.y = -10;

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
        scene.add(theModel);
        mixer = new THREE.AnimationMixer(theModel);
        clips = gltf.animations;
        standing();

        // 가로/세로/앞뒤 모두 1미터 크기의 박스 뼈대 생성
        // var bone = new THREE.PlaneBufferGeometry(200, 200, 2, 2);
        // // 최근의 THREE.js 에서는
        // // 기존의 BoxGeometry 를 대신하여 재사용이 용이한 BoxBufferGeometry를 추천하고 있다.
        // // 재질의 색깔을 녹색으로 생성
        // var material = new THREE.MeshBasicMaterial({ color: 0x00000 });

        // // 뼈대와 재질을 섞어 "박스"라는 메쉬오브젝트 생성
        // var floor = new THREE.Mesh(bone, material);

        // scene.add(floor); // "장면" 에 "박스" 메쉬오브젝트 추가
      },
      undefined,
      function (error) {
        console.error(error);
      },
    );

    const standing = () => {
      let clip = THREE.AnimationClip.findByName(clips, "stand");
      let action = mixer.clipAction(clip);

      action.play();
    };
    const clock = new THREE.Clock();

    const animate = () => {
      if (mixer) {
        mixer.update(clock.getDelta());
      }
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
