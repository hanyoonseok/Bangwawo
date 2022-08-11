import jwt_decode from "jwt-decode";

// state 값 바꾸는 부분
export const setUserInfo = (state, payload) => {
  const decode_jwt = jwt_decode(payload.JWT);
  decode_jwt.user.userType = decode_jwt.userType.toLowerCase();
  decode_jwt.user.accessToken = payload.accessToken;
  state.user = decode_jwt.user;
  localStorage.setItem("isLogin", true);
};

export const logoutUser = (state) => {
  state.user = null;
  localStorage.removeItem("isLogin");
};

export const setModifiedStudentInfo = (state, payload) => {
  state.user.nickname = payload;
};

export const setModifiedVolunteerInfo = (state, payload) => {
  state.user.introduce = payload;
};

export const setCharacterInfo = (state, payload) => {
  state.user.character = payload.character;
};
