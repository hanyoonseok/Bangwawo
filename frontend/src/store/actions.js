// db값 바꾸는 곳
import axios from "axios";
const DEFAULT_ACCEPT_TYPE =
  "application/json; charset=utf-8; application/x-www-form-urlencoded;";
axios.defaults.headers.common["Content-Type"] = DEFAULT_ACCEPT_TYPE;
axios.defaults.baseURL = process.env.VUE_APP_API_URL;

export const loginUser = ({ state }, payload) => {
  console.log("requestLogin", state, payload);
  const url = `/${payload.userType}`;
  let body = payload;
  localStorage.setItem("isLogin", true);
  return axios.post(url, body);
};

export const getUserInfo = ({ state }, code) => {
  console.log("getUserInfo", state, code);
  const url = `/kakao/${code}`;
  return axios.get(url);
};
