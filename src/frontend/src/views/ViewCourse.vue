<template>
  <div>
    <h1>Course name</h1>
    <div id="spacer">
      <h2>Task summary</h2>
      <div id="list-of-tasks">
        <div
          id="taskgroup-grid"
          v-for="taskGroup in taskGroups"
          :key="taskGroup.id"
        >
          <div id="group-header">Group {{ taskGroup.number }}</div>
          <div id="taskgroup">
            <div v-for="task in taskGroup.tasks" :key="task.id">
              <div class="task-1">
                {{ task.number }}
              </div>
            </div>
          </div>
          <div id="group-status">Group status: / {{ taskGroup.required }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import { onBeforeMount, onMounted } from "@vue/runtime-core";
import http from "../service/http-common";
import { useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    let taskGroups = ref();

    onMounted(() => {
      http.get("/courses/" + route.params.id).then((response) => {
        const course = response.data;

        // sort groups
        course.taskGroups.sort(function (a, b) {
          return a.number - b.number;
        });

        // sort tasks
        for (const taskGroup in course.taskGroups) {
          course.taskGroups[taskGroup].tasks.sort(function (a, b) {
            return a.number - b.number;
          });
        }

        taskGroups.value = course.taskGroups;
      });
    });

    return { taskGroups };
  },
};
</script>

<style></style>
