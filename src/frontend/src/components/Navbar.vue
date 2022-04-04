<template>
  <div id="nav">
    <div id="left-logo"><h1>Queues</h1></div>
    <div id="left">
      <h1 @click="pushToHome">Queues</h1>
      <router-link class="link" tag="li" to="/"
        ><i class="fa-solid fa-list"></i> Courses</router-link
      >
      <router-link class="link" tag="li" to="/about"
        ><i class="fa-solid fa-message-text"></i
        ><i class="fa-solid fa-info"></i> About</router-link
      >
      <router-link class="link" tag="li" to="/archieve"
        ><i class="fa-solid fa-box-archive"></i> Archive</router-link
      >
      <router-link v-if="properRank" class="link" tag="li" to="/management"
        ><i class="fa-solid fa-list-check"></i> Management</router-link
      >
    </div>
    <div id="right-menu" @click="openMenu">
      <i class="fa-solid fa-bars"></i>
    </div>
    <div v-if="expanded" id="right-expanded">
      <div @click="clickLink('/')" class="link">Courses</div>
      <div @click="clickLink('/about')" class="link">About</div>
      <div @click="clickLink('/archieve')" class="link">Archive</div>
      <div v-if="properRank" @click="clickLink('/management')" class="link">
        Management
      </div>

      <div @click="clickLink('/profile')" class="link">Profile</div>
    </div>
    <div id="right">
      <router-link class="link" tag="li" to="/profile">
        <i class="fa-solid fa-user"></i> Profile</router-link
      >
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
    let expanded = ref(false);

    const openMenu = () => {
      expanded.value = !expanded.value;
      console.log(expanded.value);
    };

    const properRank = computed(() => {
      if (
        store.state.role == "ADMIN" ||
        store.state.role == "TEACHER" ||
        store.state.role == "ASSISTANT"
      ) {
        console.log("true");
        return true;
      } else {
        console.log("false");
        return false;
      }
    });

    const clickLink = (route) => {
      expanded.value = false;

      router.push(route);
    };

    const pushToHome = () => {
      router.push("/");
    }

    return {
      properRank,
      expanded,
      openMenu,
      clickLink,
      pushToHome
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
  margin: 15px 0 0 30px;
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
  display: flex;
}

#right-expanded {
  display: none;
}

li.router-link-active,
li.router-link-exact-active {
  background-color: rgb(223, 9, 176);
  color: green;
  cursor: pointer;
}

@media only screen and (max-width: 800px) {
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
    padding: 10px;
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
