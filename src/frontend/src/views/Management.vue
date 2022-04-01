<template>
  <div>
    <h1>Management</h1>
    <div id="management-bar">
      <div id="title"><h2>Your courses</h2></div>
      <div id="buttons">
        <Button :title="'Add new course'" :route="'course'" />
        <Button :title="'Manage users'" :route="'manageUsers'" />
        <Button :title="'Manage rooms'" :route="'manageUsers'" />
      </div>
    </div>
    <div v-for="course in courses" :key="course.id" id="course-list">
      <CourseCard :course="course" />
    </div>
    
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";
import CourseCard from "../components/CourseCard.vue";
import UserCard from "../components/UserCard.vue";
import { computed, onMounted } from "@vue/runtime-core";
import http from "@/service/http-common"

export default {
  components: { Button, CourseCard, UserCard },
  setup() {
    let courses = ref();

    onMounted(() => {
      http.get("/courses").then((response) => {
        let notArchived = [];

        for (const course in response.data) {
          if (!response.data[course].archived) {
            notArchived.push(response.data[course]);
          }
        }
        courses.value = notArchived;
      });
    });

 

    return {
      courses,
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
  grid-column: 2 / 3;
  display: flex;
}
</style>
