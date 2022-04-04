<template>
  <div>
    <h1>{{ code }}: {{ title }}</h1>
    <div id="list">
      <div id="headline"><h2>Teachers</h2></div>
      <div Id="user-list">
        <div id="element-1">Email</div>
        <div id="element-2">Firstname</div>
        <div id="element-3">Lastname</div>
      </div>
      <div id="user-list" v-for="teacher in teachers" :key="teacher.id">
        <div id="element-1">{{ teacher.email }}</div>
        <div id="element-2">{{ teacher.firstName }}</div>
        <div id="element-3">{{ teacher.lastName }}</div>
      </div>
    </div>
    <div id="list">
      <div id="headline"><h2>Student assistants</h2></div>
      <div Id="user-list">
        <div id="element-1">Email</div>
        <div id="element-2">Firstname</div>
        <div id="element-3">Lastname</div>
      </div>
      <div id="user-list" v-for="assistant in assistants" :key="assistant.id">
        <div id="element-1">{{ assistant.email }}</div>
        <div id="element-2">{{ assistant.firstName }}</div>
        <div id="element-3">{{ assistant.lastName }}</div>
      </div>
    </div>
    <div id="list">
      <div id="headline"><h2>Students</h2></div>
      <div Id="student-list">
        <div id="element-1">Email</div>
        <div id="element-2">Firstname</div>
        <div id="element-3">Lastname</div>
        <div id="element-4">Passed</div>
      </div>
      <div
        id="student-list"
        v-for="student in students"
        :key="student.id"
        @click="clickStudent(student)"
      >
        <div id="element-1">{{ student.email }}</div>
        <div id="element-2">{{ student.firstName }}</div>
        <div id="element-3">{{ student.lastName }}</div>
        <div id="element-4">
          <div v-if="coursePassed(taskGroups, student)">
            <i class="fa-solid fa-check"></i>
          </div>
          <div v-else><i class="fa-solid fa-xmark"></i></div>
        </div>
        <div id="element-5" v-if="student.active">
          <h2>Task status</h2>
          <div v-for="taskGroup in taskGroups" :key="taskGroup.id">
            <div
              id="task-status"
              v-for="task in taskGroup.tasks"
              :key="task.id"
            >
              <div id="task-number-left">{{ task.number }}</div>
              <div id="task-status-right">
                <div v-if="taskIsApproved(task, student)">
                  <i class="fa-solid fa-check"></i>
                </div>
                <div v-else><i class="fa-solid fa-xmark"></i></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import { onBeforeMount } from "@vue/runtime-core";
import http from "../service/http-common";
import { useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    let students = ref([]);
    let teachers = ref([]);
    let assistants = ref([]);
    let taskGroups = ref();
    let code = ref();
    let title = ref();

    onBeforeMount(() => {
      http.get("/courses/" + route.params.id).then((response) => {
        const course = response.data;

        // add users to lists
        for (const user in course.users) {
          if (course.users[user].role == "TEACHER")
            teachers.value.push(course.users[user].user);
          if (course.users[user].role == "STUDENT")
            students.value.push(course.users[user].user);
          if (course.users[user].role == "ASSISTANT")
            assistants.value.push(course.users[user].user);
        }

        // sort groups
        course.taskGroups.sort(function (a, b) {
          return a.number - b.number;
        });

        // sort tasks
        for (const taskGroup in course.taskGroups) {
          course.taskGroups[taskGroup].tasks.sort(function (a, b) {
            return a.number - b.number;
          });
        }

        code.value = course.code;
        title.value = course.title;
        taskGroups.value = course.taskGroups;
      });
    });

    const clickStudent = (student) => {
      student.active = !student.active;
    };

    const taskIsApproved = (task, student) => {
      return student.approved.find((approvedItem) => {
        return approvedItem.task.id === task.id;
      });
    };

    const coursePassed = (taskGroups, student) => {
      const totalRequired = taskGroups.length;
      let totalCompleted = 0;
      for (const taskGroup of taskGroups) {
        let completed = 0;
        for (const task of taskGroup.tasks) {
          if (taskIsApproved(task, student)) {
            completed++;
          }
        }
        console.log(completed);
        console.log(taskGroup.required);
        if (completed >= taskGroup.required) {
          totalCompleted++;
        }
      }
      console.log(totalCompleted);
      console.log(totalRequired);
      if (totalCompleted >= totalRequired) {
        return true;
      }
      return false;
    };

    return {
      taskGroups,
      students,
      teachers,
      assistants,
      code,
      title,
      clickStudent,
      taskIsApproved,
      coursePassed,
    };
  },
};
</script>

<style>
#task-number-left {
  grid-column: 1/2;
  justify-self: start;
}

#task-status-right {
  grid-column: 2/3;

  justify-self: end;
}

#task-status {
  display: grid;
  grid-template-columns: 1fr 1fr;
  border-bottom: 1px solid white;
  padding: 5px 10px;
}

#list {
  margin: 50px 0px;
}

#headline {
  display: flex;
}

#user-list {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  background: rgb(7, 3, 29);
  padding: 5px 20px;
  border-radius: 10px;
  margin-top: 2px;
}

#student-list {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-template-rows: 30px 1fr;
  background: rgb(7, 3, 29);
  padding: 5px 20px;
  border-radius: 10px;
  margin-top: 2px;
}

#student-list:hover {
  cursor: pointer;
  background: rgb(13, 6, 53);
}

#element-1 {
  grid-column: 1/2;
  grid-row: 1/2;

  justify-self: start;
}
#element-2 {
  grid-column: 2/3;
  grid-row: 1/2;

  justify-self: center;
}
#element-3 {
  grid-column: 3/4;
  grid-row: 1/2;

  justify-self: end;
}

#student-list,
#element-3 {
  grid-column: 3/4;
  grid-row: 1/2;

  justify-self: end;
}

#element-4 {
  grid-column: 4/5;
  grid-row: 1/2;

  justify-self: end;
}
#element-5 {
  grid-column: 1/5;
  grid-row: 2/3;
  margin: 20px;
}
</style>
