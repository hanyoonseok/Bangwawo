import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";

const MODEL_PATH = "http://localhost:8080/duck.glb"; //"./duck.glb";

const loader = new GLTFLoader();

const user = {
  name: "test",
  characterColors: [
    { id: "body", color: "f1f1f1" },
    { id: "bag", color: "000000" },
    { id: "clothes", color: "ffffff" },
    { id: "hat", color: "527329" },
    { id: "foot", color: "ff9696" },
    { id: "glasses", color: "ff9696" },
  ],
  model: {},
};

loader.load(
  MODEL_PATH,
  function (gltf) {
    user.model = gltf.scene;

    user.model.traverse((o) => {
      if (o.isMesh) {
        o.castShadow = true;
        o.receiveShadow = true;
      }
    });

    // Set the models initial scale
    user.model.scale.set(4.5, 4.5, 4.5);
    // user.model.rotation.y = Math.PI;

    // Add the model to the scene
    user.model.position.y = -7;
  },
  undefined,
  function (error) {
    console.error(error);
  },
);

export default {
  user,
};
