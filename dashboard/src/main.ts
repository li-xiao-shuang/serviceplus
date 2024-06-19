import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "./route";

import "@/style/index.scss";

createApp(App).use(router).mount("#app");
