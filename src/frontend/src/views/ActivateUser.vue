<template>
  <div id="activate-user-grid">
    <div id="activate-user-box" v-on:keyup.enter="activateUser">
      <div class="row"><h1>Activate user</h1></div>
      <div class="row">
        <h3>{{ user.email }}</h3>
      </div>
      <div class="row">
        <input
          class="input-field"
          type="password"
          placeholder="Password"
          v-model="user.password"
        />
      </div>
      <div class="row">
        <input
          class="input-field"
          type="password"
          placeholder="Repeat password"
          v-model="repeatPassword"
        />
      </div>
      <div class="row">
        <div id="button-row">
          <Button :title="'Activate'" @click="activateUser" />
        </div>
      </div>
      <div class="row">
        <h5 id="error_field">{{ error_msg }}</h5>
      </div>
    </div>
  </div>
</template>

<script>
import Button from "../components/Button";
import { useRoute } from "vue-router";
import http from "@/service/http-common";
import router from "@/router";
import { ref } from "@vue/reactivity";

export default {
  components: { Button },
  setup() {
    const route = useRoute();
    let code = route.params.id;
    const user = ref({
      email: "",
      password: "",
    });
    const repeatPassword = ref("");
    const error_msg = ref(null);

    http
      .get("/activation/" + code)
      .then((response) => {
        user.value = response.data;
        console.log(user.value);
      })
      .catch((err) => {
        console.log(err);
      });

    const activateUser = () => {
      if (
        user.value.password !== "" &&
        user.value.password === repeatPassword.value
      ) {
        user.value.email = code;
        http
          .put("/activate", user.value)
          .then((response) => {
            console.log(response.data);
            // If the activation is successfull, we route the user to the login page
            router.push("/login");
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        error_msg.value = "Passwords do not match!";
      }
    };

    return {
      user,
      repeatPassword,
      error_msg,
      activateUser,
    };
  },
};
</script>

<style>
#activate-user-grid {
  display: grid;
  height: 100vh;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr 1fr;
}

#activate-user-box {
  grid-column: 2 / 3;
  grid-row: 2/3;
}

#input-field {
  border-radius: 20px;
  padding: 10px;
}

#button-row {
  display: flex;
  justify-content: center;
}

#error_field {
  color: red;
}
</style>
