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
    </div>

    <div class="row">
      <input
        class="input-field"
        type="text"
        v-model="title"
        placeholder="Title"
      />
    </div>

    <div class="row">
      <div id="date-row">
        <h2>Select start and end date</h2>
        <div id="datepicker">
          <Datepicker range v-model="date"></Datepicker>
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
import Datepicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";
import PersonList from "../components/PersonList.vue";
import TaskList from "../components/TaskList.vue";
import router from "../router";

export default {
  components: { Datepicker, Button, PersonList, TaskList },
  setup() {
    const title = ref("");
    const code = ref("");
    const date = ref();
    let listOfTeachers = ref([
      { email: "asdasdasd", firstname: "thor", lastname: "dfdfdf" },
      { email: "123123", firstname: "thor1", lastname: "dfdfdf2" },
      { email: "ghghgh", firstname: "thor2", lastname: "dfdfdf3" },
    ]);
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
      taskgroups: [
        {
          number: 1,
          tasks: [
            {
              number: 1,
            },
            {
              number: 2,
            },
            {
              number: 3,
            },
            {
              number: 4,
            },
          ],
          required: 2,
          total: 4,
        },
        {
          number: 2,
          tasks: [
            {
              number: 5,
            },
            {
              number: 6,
            },
            {
              number: 7,
            },
          ],
          required: 2,
          total: 3,
        },
        {
          number: 3,
          tasks: [
            {
              number: 8,
            },
          ],
          required: 1,
          total: 1,
        },
      ],
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
