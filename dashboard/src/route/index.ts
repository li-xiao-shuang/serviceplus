import { createWebHistory, createRouter } from "vue-router";

import AppList from "@/pages/Applist/index.vue";

import ServiceDetail from "@/pages/ServiceDetail/index.vue";

const routes = [
  {
    path: "/",
    redirect: "/appList",
  },
  {
    path: "/appList",
    component: AppList,
  },
  {
    path: "/serviceDetail",
    component: ServiceDetail,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
