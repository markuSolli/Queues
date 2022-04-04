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
  border-radius: 10px;
  padding: 5px 15px;
  margin: 0px 5px;
  text-align: center;
  font-size: 15px;
}

#button:hover ~ .button-text,
#button:hover {
  color: rgb(255, 255, 255);
  background: rgb(14, 7, 54);
}

.button-text {
  color: white;
  text-decoration: none;
}

@media only screen and (max-width: 500px) {
  #button {
    cursor: pointer;
    border: 1px white solid;
    border-radius: 10px;
    padding: 5px 5px;
    margin: 0px 5px;
    text-align: center;
    font-size: 12px;
  }
}
</style>
