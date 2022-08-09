import jwt_decode from "jwt-decode";

// state 값 바꾸는 부분
export const setUserInfo = (state, payload) => {
  const decode_jwt = jwt_decode(payload.JWT);
  decode_jwt.user.userType = decode_jwt.userType.toLowerCase();
  state.user = decode_jwt.user;
};
