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
  const url = `/kakao/login/${code}`;
  return axios.get(url);
};

export const getChildren = ({ state }, email) => {
  console.log("getChildren", state, email);
  const url = `/parent/${email}`;
  return axios.get(url);
};

export const modifyUserInfo = ({ state }, payload) => {
  console.log("modifyStudentInfo", state, payload);
  const field =
    payload.userType === "student"
      ? "/nickname"
      : payload.userType === "volunteer"
      ? "/introduce"
      : "";
  const url = `/${payload.userType}${field}`;
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
  const url = `/${modifyInfo.userType}/character`;
  return axios.put(url, modifyInfo);
};

export const deleteUser = ({ state }, payload) => {
  console.log("deleteUser", state, payload);
  const url = `/${payload.userType}/${payload.vid || payload.sid}`;
  return axios.delete(url);
};

export const deleteKakaoInfo = ({ state }, accessToken) => {
  console.log("deleteKakaoInfo", state, accessToken);
  const url = `/kakao/delete/${accessToken}`;
  return axios.get(url);
};

export const inactiveKakaoToken = ({ state }, accessToken) => {
  console.log("inactiveKakaoToken", state, accessToken);
  const url = `/kakao/logout/${accessToken}`;
  return axios.get(url);
};
// 수업
export const getClassList = ({ state }) => {
  console.log("getClassList", state);
  const url = `/class`;
  return axios.get(url);
};

export const getClassDetail = ({ state }, cid) => {
  console.log("getClassDetail", state, cid);
  const url = `/class/${cid}`;
  return axios.get(url);
};

export const getEnrolStudent = ({ state }, cid) => {
  console.log("getEnrolStudent", state, cid);
  const url = `/enrol/class/${cid}`;
  return axios.get(url);
};

export const enrolClass = ({ state }, payload) => {
  console.log("enrolClass", state, payload);
  const url = `/enrol`;
  return axios.post(url, payload);
};

export const deleteClass = ({ state }, cid) => {
  console.log("deleteClass", state, cid);
  const url = `/class/${cid}`;
  return axios.delete(url);
};

export const registerImage = ({ state }, payload) => {
  console.log("registerImage", state, payload);
  const url = `/class/image`;
  const formData = payload;
  return axios.post(url, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};

export const modifyClass = ({ state }, payload) => {
  console.log("modifyClass", state, payload);
  const url = `/class`;
  return axios.put(url, payload);
};

export const registerClass = ({ state }, payload) => {
  console.log("registerClass", state, payload);
  const url = `/class`;
  return axios.post(url, payload);
};

export const startClass = ({ state }, payload) => {
  console.log("startClass", state, payload);
  const url = `/class`;
  return axios.put(url, payload);
};
