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
import http from "@/service/http-common";
import { ref } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";

export default {
  components: { CourseCard },
  setup() {
    let archivedCourses = ref();

    onMounted(() => {
      http.get("/courses?archived=true").then((response) => {
        archivedCourses.value = response.data;
      });
    });

    return {
      archivedCourses,
    };
  },
};
</script>

<style></style>
