<template>
  <div id="nav">
    <div id="left-logo"><h1>Queues</h1></div>
    <div id="left">
      <h1>Queues</h1>
      <router-link class="link" tag="li" to="/">Courses</router-link>
      <router-link class="link" tag="li" to="/about">About</router-link>
      <router-link class="link" tag="li" to="/archieve">Archieve</router-link>
      <router-link v-if="properRank" class="link" tag="li" to="/management"
        >Management</router-link
      >
    </div>
    <div id="right-menu" @click="openMenu">
      <i class="fa-solid fa-bars"></i>
    </div>
    <div v-if="expanded" id="right-expanded">
      <div @click="clickLink('/')" class="link">Courses</div>
      <div @click="clickLink('/about')" class="link">About</div>
      <div @click="clickLink('/archieve')" class="link">Archieve</div>
      <div @click="clickLink('/management')" class="link">Management</div>

      <div @click="clickLink('/profile')" class="link">Profile</div>
    </div>
    <div id="right">
      <router-link class="link" tag="li" to="/profile">Profile</router-link>
    </div>
  </div>
</template>

<script>
import { computed, ref } from "@vue/runtime-core";
import { useStore } from "vuex";
import router from "../router";

export default {
  components: {},
  setup() {
    const store = useStore();
    let expanded = ref(true);

    const openMenu = () => {
      expanded.value = !expanded.value;
      console.log(expanded.value);
    };

    const properRank = computed(() => {
      if (store.state.role < 3) {
        return true;
      } else {
        return false;
      }
    });

    const clickLink = (route) => {
      expanded.value = false;

      router.push(route);
    };

    return {
      properRank,
      expanded,
      openMenu,
      clickLink,
    };
  },
};
</script>

<style>
#nav {
  text-align: left;
  font-size: 16px;
  padding: 30px;
}

h1 {
  display: inline;
  margin-right: 30px;
}

.link {
  display: inline;
  margin: 0 0 0 20px;
  color: white;
  text-decoration: none;
}

.link:hover {
  color: grey;
}

#left {
  float: left;
  display: flex;
}

#left-logo {
  display: none;
}

#right-menu {
  display: none;
}

#right {
  float: right;
}

li.router-link-active,
li.router-link-exact-active {
  background-color: rgb(223, 9, 176);
  color: green;
  cursor: pointer;
}

@media only screen and (max-width: 600px) {
  #left {
    display: none;
  }
  #right {
    display: none;
  }

  #left-logo {
    display: block;

    float: left;
  }

  #right-menu {
    display: block;
    padding: 10;
    font-size: 25px;
    cursor: pointer;
    float: right;
  }

  #right-expanded {
    padding: 15px;
    display: grid;
    grid-template-columns: 1fr;
    right: 0;
    top: 75px;
    position: fixed;
    width: 150px;
    height: 200px;
    background: rgb(7, 3, 29);
    background: linear-gradient(
      0deg,
      rgba(7, 3, 29, 1) 0%,
      rgba(0, 0, 0, 1) 100%
    );
    z-index: 2;
    cursor: pointer;
    border-bottom-left-radius: 20px;
  }
}
</style>
