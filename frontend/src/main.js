import { createApp } from "vue";
import App from "./App.vue";
import store from "@/store/store";
import { router } from "./router";
import { SetupCalendar, Calendar, DatePicker } from "v-calendar";

const app = createApp(App);
app
  .use(SetupCalendar)
  .use(store)
  .component("Calendar", Calendar)
  .component("DatePicker", DatePicker)
  .use(router)
  .mount("#app");
