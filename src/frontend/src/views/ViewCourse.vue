<template>
  <div>
    <h1>{{ course.code + " " + course.title }}</h1>
    <div id="spacer">
      <h2>Task summary</h2>
      <div id="list-of-tasks">
        <div
          id="taskgroup-grid"
          v-for="taskGroup in course.taskGroupProgress"
          :key="taskGroup.id"
        >
          <div id="group-header">Group {{ taskGroup.number }}</div>
          <div id="taskgroup">
            <div v-for="task in taskGroup.taskProgress" :key="task.id">
              <div :class="task.approved ? 'task-1' : 'task-2'">
                {{ task.number }}
              </div>
            </div>
          </div>
          <div id="group-status">
            {{ taskGroup.completed }} completed /
            {{ taskGroup.required }} required
          </div>
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
    const course = ref({});
    let taskGroups = ref([]);

    onMounted(() => {
      http.get("/courses/progress/" + route.params.id).then((response) => {
        console.log(response.data);
        course.value = response.data;
      });
    });

    return { course, taskGroups };
  },
};
</script>

<style></style>
