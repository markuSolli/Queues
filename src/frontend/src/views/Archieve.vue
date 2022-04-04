<template>
  <div>
    <h1>Archive</h1>
    <div id="management-bar">
      <div id="title"><h2>Archived courses</h2></div>
    </div>

    <div v-for="course in archivedCourses" :key="course.id + course.archived" id="course-list">
      <CourseCard @refresh="refresh" :course="course" />
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

    const refresh = () => {
      console.log("refresh");
      http.get("/courses?archived=true").then((response) => {
        archivedCourses.value = response.data;
      });
    }

    return {
      archivedCourses,
      refresh
    };
  },
};
</script>

<style></style>
