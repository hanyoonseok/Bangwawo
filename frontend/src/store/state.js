import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";

const MODEL_PATH = "./custom.glb";

const loader = new GLTFLoader();

const user = {
  name: "test",
  characterColors: ["f1f1f1", "000000", "ffffff", "527329", "ff9696", "ff9696"],
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
    user.model.scale.set(2.5, 2.5, 2.5);
    user.model.rotation.y = Math.PI;

    // Add the model to the scene
    user.model.position.y = -4;
  },
  undefined,
  function (error) {
    console.error(error);
  },
);

export default {
  user,
};
