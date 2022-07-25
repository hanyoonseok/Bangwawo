import { createWebHistory, createRouter } from "vue-router";

// const onlyAuthUser = async (to, from, next) => {
//   const checkUserInfo = store.getters["userStore/checkUserInfo"];
//   const getUserInfo = store._actions["userStore/getUserInfo"];
//   let token = sessionStorage.getItem("access-token");
//   if (checkUserInfo == null && token) {
//     await getUserInfo(token);
//   }
//   if (checkUserInfo === null) {
//     alert("로그인이 필요한 페이지입니다..");
//     next("/user/login");
//   } else {
//     next();
//   }
// };

// const onlyAdmin = async (to, from, next) => {
//   // console.log(store);
//   const checkUserInfo = store.getters["userStore/checkUserInfo"];
//   const getUserInfo = store._actions["userStore/getUserInfo"];
//   let token = sessionStorage.getItem("access-token");
//   if (checkUserInfo == null && token) {
//     await getUserInfo(token);
//   }
//   if (checkUserInfo.role !== 1) {
//     alert("관리자만 입장 가능한 페이지입니다..");
//     next("/");
//   } else {
//     next();
//   }
// };

const routes = [
  {
    path: "/",
    name: "home",
    component: () => import("@/views/HomeView"),
  },
];

export const router = createRouter({
  history: createWebHistory(),
  routes,
});
