<template>
  <div>
    <div id="queue-container">
      <div id="queue-container-header">
        <h1><i class="fa-solid fa-toggle-on"></i> Active</h1>
      </div>

      <div v-for="course in activeCourses" :key="course.id">
        <CourseCard :course="course" :cardInQueue="true" />
      </div>
    </div>

    <div id="queue-container">
      <div id="queue-container-header">
        <h1><i class="fa-solid fa-toggle-off"></i> Inactive</h1>
      </div>
      <div v-for="course in inActiveCourses" :key="course.id + course.active">
        <CourseCard :course="course" :cardInQueue="true" />
      </div>
    </div>
  </div>
</template>

<script>
import CourseCard from "../components/CourseCard";
import { ref } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import http from "@/service/http-common";

export default {
  components: { CourseCard },
  setup() {
    let activeCourses = ref();
    let inActiveCourses = ref();

    onMounted(() => {
      http.get("/courses/progress").then((response) => {
        console.log(response.data);
        let active = [];
        let inactive = [];

        for (const course in response.data) {
          if (response.data[course].active) {
            active.push(response.data[course]);
          } else {
            inactive.push(response.data[course]);
          }
        }

        activeCourses.value = active;
        inActiveCourses.value = inactive;
      });
    });

    return {
      activeCourses,
      inActiveCourses,
    };
  },
};
</script>

<style>
#queue-container {
  margin: 20px 0px 0px 0px;

  border-color: linear-gradient(
    17deg,
    rgba(16, 16, 17, 1) 38%,
    rgba(0, 0, 0, 1) 100%
  );

  padding: 10px;
  align-content: center;
}

#queue-container-header {
  padding: 20px;
}
</style>
