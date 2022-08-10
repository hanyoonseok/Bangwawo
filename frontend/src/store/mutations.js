import jwt_decode from "jwt-decode";

// state 값 바꾸는 부분
export const setUserInfo = (state, payload) => {
  const decode_jwt = jwt_decode(payload.JWT);
  decode_jwt.user.userType = decode_jwt.userType.toLowerCase();
  if (decode_jwt.user.character) {
    const keySet = Object.keys(decode_jwt.user.character);
    const valueSet = Object.values(decode_jwt.user.character);
    decode_jwt.user.cKey = keySet;
    decode_jwt.user.cValue = valueSet;
  }
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
