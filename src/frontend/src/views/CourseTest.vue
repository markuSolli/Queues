<template>
  <div>
    <h1>Course</h1>

    <div class="row">
      <input
        class="input-field"
        type="text"
        v-model="code"
        placeholder="Code"
      />
      <input
        class="input-field"
        type="text"
        v-model="title"
        placeholder="Title"
      />
      <div id="date-row">
        <h3>Start and end date</h3>
        <div id="datepicker">
          <!--<Datepicker range v-model="date"></Datepicker>-->
        </div>
      </div>
    </div>

    <div class="row">
      <TaskList :title="'Tasks'" :list="tasks" />
    </div>

    <div class="row">
      <PersonList :title="'Teachers'" :list="listOfTeachers" />
    </div>

    <div class="row">
      <PersonList :title="'Student assistants'" :list="listOfStudAss" />
    </div>

    <div class="row">
      <PersonList :title="'Students'" :list="listOfStudents" />
    </div>

    <div class="row">
      <div id="done-button">
        <Button :title="'Done'" @click="createCourse" />
      </div>
    </div>
  </div>
</template>

<script>
//import Datepicker from "@vuepic/vue-datepicker";
//import "@vuepic/vue-datepicker/dist/main.css";
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";
import PersonList from "../components/PersonList.vue";
import TaskList from "../components/TaskList.vue";
import router from "../router";
import axios from "axios";

export default {
  components: { Button, PersonList, TaskList },
  async setup() {
    const response = await axios.get("http://localhost:3000/courses");
    console.log(response.data);
    const course = response.data[0];
    const title = ref(course.title);
    const code = ref(course.code);
    // const date = ref(course.startDate);
    // let listOfTeachers = ref(course.users);
    let listOfStudents = ref([
      { email: "student1", firstname: "thor", lastname: "dfdfdf" },
      { email: "student3", firstname: "thor1", lastname: "dfdfdf2" },
      { email: "student4", firstname: "thor2", lastname: "dfdfdf3" },
    ]);
    let listOfStudAss = ref([
      { email: "studass1", firstname: "thor", lastname: "dfdfdf" },
      { email: "studass2", firstname: "thor1", lastname: "dfdfdf2" },
      { email: "studass3", firstname: "thor2", lastname: "dfdfdf3" },
    ]);
    let tasks = ref({
      // taskgroups: course.tasks
    });

    const createCourse = () => {
      // validate title, code and date
      console.log(title.value);
      console.log(code.value);
      console.log(date.value);

      // redirect to management page
      router.push("/management");
    };

    return {
      title,
      code,
      date,
      listOfTeachers,
      listOfStudents,
      listOfStudAss,
      tasks,
      createCourse,
    };
  },
};
</script>

<style>
#done-button {
  display: flex;
  justify-content: center;
}

#datepicker {
  width: 350px;
  margin-left: 10px;
}

#date-row {
  display: flex;
  justify-content: center;

  padding: 20px;
}

.row {
  margin: 30px 0px;
}
</style>
