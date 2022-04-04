<template>
  <div>
    <h1>Entering queue...</h1>
    <div id="spacer">
      <h2>Select task:</h2>
      <div id="list-of-tasks">
        <div
          id="taskgroup-grid"
          v-for="taskGroup in taskGroupProgress"
          :key="taskGroup.id"
        >
          <div id="group-header">Group {{ taskGroup.number }}</div>
          <div id="taskgroup">
            <div v-for="task in taskGroup.taskProgress" :key="task.id">
              <div
                :class="[
                  task.approved ? 'task-1' : 'task-2',
                  task.number === selectedTask ? 'selected-task' : null,
                ]"
                @click="taskClick(task.number, task.id)"
                class="task-1"
              >
                {{ task.number }}
              </div>
            </div>
          </div>
          <div id="group-status">
            {{ taskGroup.completed }} completed / {{ taskGroup.required }}
            required
          </div>
        </div>
      </div>
      <div v-if="selectedTask != 0">
        <h3>Selected task: {{ selectedTask }}</h3>
      </div>
    </div>
    <div id="spacer">
      <h2>Select room:</h2>
      <div>
        Selected room: {{ selectedRoom.buildingName }}
        {{ selectedRoom.roomName }}
      </div>
    </div>
    <div id="spacer">
      <h2>Select type:</h2>
      <div id="help-approval">
        <Button
          v-bind:class="{ buttonSelected: help }"
          :title="'Help'"
          @click="clickHelp"
        />
        <Button
          v-bind:class="{ buttonSelected: approval }"
          :title="'Approval'"
          @click="clickApproval"
        />
      </div>
    </div>
    <div id="button-pos">
      <Button :title="'Enter queue'" @click="enterQueue" />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";
import { useRoute } from "vue-router";
import { onMounted } from "@vue/runtime-core";
import http from "@/service/http-common";
import router from "../router";

export default {
  components: { Button },
  setup() {
    const route = useRoute();
    let selectedTask = ref(0);
    let selectedTaskId = ref();
    let help = ref(false);
    let approval = ref(true);
    let course = ref({});
    let taskGroupProgress = ref([]);

    onMounted(() => {
      http.get("/courses/progress/" + route.params.id).then((response) => {
        console.log(response.data.taskGroupProgress);
        course.value = response.data;
        taskGroupProgress.value = response.data.taskGroupProgress;
      });
    });

    const taskClick = (taskNumber, taskId) => {
      selectedTask.value = taskNumber;
      selectedTaskId.value = taskId;
      console.log(selectedTaskId.value);
    };

    const clickHelp = () => {
      help.value = true;
      approval.value = false;
    };
    const clickApproval = () => {
      help.value = false;
      approval.value = true;
    };
    const enterQueue = () => {
      http
        .post("/queue/" + route.params.id + "/" + selectedTaskId.value, {
          help: help.value,
        })
        .then((response) => {
          console.log(response.data);

          // redirect to queue in successfull
          router.push({
            name: "courseQueue",
            params: {
              id: route.params.id,
            },
          });
        });
    };

    return {
      taskClick,
      selectedTask,
      clickHelp,
      clickApproval,
      enterQueue,
      help,
      approval,
      course,
      taskGroupProgress,
      mapRef,
      selectedRoom,
    };
  },
};
</script>

<style>
#group-header {
  grid-row: 1/2;
  font-size: 25px;
}

#group-status {
  grid-row: 3 /4;
  justify-self: center;
  align-self: center;
  font-size: 20px;
}

#taskgroup-grid {
  display: grid;
  font-weight: 200;
  color: white;
  padding: 0px 10px;
  border-radius: 20px;
  grid-template-rows: 1fr 1fr 1fr;
}

#taskgroup {
  grid-row: 2 /3;
  display: flex;
}

#help-approval {
  display: flex;
  margin: 20px 0px;
  justify-content: center;
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
  color: white;
  flex-flow: row wrap;
  justify-content: space-around;
  display: flex;
  margin: 25px 0px;
  padding: 20px;
  border-radius: 20px;
}

.task-1,
.task-2 {
  background: green;
  border-radius: 10px;

  font-size: 30px;
  padding: 5px 40px;
  margin: 0px 5px;
}

.task-2 {
  background: red;
}

.task-1:hover,
.task-2:hover {
  background: rgb(212, 145, 0);
  cursor: pointer;
}

.selected-task {
  background: rgb(212, 145, 0);
}
</style>
