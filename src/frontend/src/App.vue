<template>
  <div id="grid" v-if="!isLoading">
    <Navbar v-if="loggedIn" /> <router-view />
  </div>
</template>

<script>
import { computed, ref } from "@vue/reactivity";
import Navbar from "./components/Navbar";
import { useStore } from "vuex";
import { refreshToken, whoAmI } from "@/service/AuthenticationService";
import { onBeforeMount } from "vue";
import router from "./router";
import { useRoute } from "vue-router";

export default {
  components: { Navbar },
  setup() {
    const route = useRoute();
    let isLoading = ref(true);

    onBeforeMount(() => {
      const fetchToken = async () => {
        console.log("Fetching user");
        try {
          let response = await refreshToken();
          if (!response.data.accessToken) {
            //
            return;
          }
          store.commit("setAccessToken", response.data.accessToken);
          store.commit("updateLoggedin", true);
          response = await whoAmI();
          store.commit("setUser", response.data);
        } catch (err) {
          console.log(err);
          if (!route.path.includes("activateUser")) {
            router.push("/login");
          }
        } finally {
          isLoading.value = false;
        }
      };
      fetchToken();
    });

    const store = useStore();

    let loggedIn = computed(() => {
      if (store.state.loggedIn) {
        return true;
      } else {
        return false;
      }
    });

    return {
      isLoading,
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
  max-width: 1200px;
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
