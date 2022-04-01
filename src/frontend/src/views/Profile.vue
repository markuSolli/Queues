<template>
  <div>
    <h1>Profile</h1>

    <h2 id="spacer">{{user.firstName + " " + user.lastName}}</h2>
    <h3 id="spacer">{{user.email}}</h3>
    <div id="log-out-button">
      <Button :title="'Reset password'" @click="resetPassword" />
    </div>
    <div id="log-out-button">
      <Button :title="'Log out'" @click="logOut" />
    </div>
  </div>
</template>

<script>
import Button from "../components/Button.vue";
import router from "../router";
import http from "@/service/http-common";
import { useStore } from "vuex";
import { ref } from "@vue/reactivity";
import { onBeforeMount } from "vue";
import { logout } from "@/service/AuthenticationService.js";

export default {
  components: { Button },
  setup() {
    const store = useStore();
    const user = ref({})

    onBeforeMount(() => {
      http.
        get("/me")
      .then((response) => {
        user.value = response.data;
      })
      .catch((err) => {
        console.log(err);
      })

    })

    const logOut = async () => {
      try {
        await logout();
        store.commit("setAccessToken", null)
        store.commit("updateLoggedin", false);
        router.push("logIn");
      } catch(err) {
        console.log(err);
      }
       };

    const resetPassword = () => {};

    return {
      logOut,
      resetPassword,
      user,
    };
  },
};
</script>

<style>
#spacer {
  margin: 50px 0px;
}

#log-out-button {
  display: flex;
  justify-content: center;
  margin: 50px 0px;
}
</style>
