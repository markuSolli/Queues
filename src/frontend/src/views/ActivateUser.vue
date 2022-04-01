<template>
  <div id="activate-user-grid">
    <div id="activate-user-box">
      <div class="row"><h1>Activate user</h1></div>
      <div class="row"><h3>exampleuser@ntnu.no</h3></div>
      <div class="row"><input class="input-field" type="password" placeholder="Password" v-model="user.password"/></div>
      <div class="row"><input class="input-field" type="password" placeholder="Repeat password" v-model="user.repeatPassword"/></div>
      <div class="row"><div id="button-row"><Button :title="'Activate'" @click="activateUser" /></div></div>
    </div>
  </div>
</template>

<script>
import { ref } from '@vue/reactivity';
import Button from "../components/Button";
import { useRoute } from "vue-router";

export default {
    components: { Button },
    setup(){
        const route = useRoute();
        let user = ref({ id: route.params.id});

        console.log(route.params.id);

        const activateUser = () => {

        }

        return {
            user,
            activateUser
        }
    }
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
    display:flex;
    justify-content: center;
}

</style>
