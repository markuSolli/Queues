<template>
  <div id="button">
    <div>
      <router-link
        v-if="!func"
        class="button-text"
        tag="li"
        :to="'/' + route"
        >{{ title }}</router-link
      >
      <div @click="func" v-else>{{ title }}</div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  props: ["title", "route", "func"],
  setup(props) {
    const route = ref(props.route);
    const func = props.func;

    return {
      route,
      func,
    };
  },
};
</script>

<style>
#button {
  cursor: pointer;
  border: 1px white solid;
  border-radius: 12px;
  padding: 10px;
  width: 150px;
}

#button:hover ~ .button-text {
  color: grey;
}

.button-text {
  color: white;
  text-decoration: none;
}
</style>
