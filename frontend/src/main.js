import { createApp } from "vue";
import App from "./App.vue";
import store from "@/store/store";
import { router } from "./router";
import { SetupCalendar, Calendar, DatePicker } from "v-calendar";
import axios from "axios";

const DEFAULT_ACCEPT_TYPE =
  "application/json; charset=utf-8; application/x-www-form-urlencoded;";
axios.defaults.headers.common["Content-Type"] = DEFAULT_ACCEPT_TYPE;
const app = createApp(App);
app
  .use(SetupCalendar)
  .use(store)
  .component("Calendar", Calendar)
  .component("DatePicker", DatePicker)
  .use(router)
  .mount("#app");
