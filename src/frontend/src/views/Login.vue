<template>
  <div id="login">
    <div id="login-box">
      <h1>Login</h1>
      <div id="input-row">
        <input
          class="input-field"
          type="text"
          v-model="username"
          placeholder="Username"
        />
      </div>

      <div id="input-row">
        <input
          class="input-field"
          type="password"
          v-model="password"
          placeholder="Password"
        />
      </div>
      <div id="input-row">
        <div id="login-button">
          <Button :title="'Log in'" @click="logIn" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";
import { useStore } from "vuex";
import router from "../router";
import { login } from "@/service/AuthenticationService";
import http from "../service/http-common";

export default {
  components: { Button },
  setup() {
    const store = useStore();

    let username = ref("");
    let password = ref("");

    const logIn = async () => {
      try {
        const response = await login({
          email: username.value,
          password: password.value,
        });

        store.commit("setAccessToken", response.data.accessToken);

        // SET LOGGED IN USER IN STATE
        http
          .get("/me")
          .then((response) => {
            store.commit("updateLoggedin", true);
            store.commit("updateEmail", response.data.email);
            store.commit("updateFirstname", response.data.firstName);
            store.commit("updateLastname", response.data.lastName);
          })
          .catch((err) => {
            console.log(err);
          });
        // ----------------------------

        router.push("/");
      } catch (err) {
        console.log(err);
      }
    };

    return {
      username,
      password,
      logIn,
    };
  },
};
</script>

<style>
#login {
  display: grid;
  grid-template-rows: 1fr 1fr 1fr;
  grid-template-columns: 1fr 1fr 1fr;
  height: 100vh;
}

#login-box {
  grid-column: 2 / 3;
  grid-row: 2 / 3;
}

#input-row {
  margin: 50px;
}

.input-field {
  padding: 15px 25px;
  border-radius: 20px;
  font-size: 20px;
}

#login-button {
  display: flex;
  justify-content: center;
}
</style>
