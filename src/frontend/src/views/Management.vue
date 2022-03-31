<template>
  <div>
    <h1>Management</h1>
    <div id="management-bar">
      <div id="title"><h2>Your courses</h2></div>
      <div id="buttons">
        <Button :title="'Add new course'" :route="'course'" />
      </div>
    </div>
    <div v-for="course in courses" :key="course.id" id="course-list">
      <CourseCard :edit="true" :name="course.title" :id="course.id" />
    </div>
    <div id="management-bar">
      <div id="title"><h2>Users</h2></div>
      <div id="buttons">
        <Button :title="'Add new user'" @click="addUserInterfaceOn" />
      </div>
    </div>
    <UserCard
      :email="'Email'"
      :firstname="'Firstname'"
      :lastname="'Lastname'"
      :role="'Role'"
      :edit="false"
    />
    <UserCard
      v-if="create"
      :create="true"
      :interfaceOff="addUserInterfaceOff"
      :addUser="addUser"
    />
    <div v-for="user in listOfUsers" :key="user.email">
      <UserCard
        :email="user.email"
        :firstname="user.firstname"
        :lastname="user.lastname"
        :role="user.role"
        :edit="true"
      />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";
import CourseCard from "../components/CourseCard.vue";
import UserCard from "../components/UserCard.vue";
import { computed, onMounted } from "@vue/runtime-core";
import {fetchCourses} from "@/service/CourseService";

export default {
  components: { Button, CourseCard, UserCard },
  setup() {
    let create = ref(false);
    let courses = ref();

    onMounted(() => {
      fetchCourses().then((response) => {
        courses.value = response.data;
      });
    });

    let listOfUsers = ref([
      {
        email: "asdasdasd",
        firstname: "thor",
        lastname: "dfdfdf",
        role: 0,
      },
      { email: "123123", firstname: "thor1", lastname: "dfdfdf2", role: 1 },
      { email: "ghghgh", firstname: "thor2", lastname: "dfdfdf3", role: 1 },
      { email: "ghghgh2", firstname: "thor2", lastname: "dfdfdf3", role: 2 },
      { email: "ghghgh4", firstname: "thor2", lastname: "dfdfdf3", role: 3 },
    ]);

    const addUserInterfaceOn = () => {
      create.value = true;
    };
    const addUserInterfaceOff = () => {
      create.value = false;
    };
    const addUser = (email, firstname, lastname, role) => {
      // add user to database
      console.log(email + firstname + lastname + role);

      // if succesfull close window
      addUserInterfaceOff();
    };

    return {
      listOfUsers,
      create,
      courses,
      addUserInterfaceOn,
      addUserInterfaceOff,
      addUser,
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
