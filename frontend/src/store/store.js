import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import root from "./index";

export default createStore({
  modules: { root },
  plugins: [createPersistedState()],
});
