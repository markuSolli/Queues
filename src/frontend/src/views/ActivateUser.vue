<template>
  <div id="activate-user-grid">
    <div id="activate-user-box">
      <div class="row"><h1>Activate user</h1></div>
      <div class="row"><h3>{{ user.email }}</h3></div>
      <div class="row"><input class="input-field" type="password" placeholder="Password" v-model="user.password"/></div>
      <div class="row"><input class="input-field" type="password" placeholder="Repeat password" v-model="repeatPassword"/></div>
      <div class="row"><div id="button-row"><Button :title="'Activate'" @click="activateUser" /></div></div>
      <div class="row"><h5 id="error_field">{{ error_msg }}</h5></div>
    </div>
  </div>
</template>

<script>
import Button from "../components/Button";
import { useRoute } from "vue-router";
import http from "@/service/http-common";
import router from "@/router";

export default {
  components: {Button},
  setup() {
    const route = useRoute();
    let code = route.params.id;
    const user = {
      email: "",
      password: ""
    }
    const repeatPassword = "";
    let error_msg = "";

    http.get("/activation/" + code)
        .then((response) => {
          user.email = response.data.email;
        })
        .catch((err) => {
          console.log(err);
        });

    const activateUser = () => {
      if(user.password === repeatPassword){
        http.put("/activate", user)
            .catch((err) => {
              console.log(err);
            })
        router.push("/");
      }else{
        error_msg = "Passwords do not match!";
      }
    }

    return {
      user,
      repeatPassword,
      error_msg,
      activateUser
    }
  }
}
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
    display:flex;
    justify-content: center;
}

#error_field {
  color: red;
}

</style>
