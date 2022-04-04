<template>
  <div>
    <h1>Management</h1>
    <div id="management-bar">
      <div id="title"><h2>Your courses</h2></div>
      <div id="buttons">
        <Button :title="'Add new course'" :route="'course'" />
        <Button :title="'Manage users'" :route="'manageUsers'" />
      </div>
    </div>
    <div v-for="course in courses" :key="course.id + course.archived + course.active" id="course-list">
      <CourseCard @refresh="refresh" :course="course" />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";
import CourseCard from "../components/CourseCard.vue";
import UserCard from "../components/UserCard.vue";
import { computed, onMounted } from "@vue/runtime-core";
import http from "@/service/http-common";
import router from "../router";

export default {
  components: { Button, CourseCard, UserCard },
  setup() {
    let courses = ref();

    onMounted(() => {
      http.get("/courses?archived=false").then((response) => {
        courses.value = response.data;
      });
    });

    const refresh = () => {
      console.log("refresh");
      http.get("/courses").then((response) => {
        courses.value = response.data;
      });
    }

    return {
      courses,
      refresh
    };
  },
};
</script>

<style>
#management-bar {
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin: 25px 0px;
  padding: 25px;
}

#title {
  justify-self: start;
  grid-column: 1 / 2;
}

#buttons {
  justify-self: end;
  align-self: center;
  grid-column: 2 / 3;
  display: flex;
}
</style>
