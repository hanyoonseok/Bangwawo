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
  localStorage.removeItem("isLogin");
  state.user = null;
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

export const toggleTalkable = (state) => {
  state.user.talkable = !state.user.talkable;
};

export const initEmotion = (state) => {
  state.emotions = {
    angry: 0,
    disgusted: 0,
    fearful: 0,
    happy: 0,
    neutral: 0,
    sad: 0,
    surprised: 0,
  };
  state.emotionCnt = 0;
};

export const addEmotion = (state, payload) => {
  const keySet = Object.keys(payload);
  for (let i = 0; i < keySet.length; i++) {
    state.emotions[keySet[i]] += payload[keySet[i]];
  }
  state.emotionCnt++;
};
