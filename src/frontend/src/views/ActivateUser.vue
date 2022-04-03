<template>
  <div id="activate-user-grid">
    <div id="activate-user-box">
      <div class="row"><h1>Activate user</h1></div>
      <div class="row"><h3>{{ e_mail }}</h3></div>
      <div class="row"><input class="input-field" type="password" placeholder="Password" v-model="password"/></div>
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
  data() {
    return {
      e_mail: "",
      password: "",
      repeatPassword: "",
      error_msg: ""
    }
  },
  methods: {
    activateUser() {
      this.error_msg = "";
      if (this.password === this.repeatPassword) {
        http.put("/activate", {
          email: this.e_mail,
          password: this.password
        })
            .catch((err) => {
              console.log(err);
            })
        router.push("/");
      } else {
        this.error_msg = "The two passwords do not match!"
      }
    }
  },
  setup() {
    const route = useRoute();
    let code = route.params.id;
    const user = {
      email: "",
      password: ""
    }

    http.get("/activation/" + code)
        .then((response) => {
          user.email = response.data.email;
        })
        .catch((err) => {
          console.log(err);
        });
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
