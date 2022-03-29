<template>
  <div>
    <h1>Profile</h1>

    <h2 id="spacer">Email:</h2>
    <h2 id="spacer">Firstname:</h2>
    <h2 id="spacer">Lastname:</h2>
    <div id="log-out-button">
      <Button :title="'Reset password'" :func="resetPassword" />
    </div>
    <div id="log-out-button">
      <Button :title="'Log out'" :func="logOut" />
    </div>
  </div>
</template>

<script>
import Button from "../components/Button.vue";
import router from "../router";
import { useStore } from "vuex";

export default {
  components: { Button },
  setup() {
    const store = useStore();

    const logOut = () => {
      store.commit("updateLoggedin", false);
      router.push("logIn");
    };

    const resetPassword = () => {};

    return {
      logOut,
      resetPassword,
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
