<template>
  <div>
    <div class="add-row">
      <div class="add-row-left">
        <h2>{{ title }}</h2>
      </div>
      <div class="add-row-right">
        <Button :title="'Add group'" @click="addGroup(list)" />
      </div>
    </div>
    <div
      v-for="taskgroup in list.taskgroups"
      :key="taskgroup.number"
      class="person-list"
    >
      <div id="task-group">
        <div id="group-header">
          <h3>Group {{ taskgroup.number }}</h3>
        </div>
        <div id="group-description">
          <h4>Tasks required to pass:</h4>
          <input
            v-model="number"
            placeholder="Number"
            id="amount-input"
            type="number"
            @input="updateRequired(taskgroup, number)"
          />
          <h4>
            /
            {{ taskgroup.total }}
          </h4>
        </div>
        <div id="group-buttons">
          <Button
            :title="'Add Task'"
            @click="addTask(list, taskgroup.number)"
          />

          <Button
            :title="'Remove group'"
            @click="removeGroup(list, taskgroup.number)"
          />
        </div>
      </div>
      <div id="tasks" v-for="task in taskgroup.tasks" :key="task.number">
        <div id="header">Task {{ task.number }}</div>
        <div id="buttons">
          <Button
            :title="'Remove'"
            @click="removeTask(list, taskgroup.number)"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";

import Button from "./Button.vue";

export default {
  components: { Button },
  props: ["title", "list"],
  setup(props) {
    const title = ref(props.title);
    const list = ref(props.list);

    const updateRequired = (taskgroup, number) => {
      if (number >= 0 && number <= taskgroup.total) {
        taskgroup.required = number;
      }
    };

    const updateProperRequired = (e) => {};

    const addGroup = (list) => {
      const length = list.taskgroups.length + 1;

      list.taskgroups.push({
        number: length,
        tasks: [],
      });
    };
    const removeGroup = (list, number) => {
      list.taskgroups.splice(number - 1, 1);

      for (let i = 0; i < list.taskgroups.length; i++) {
        list.taskgroups[i].number = i + 1;
      }
    };

    const addTask = (list, number) => {
      for (const groupNumber in list.taskgroups) {
        const taskgroup = list.taskgroups[groupNumber];

        if (taskgroup.number == number) {
          const length = taskgroup.tasks.length + 1;

          taskgroup.tasks.push({ number: length });

          taskgroup.total = taskgroup.tasks.length;
        }
      }
    };
    const removeTask = (list, number) => {
      for (const groupNumber in list.taskgroups) {
        const taskgroup = list.taskgroups[groupNumber];

        if (taskgroup.number == number) {
          taskgroup.tasks.pop();

          taskgroup.total = taskgroup.tasks.length;
        }
      }
    };

    return {
      title,
      list,
      addGroup,
      removeGroup,
      addTask,
      removeTask,
      updateRequired,
    };
  },
};
</script>

<style>
#amount-input {
  height: 20px;
  width: 75px;
  margin: 0px 10px;
  background: transparent;
  border-bottom: 1px solid white;
  color: white;
  border-top: none;
  border-left: none;
  border-right: none;
  text-align: center;
  font-size: 16px;
}

#tasks {
  display: grid;
  grid-auto-columns: 1fr 1fr 1fr;
  padding: 5px 20px;
  margin-top: 2px;
  background: rgb(7, 3, 29);
}

#group-header {
  grid-column: 1/2;
  justify-self: start;
  align-self: center;
}

#group-description {
  display: flex;
  grid-column: 2/3;
  justify-self: center;
  align-self: center;
}

#group-buttons {
  display: flex;
  grid-column: 3/4;
  justify-self: end;
}

#header {
  grid-column: 1/2;
  justify-self: start;
  align-self: center;
}

#buttons {
  grid-column: 2/3;
  justify-self: end;
}

#task-group {
  display: grid;
  grid-template-columns: 1fr 1fr;

  margin: 20px 0px 0px 0px;
  padding: 10px 20px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  background: rgb(7, 3, 29);
}

.add-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin: 50px 0px;
  border-bottom: 1px white solid;
  padding-bottom: 10px;
}

.add-row-left {
  justify-self: start;
  grid-column: 1 /2;
}
.add-row-right {
  display: flex;
  justify-self: end;
  grid-column: 2 / 3;
}
</style>
