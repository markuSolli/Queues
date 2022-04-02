<template>
  <div>
    <h1>Course name</h1>
    <div id="teachers">
      <h2>Teachers</h2>
      <div id="user-list" v-for="teacher in teachers" :key="teacher.id">
        <div id="element-1">{{ teacher.email }}</div>
        <div id="element-2">{{ teacher.firstName }}</div>
        <div id="element-3">{{ teacher.lastName }}</div>
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
    let students = ref([]);
    let teachers = ref([]);
    let assistants = ref([]);
    let taskGroups = ref();

    onMounted(() => {
      http.get("/courses/" + route.params.id).then((response) => {
        const course = response.data;
        console.log(course);
        // add users to lists
        for (const user in course.users) {
          if (course.users[user].role == "TEACHER")
            teachers.value.push(course.users[user].user);
          if (course.users[user].role == "STUDENT")
            students.value.push(course.users[user].user);
          if (course.users[user].role == "ASSISTANT")
            assistants.value.push(course.users[user].user);
        }

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

    return { taskGroups, students, teachers, assistants };
  },
};
</script>

<style>
#teachers {
  justify-content: start;
}

#user-list {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
}

#element-1 {
  grid-column: 1/2;
}
#element-2 {
  grid-column: 2/3;
}
#element-3 {
  grid-column: 3/4;
}
</style>
