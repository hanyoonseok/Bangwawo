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
  {
    path: "/oauth/redirect",
    name: "kakao",
    component: () => import("@/views/user/LoginRedirectView"),
  },
  {
    path: "/secret",
    name: "secret",
    component: () => import("@/views/secret/SecretFriendView"),
  },
  {
    path: "/secrettalk",
    name: "secrettalk",
    component: () => import("@/views/secret/SecretTalkView"),
  },
  {
    path: "/class",
    name: "class",
    component: () => import("@/views/class/ClassView"),
    redirect: "/class/list",
    children: [
      {
        path: "list",
        name: "classlist",
        component: () => import("@/views/class/ClassListView"),
      },
      {
        path: "detail",
        name: "classdetail",
        component: () => import("@/views/class/ClassDetailView"),
      },
      {
        path: "register",
        name: "classregister",
        component: () => import("@/views/class/ClassRegisterView"),
      },
      {
        path: "modify",
        name: "classmodify",
        component: () => import("@/views/class/ClassModifyView"),
      },
      {
        path: "requestlist",
        name: "classrequest",
        component: () => import("@/views/class/ClassRequestListView"),
      },
      {
        path: "requestregist",
        name: "classrequestregist",
        component: () => import("@/views/class/ClassRequestRegist"),
      },
      {
        path: "requestmodify",
        name: "classrequestmodify",
        component: () => import("@/views/class/ClassRequestModify"),
      },
      {
        path: "requestdetail",
        name: "classrequestdetail",
        component: () => import("@/views/class/ClassRequestDetail"),
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/user/UserView"),
    children: [
      {
        path: "login",
        name: "login",
        component: () => import("@/views/user/LoginView"),
      },
      {
        path: "signup/student",
        name: "signupStudent",
        component: () => import("@/views/user/SignupStudentView"),
      },
      {
        path: "signup/volunteer",
        name: "signupVolunteer",
        component: () => import("@/views/user/SignupVolunteerView"),
      },
    ],
  },
  {
    path: "/mypage",
    name: "mypage",
    component: () => import("@/views/user/MypageView"),
  },
  {
    path: "/inclass",
    name: "inclass",
    component: () => import("@/views/class/InClassView"),
  },
  {
    path: "/feedback",
    name: "feedback",
    component: () => import("@/views/feedback/FeedBack"),
  },

  {
    path: "/feedbackSubmit",
    name: "feedbackSubmit",
    component: () => import("@/views/feedback/FeedbackSubmit"),
  },
];

export const router = createRouter({
  history: createWebHistory(),
  routes,
});
