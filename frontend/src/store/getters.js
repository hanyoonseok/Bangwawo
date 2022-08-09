import state from "./state";

// 플랫폼 관련 정보 가져오기
export const getUserType = () => {
  return state.user.userType;
};
