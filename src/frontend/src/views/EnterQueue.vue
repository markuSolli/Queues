<template>
  <div>
    <h1>Entering queue...</h1>
    <div id="spacer">
      <h2>Select task:</h2>
      <div id="list-of-tasks">
        <div class="task-1" @click="taskClick(1)">1</div>
        <div class="task-1" @click="taskClick(2)">2</div>
        <div class="task-1" @click="taskClick(3)">3</div>
        <div class="task-1" @click="taskClick(4)">4</div>
        <div class="task-2" @click="taskClick(5)">5</div>
        <div class="task-2" @click="taskClick(6)">6</div>
        <div class="task-2" @click="taskClick(7)">7</div>
      </div>
      <div v-if="selectedTask != 0">
        <h3>Selected task: {{ selectedTask }}</h3>
      </div>
    </div>
    <div id="spacer">
      <h2>Select room:</h2>
    </div>
    <div id="spacer">
      <h2>Select type:</h2>
      <div id="help-approval">
        <Button
          v-bind:class="{ buttonSelected: help }"
          :title="'Help'"
          :route="'courseQueue'"
          :func="clickHelp"
        />
        <Button
          v-bind:class="{ buttonSelected: approval }"
          :title="'Approval'"
          :route="'courseQueue'"
          :func="clickApproval"
        />
      </div>
    </div>
    <div id="button-pos">
      <Button :title="'Enter queue'" :route="'courseQueue'" />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";

export default {
  components: { Button },
  setup() {
    let selectedTask = ref(0);
    let help = ref(false);
    let approval = ref(false);

    const taskClick = (taskNumber) => {
      console.log(taskNumber);
      selectedTask.value = taskNumber;
    };

    const clickHelp = () => {
      help.value = true;
      approval.value = false;
    };
    const clickApproval = () => {
      help.value = false;
      approval.value = true;
    };

    return {
      taskClick,
      selectedTask,
      clickHelp,
      clickApproval,
      help,
      approval,
    };
  },
};
</script>

<style>
#help-approval {
  display: flex;
  margin: 20px auto;
  width: 400px;
  justify-content: space-evenly;
}

.buttonSelected {
  background: rgb(212, 145, 0);
  color: black;
}

#select-room {
  margin: 25px 0px;
}

#button-pos {
  display: flex;
  justify-content: center;
}

#spacer {
  margin: 75px 0px;
}

#list-of-tasks {
  justify-content: space-around;
  background: rgb(255, 255, 255);
  display: flex;
  margin: 25px 0px;
  border: 2px black solid;
  padding: 20px;
  border-radius: 20px;
}

.task-1,
.task-2 {
  background: green;
  border-radius: 50%;
  height: 40px;
  width: 40px;
  font-size: 30px;
  padding: 25px;
}

.task-2 {
  background: red;
}

.task-1:hover,
.task-2:hover {
  background: rgb(212, 145, 0);
  cursor: pointer;
}
</style>
