<template>
  <div>
    <h1>Archieve</h1>
    <div id="management-bar">
      <div id="title"><h2>Archieved courses</h2></div>
    </div>

    <div id="course-list">
      <CourseCard :archieved="true" />
      <CourseCard :archieved="true" />
      <CourseCard :archieved="true" />
      <CourseCard :archieved="true" />
    </div>
  </div>
</template>

<script>
import CourseCard from "../components/CourseCard.vue";

export default {
  components: { CourseCard },
};
</script>

<style></style>
