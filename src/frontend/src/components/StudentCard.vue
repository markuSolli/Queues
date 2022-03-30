<template>
  <div id="full">
    <div id="studentcard-container" v-bind:class="{ guide: guide }">
      <div class="element-1">{{ firstname }} {{ lastname }}</div>
      <div class="element-2">{{ type }}</div>
      <div class="element-3">
        {{ time }}
      </div>
      <div class="element-4">{{ studass }}</div>
    </div>
    <div id="being-approved" v-if="!guide"></div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
export default {
  props: [
    "email",
    "firstname",
    "lastname",
    "time",
    "type",
    "studentAssistant",
    "guide",
  ],
  setup(props) {
    const email = ref(props.email);
    const firstname = ref(props.firstname);
    const lastname = ref(props.lastname);
    const time = ref(props.time);
    const type = ref(props.type);
    const studass = ref(props.studentAssistant);
    const guide = ref(props.guide);

    return {
      email,
      firstname,
      lastname,
      time,
      type,
      studass,
      guide,
    };
  },
};
</script>

<style>
#full {
  display: flex;
}

#studentcard-container {
  background: rgb(7, 3, 29);
  /*background: linear-gradient(
    217deg,
    rgba(7, 3, 29, 1) 0%,
    rgba(0, 0, 0, 1) 100%
  );*/
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  text-align: left;
  padding: 25px;
  margin: 0px 0px 10px 0px;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  flex: 1 1 auto;
  font-size: 16px;
}

.guide {
  background: transparent;
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
}

#being-approved {
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  background-color: red;
  max-width: 10px;
  min-width: 10px;
  margin: 0px 0px 10px 0px;
}

#studentcard-container:hover {
  cursor: pointer;
}

#prog-text {
  display: inline-block;
  margin-right: 15px;
}

.element-1 {
  grid-column: 1 / 2;
}

.element-2 {
  grid-column: 2 / 3;
  justify-self: start;
}

.element-3 {
  grid-column: 3 / 4;
  justify-self: start;
  font-size: 16px;
}

.element-4 {
  grid-column: 4 / 5;
  justify-self: start;
}
</style>
