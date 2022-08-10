// db값 바꾸는 곳
import axios from "axios";
const DEFAULT_ACCEPT_TYPE =
  "application/json; charset=utf-8; application/x-www-form-urlencoded;";
axios.defaults.headers.common["Content-Type"] = DEFAULT_ACCEPT_TYPE;
axios.defaults.baseURL = process.env.VUE_APP_API_URL;

export const loginUser = ({ state }, payload) => {
  console.log("requestLogin", state, payload);
  const url = `/${payload.userType}/`;
  let body = payload;
  localStorage.setItem("isLogin", true);
  return axios.post(url, body);
};

export const getUserInfo = ({ state }, code) => {
  console.log("getUserInfo", state, code);
  const url = `/kakao/${code}`;
  return axios.get(url);
};

export const getChildren = ({ state }, email) => {
  console.log("getChildren", state, email);
  const url = `/parent/${email}`;
  return axios.get(url);
};

export const modifyUserInfo = ({ state }, payload) => {
  console.log("modifyStudentInfo", state, payload);
  const url = `/${payload.userType}/`;
  return axios.put(url, payload);
};

export const getStudentClasses = ({ state }, sid) => {
  console.log("getStudentClasses", state, sid);
  const url = `/enrol/student/${sid}/`;
  return axios.get(url);
};

export const getEndedClasses = ({ state }, vid) => {
  console.log("getEndedClasses", state, vid);
  const url = `/class?state=2&vid=${vid}`;
  return axios.get(url);
};

export const getComingClasses = ({ state }, vid) => {
  console.log("getComingClasses", state, vid);
  const url = `/class?state=0&vid=${vid}`;
  return axios.get(url);
};

export const setCharacterInfo = ({ state }, modifyInfo) => {
  console.log("setCharacterInfo", state, modifyInfo);
  const url = `/${modifyInfo.userType}/${
    modifyInfo.userType === "student" ? modifyInfo.sid : modifyInfo.vid
  }`;
  return axios.put(url, modifyInfo);
};
