<template>
  <div>
    <h1>Archieve</h1>
    <div id="management-bar">
      <div id="title"><h2>Archieved courses</h2></div>
    </div>

    <div v-for="course in archivedCourses" :key="course.id" id="course-list">
      <CourseCard :course="course" />
    </div>
  </div>
</template>

<script>
import CourseCard from "../components/CourseCard";
import axios from "axios";
import { ref } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";

export default {
  components: { CourseCard },
  setup() {
    let archivedCourses = ref();

    onMounted(() => {
      axios.get("http://localhost:3000/courses").then((response) => {
        let archived = [];

        for (const course in response.data) {
          if (response.data[course].archived) {
            archived.push(response.data[course]);
          }
        }

        archivedCourses.value = archived;
      });
    });

    return {
      archivedCourses,
    };
  },
};
</script>

<style></style>
