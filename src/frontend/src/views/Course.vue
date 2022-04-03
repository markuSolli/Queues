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
          Select season:
          <select v-model="season" id="season-dropdown">
            <option>Spring</option>
            <option>Autumn</option>
          </select>
          <input id="year" placeholder="Year" v-model="year" />
        </div>
      </div>
    </div>

    <div class="row">
      <TaskList :title="'Tasks'" :list="tasks" />
    </div>

    <div class="row">
      <PersonList
        :title="'Teachers'"
        :list="listOfTeachers"
        :idName="'teachersID'"
      />
    </div>

    <div class="row">
      <PersonList
        :title="'Student assistants'"
        :list="listOfStudAss"
        :idName="'assistantsID'"
      />
    </div>

    <div class="row">
      <PersonList
        :title="'Students'"
        :list="listOfStudents"
        :idName="'studentsID'"
      />
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
    const season = ref("Spring");
    const year = ref(new Date().getFullYear());
    let listOfTeachers = ref([]);
    let listOfStudents = ref([]);
    let listOfStudAss = ref([]);
    let tasks = ref({
      taskgroups: [],
    });

    // EDIT
    onMounted(() => {
      // id is sent, we know this is for edit
      if (route.params.id) {
        console.log("Params!" + route.params.id);
        http.get("/courses/" + route.params.id).then((response) => {
          const course = response.data;
          title.value = course.title;
          code.value = course.code;
          season.value = course.season;
          year.value = course.year;
          for (const user in course.users) {
            if (course.users[user].role == "TEACHER")
              listOfTeachers.value.push(course.users[user].user);
            if (course.users[user].role == "ASSISTANT")
              listOfStudAss.value.push(course.users[user].user);
            if (course.users[user].role == "STUDENT")
              listOfStudents.value.push(course.users[user].user);
          }
          tasks.value.taskgroups = course.taskGroups;
          // sort groups
          tasks.value.taskgroups.sort(function (a, b) {
            return a.number - b.number;
          });
          // sort tasks
          for (let groupNumber in tasks.value.taskgroups) {
            tasks.value.taskgroups[groupNumber].tasks.sort(function (a, b) {
              return a.number - b.number;
            });
          }
        });
        console.log("yo");
      } else {
        console.log("No params");

        // add current user to top of teachers list
        http
          .get("/me")
          .then((response) => {
            if (
              response.data.role == "ADMIN" ||
              response.data.role == "TEACHER"
            ) {
              listOfTeachers.value.push({
                email: response.data.email,
                firstName: response.data.firstName,
                lastName: response.data.lastName,
              });
            }
          })
          .catch((err) => {
            console.log(err);
          });

        // set function for DONE button when edit
        createCourse = () => {
          // validate title, code and date
          if (title.value == "" || code.value == "") {
            status.value = "Fields cant be empty";
          } else {
            http
              .post("/courses", {
                code: code.value,
                title: title.value,
                season: getSeasonRightFormat(season.value),
                year: year.value,
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

    // set function for DONE button when no parameters
    let createCourse = () => {
      // validate title, code and date
      if (title.value == "" || code.value == "") {
        status.value = "Fields cant be empty";
        return;
      }
      if (route.params.id) {
        http
          .put("/courses/" + route.params.id, {
            code: code.value,
            title: title.value,
            season: getSeasonRightFormat(season.value),
            year: year.value,
            taskGroups: tasks.value.taskgroups,
            students: listOfStudents.value,
            assistants: listOfStudAss.value,
            teachers: listOfTeachers.value,
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
            season: getSeasonRightFormat(season.value),
            year: year.value,
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

    const getSeasonRightFormat = (season) => {
      if (season === "Spring") return "SPRING";
      if (season === "Autumn") return "AUTUMN";
    };

    return {
      status,
      title,
      code,
      listOfTeachers,
      listOfStudents,
      listOfStudAss,
      tasks,
      season,
      year,
      createCourse,
    };
  },
};
</script>

<style>
#season-dropdown {
  padding: 5px;
  border-radius: 20px;
  width: 150px;
}

#year {
  border-radius: 20px;
  padding: 5px;
  width: 50px;
}

#done-text {
  color: red;
}

#done-button {
  display: inline-block;
  justify-content: center;
}

#datepicker {
  display: flex;
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
