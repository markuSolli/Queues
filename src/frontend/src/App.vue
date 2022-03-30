<template>
  <div id="grid"><Navbar v-if="loggedIn" /> <router-view /></div>
</template>

<script>
import { computed, ref } from "@vue/reactivity";
import Navbar from "./components/Navbar";
import { useStore } from "vuex";

export default {
  components: { Navbar },
  setup() {
    const store = useStore();

    let loggedIn = computed(() => {
      if (store.state.loggedIn) {
        return true;
      } else {
        return false;
      }
    });

    return {
      loggedIn,
    };
  },
};
</script>

<style>
* {
  margin: 0px;
  padding: 0px;
}

#grid {
  display: grid;
  grid-area: "Navbar" "router-view";
  width: 1200px;
  margin: 0 auto;
}

#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #ffffff;
  min-height: 100vh;
  background: rgb(11, 4, 36);
  background: linear-gradient(
    17deg,
    rgba(11, 4, 36, 1) 11%,
    rgba(0, 0, 0, 1) 100%
  );
  font-family: "Mukta", Helvetica, Arial;
}
</style>
