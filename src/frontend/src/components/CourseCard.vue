<template>
  <div id="card">
    <div class="element-1"><h3>Name of course</h3></div>
    <div class="element-2" v-if="edit || archieved || inactive"></div>
    <div class="element-2" v-else>
      <h4>
        <router-link class="link" tag="li" to="/courseQueue"
          >Go to queue -></router-link
        >
      </h4>
    </div>
    <div class="element-3"><h3>course description should be here</h3></div>
    <div class="element-4" v-if="!edit">
      <div id="prog-text"><h4>Course progress:</h4></div>
      <div class="loadingBar-1">1</div>
      <div class="loadingBar-1">2</div>
      <div class="loadingBar-1">3</div>
      <div class="loadingBar-1">4</div>
      <div class="loadingBar-2">5</div>
      <div class="loadingBar-2">6</div>
      <div class="loadingBar-2">7</div>
    </div>
    <div class="element-4" v-if="edit">
      <Button :title="'Edit course'" :route="'course'" />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "./Button.vue";

export default {
  props: ["edit", "archieved", "inactive"],
  components: { Button },
  setup(props) {
    let edit = ref(props.edit);
    let archieved = ref(props.archieved);
    let inactive = ref(props.inactive);

    const editCourse = () => {};

    return {
      edit,
      archieved,
      inactive,
    };
  },
};
</script>

<style>
#card {
  height: 60px;
  background: rgb(7, 3, 29);
  /*background: linear-gradient(
    217deg,
    rgba(7, 3, 29, 1) 0%,
    rgba(0, 0, 0, 1) 100%
  );*/
  border-radius: 10px;
  text-align: left;
  padding: 25px;
  margin: 0px 0px 10px 0px;
  display: grid;
  grid-template-areas: "element-1 element-2" "element-3 element-4";
}

#prog-text {
  display: inline-block;
  margin-right: 15px;
}

.circle-1,
.circle-2 {
  background: red;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: inline-block;
  margin: 2px 2px 2px 2px;
  font-size: 14px;
  text-align: center;
  padding: 3px;
}

.circle-1 {
  background: rgb(5, 158, 0);
}

.loadingBar-1,
.loadingBar-2 {
  background: red;
  width: 20px;
  height: 20px;
  display: inline-block;
  margin: 0px;
  font-size: 14px;
  text-align: center;
  padding: 3px;
  border: 1px rgb(37, 37, 37) solid;
}

.loadingBar-2 {
  background: rgb(5, 158, 0);
}

.element-1 {
  grid-area: element-1;
}

.element-2 {
  text-align: right;
  grid-area: element-2;
}

.element-3 {
  justify-self: start;
  grid-area: element-3;
  font-size: 12px;
}

.element-4 {
  grid-area: element-4;
  justify-self: end;
  align-self: end;
}
</style>
