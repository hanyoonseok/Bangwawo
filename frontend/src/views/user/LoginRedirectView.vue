<template>
  <div class="login-form"></div>
</template>

<script>
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  name: "LoginRedirectView",
  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    const { code } = route.query;

    const getLoginInfo = async () => {
      const { data } = await store.dispatch("root/getUserInfo", code);
      if (data.isUser) {
        store.commit("root/setUserInfo", data);
        router.push("/class/list");
      } else {
        data.ageRange < 20
          ? router.push({
              name: "signupStudent",
              params: data,
            })
          : router.push({ name: "signupVolunteer", params: data });
      }
    };

    getLoginInfo();
  },
};
</script>

<style scoped src="@/css/user.scss" lang="scss"></style>
