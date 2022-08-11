import { createWebHistory, createRouter } from "vue-router";

const onlyAuthUser = async (to, from, next) => {
  const isLogin = localStorage.getItem("isLogin");
  if (!isLogin) {
    alert("로그인이 필요한 페이지입니다..");
    next("/user/login");
  } else {
    next();
  }
};

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
    beforeEnter: onlyAuthUser,
  },
  {
    path: "/secrettalk",
    name: "secrettalk",
    component: () => import("@/views/secret/SecretTalkView"),
    beforeEnter: onlyAuthUser,
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
        path: "detail/:cid",
        name: "classdetail",
        component: () => import("@/views/class/ClassDetailView"),
        beforeEnter: onlyAuthUser,
      },
      {
        path: "register/:rid",
        name: "classregister",
        component: () => import("@/views/class/ClassRegisterView"),
        beforeEnter: onlyAuthUser,
      },
      {
        path: "modify",
        name: "classmodify",
        component: () => import("@/views/class/ClassModifyView"),
        beforeEnter: onlyAuthUser,
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
        beforeEnter: onlyAuthUser,
      },
      {
        path: "requestmodify/:rid",
        name: "classrequestmodify",
        component: () => import("@/views/class/ClassRequestModify"),
        beforeEnter: onlyAuthUser,
      },
      {
        path: "requestdetail/:rid",
        name: "classrequestdetail",
        component: () => import("@/views/class/ClassRequestDetail"),
        beforeEnter: onlyAuthUser,
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
        props: true,
      },
      {
        path: "signup/volunteer",
        name: "signupVolunteer",
        component: () => import("@/views/user/SignupVolunteerView"),
        props: true,
      },
    ],
  },
  {
    path: "/mypage",
    name: "mypage",
    component: () => import("@/views/user/MypageView"),
    beforeEnter: onlyAuthUser,
  },
  {
    path: "/inclass",
    name: "inclass",
    component: () => import("@/views/class/InClassView"),
    beforeEnter: onlyAuthUser,
  },
  {
    path: "/feedback",
    name: "feedback",
    component: () => import("@/views/feedback/FeedBack"),
    beforeEnter: onlyAuthUser,
  },
  {
    path: "/feedbackSubmit",
    name: "feedbackSubmit",
    component: () => import("@/views/feedback/FeedbackSubmit"),
    beforeEnter: onlyAuthUser,
  },
];

export const router = createRouter({
  history: createWebHistory(),
  routes,
});
