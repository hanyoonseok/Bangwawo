<template>
  <div class="mypage-background">
    <HeaderNav />
    <div class="mypage-container">
      <div class="profile-info">
        <profile-card></profile-card>
        <div class="left-box calendar">
          <h4>수업 일정</h4>
          <calendar-area />
        </div>
      </div>
      <div class="lecture-info">
        <div class="right-box">
          <div class="lecture-content">
            <h4 class="box-title">신청한 수업</h4>
            <div class="lecture-list">
              <div class="lecture">
                <div class="round-post-card">
                  <button class="status">
                    <i class="fa-solid fa-circle"></i>&nbsp;입장가능
                  </button>
                </div>
                <div class="lecture-title">
                  오리선생의 초1 수학수업입니당 진짜 재밌어요~!
                </div>
              </div>
              <div class="lecture">
                <div class="round-post-card">
                  <button class="status">
                    <i class="fa-solid fa-circle"></i>&nbsp;입장가능
                  </button>
                </div>
                <div class="lecture-title">
                  오리선생의 초1 수학수업입니당 진짜 재밌어요~!
                </div>
              </div>
              <div class="lecture">
                <div class="round-post-card">
                  <button class="status">
                    <i class="fa-solid fa-circle"></i>&nbsp;입장가능
                  </button>
                </div>
                <div class="lecture-title">
                  오리선생의 초1 수학수업입니당 진짜 재밌어요~!
                </div>
              </div>
              <div class="lecture">
                <div class="round-post-card">
                  <button class="status">
                    <i class="fa-solid fa-circle"></i>&nbsp;입장가능
                  </button>
                </div>
                <div class="lecture-title">
                  오리선생의 초1 수학수업입니당 진짜 재밌어요~!
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="right-box termination-list">
          <div class="lecture-content">
            <h4 class="box-title">종료된 수업</h4>
            <div class="lecture-list">
              <div class="lecture">
                <div
                  class="round-post-card end-lecture"
                  v-if="user.status === 3"
                >
                  <div></div>
                  <button class="feedback-btn">피드백 보기</button>
                </div>
                <div class="round-post-card end-lecture" v-else>
                  <div></div>
                  <p class="go-detail">수업 상세</p>
                </div>
                <div class="lecture-title">
                  오리선생의 초1 수학수업입니당 진짜 재밌어요~!
                </div>
              </div>
              <div class="lecture">
                <div
                  class="round-post-card end-lecture"
                  v-if="user.status === 3"
                >
                  <div></div>
                  <button class="feedback-btn">피드백 보기</button>
                </div>
                <div class="round-post-card end-lecture" v-else>
                  <div></div>
                  <p class="go-detail">수업 상세</p>
                </div>
                <div class="lecture-title">
                  오리선생의 초1 수학수업입니당 진짜 재밌어요~!
                </div>
              </div>
              <div class="lecture">
                <div
                  class="round-post-card end-lecture"
                  v-if="user.status === 3"
                >
                  <div></div>
                  <button class="feedback-btn">피드백 보기</button>
                </div>
                <div class="round-post-card end-lecture" v-else>
                  <div></div>
                  <p class="go-detail">수업 상세</p>
                </div>
                <div class="lecture-title">
                  오리선생의 초1 수학수업입니당 진짜 재밌어요~!
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CalendarArea from "@/components/mypage/CalendarArea.vue";
import ProfileCard from "@/components/mypage/ProfileCard.vue";
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  components: {
    CalendarArea,
    HeaderNav,
    ProfileCard,
  },
  setup() {
    const user = {
      status: 1,
      subscribe: 0,
    };
    return {
      user,
    };
  },
};
</script>
<style scoped src="@/css/mypage.scss" lang="scss"></style>
