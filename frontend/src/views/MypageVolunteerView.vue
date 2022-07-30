<template>
  <div class="mypage-content">
    <div class="profile-info volunteer-info">
      <div class="left-box profile">
        <a href="#sub-nav"
          ><i class="fa-solid fa-ellipsis-vertical profile-ellipse">
            <ul id="sub-nav">
              <li>
                <i class="fa-solid fa-pen-to-square"></i>
                &nbsp;<span>정보수정</span>
              </li>

              <li>
                <i class="fa-solid fa-arrow-right-from-bracket"></i>
                &nbsp;<span>회원탈퇴</span>
              </li>
            </ul></i
          ></a
        >

        <div>
          <img src="@/assets/profile.png" />
        </div>
        <div class="person-info">
          <label>이름</label>
          <div class="label-value volunteer-value">이화연 바보</div>
        </div>
        <div class="person-info">
          <label>자기소개</label>
          <div class="label-value introduce">자기소개 글</div>
        </div>
      </div>
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
              <div class="round-post-card end-lecture">
                <div></div>
                <button class="feedback-btn">피드백 보기</button>
              </div>
              <div class="lecture-title">
                오리선생의 초1 수학수업입니당 진짜 재밌어요~!
              </div>
            </div>
            <div class="lecture">
              <div class="round-post-card end-lecture">
                <div></div>
                <button class="feedback-btn">피드백 보기</button>
              </div>
              <div class="lecture-title">
                오리선생의 초1 수학수업입니당 진짜 재밌어요~!
              </div>
            </div>
            <div class="lecture">
              <div class="round-post-card end-lecture">
                <div></div>
                <button class="feedback-btn">피드백 보기</button>
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
</template>

<script>
import CalendarArea from "@/components/CalendarArea.vue";
export default {
  components: {
    CalendarArea,
  },
};
</script>
<style scoped src="@/css/mypage.scss" lang="scss"></style>
