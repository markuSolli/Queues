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
        <div id="datepicker">
          <!--<Datepicker range v-model="date"></Datepicker>-->
          <div>Start date: <input type="date" v-model="startDate" /></div>

          <div>End date: <input type="date" v-model="endDate" /></div>
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
      <div id="done-text">{{ status }}</div>
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
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import { onMounted } from "@vue/runtime-core";
import http from "@/service/http-common";

export default {
  components: { Button, PersonList, TaskList },
  setup() {
    const route = useRoute();
    const status = ref("");
    const title = ref("");
    const code = ref("");
    const startDate = ref("");
    const endDate = ref("");
    let listOfTeachers = ref([]);
    let listOfStudents = ref([]);
    let listOfStudAss = ref([]);
    let tasks = ref({
      taskgroups: [],
    });

    // EDIT
    onMounted(() => {
      if (route.params.id) {
        console.log("Params!");
        http
          .get("/courses/" + route.params.id)
          .then((response) => {
            const course = response.data;
            title.value = course.title;
            code.value = course.code;
            startDate.value = course.startDate;
            endDate.value = course.endDate;
            for (const user in course.users) {
              if (course.users[user].role == "TEACHER")
                listOfTeachers.value.push(course.users[user].user);
              if (course.users[user].role == "ASSISTANT")
                listOfStudAss.value.push(course.users[user].user);
              if (course.users[user].role == "STUDENT")
                listOfStudents.value.push(course.users[user].user);
            }
          });
        console.log("yo");

      } else {
        console.log("No params");
        // if this course page is not edit, load current teacher first in teacher list

        const store = useStore();
        if (store.state.role == 1) {
          listOfTeachers.value.push({
            email: store.state.email,
            firstname: store.state.firstname,
            lastname: store.state.lastname,
          });
        }
        createCourse = () => {
          // validate title, code and date
          if (
              title.value == "" ||
              code.value == "" ||
              startDate.value == "" ||
              endDate.value == ""
          ) {
            status.value = "Fields cant be empty";
          } else {
            http
                .post("/courses", {
                  code: code.value,
                  title: title.value,
                  startDate: startDate.value,
                  endDate: endDate.value,
                  taskGroups: tasks.value.taskgroups,
                  students: listOfStudents.value,
                  assistants: listOfStudAss.value,
                  teachers: listOfTeachers.value,
                })
                .then((response) => {
                  if (response.status == 201) {
                    router.push("/management");
                  } else {
                    status.value = "Something went wrong";
                  }
                });
          }
        };

      }
    });

    let createCourse = () => {
      // validate title, code and date
      if (
          title.value == "" ||
          code.value == "" ||
          startDate.value == "" ||
          endDate.value == ""
      ) {
        status.value = "Fields cant be empty";
        return;
      }
      if(route.params.id) {
        http
            .put("/courses/" + route.params.id, {
              code: title.value,
              title: code.value,
              startDate: startDate.value,
              endDate: endDate.value,
              taskGroups: tasks.value.taskgroups,
              students: listOfStudents.value,
              assistants: listOfStudAss.value,
              teachers: listOfTeachers.value
            })
            .then((response) => {
              console.log(response.status);
              if (response.status == 200) {
                router.push("/management");
              } else {
                status.value = "Something went wrong";
              }
            });
      } else {
        http
            .post("/courses", {
              code: code.value,
              title: title.value,
              startDate: startDate.value,
              endDate: endDate.value,
              taskGroups: tasks.value.taskgroups,
              students: listOfStudents.value,
              assistants: listOfStudAss.value,
              teachers: listOfTeachers.value,
            })
            .then((response) => {
              if (response.status == 201) {
                router.push("/management");
              } else {
                status.value = "Something went wrong";
              }
            });
      }
    };


    return {
      status,
      title,
      code,
      startDate,
      endDate,
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
#done-text {
  color: red;
}

#done-button {
  display: inline-block;
  justify-content: center;
}

#datepicker {
  width: 350px;
  margin-left: 10px;
  display: inline-block;
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
