<template>
  <div id="button">
    <div>
      <router-link
        v-if="route"
        class="button-text"
        tag="li"
        :to="'/' + route"
        >{{ title }}</router-link
      >
      <div v-else>{{ title }}</div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  props: ["title", "route"],
  setup(props) {
    const route = ref(props.route);

    return {
      route,
    };
  },
};
</script>

<style>
#button {
  cursor: pointer;
  border: 1px white solid;
  border-radius: 12px;
  padding: 10px 20px;
  margin: 0px 5px;
  text-align: center;
}

#button:hover ~ .button-text {
  color: grey;
}

.button-text {
  color: white;
  text-decoration: none;
}
</style>
