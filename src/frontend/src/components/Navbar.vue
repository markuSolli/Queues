<template>
  <div id="nav">
    <div id="left">
      <h1>Queues</h1>
      <router-link class="link" tag="li" to="/">Courses</router-link>
      <router-link class="link" tag="li" to="/about">About</router-link>
      <router-link class="link" tag="li" to="/archieve">Archieve</router-link>
      <router-link v-if="properRank" class="link" tag="li" to="/management"
        >Management</router-link
      >
    </div>
    <div id="right">
      <router-link class="link" tag="li" to="/profile">Profile</router-link>
    </div>
  </div>
</template>

<script>
import { computed } from "@vue/runtime-core";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();

    const properRank = computed(() => {
      if (store.state.rank < 2) {
        return true;
      } else {
        return false;
      }
    });

    return {
      properRank,
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
</style>
