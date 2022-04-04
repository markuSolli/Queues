<template>
  <div id="card" :class="archived ? 'archived' : null" @click="clickCardFunc">
    <div class="queue-element-1">
      <h3>{{ code }} {{ title }}</h3>
    </div>

    <div class="queue-element-2"></div>
    <div class="queue-element-3"></div>
    <div class="queue-element-4" v-if="cardInQueue">
      <div id="prog-text"><h4>Progress:</h4></div>
      <div v-for="task in taskProgress" :key="task.id">
        <div :class="task.approved ? 'loadingBar-2' : 'loadingBar-1'">
          {{ task.number }}
        </div>
      </div>
    </div>

    <!-- ADMIN / TEACHER -->
    <div class="queue-element-4" v-if="edit && !cardInQueue && !archived">
      <Button :title="'Edit'" @click="editCourse" />
      <Button :title="'Archive'" @click="clickArchive" />
      <Button :title="'Delete'" @click="deleteCourse" />
      <div class="queue-element-4" v-if="active && !cardInQueue">
        <Button :title="'Stop queue'" @click="toggleQueue" />
      </div>
      <div class="queue-element-4" v-if="!active && !cardInQueue">
        <Button :title="'Start queue'" @click="toggleQueue" />
      </div>
      <div
        class="queue-element-4"
        v-if="assistant && !active && !cardInQueue && !archived"
      >
        <Button :title="'Start queue'" @click="toggleQueue" />
      </div>
    </div>
    <div
      class="queue-element-4"
      v-if="archived && !assistant && !student && !cardInQueue && archived"
    >
      <Button :title="'Restore course'" @click="clickArchive" />
    </div>

    <!-- ASSISTANT -->
    <div
      class="queue-element-4"
      v-if="assistant && !active && !cardInQueue && !archived"
    >
      <Button :title="'Start queue'" @click="toggleQueue" />
    </div>
    <div
      class="queue-element-4"
      v-if="assistant && active && !cardInQueue && !archived"
    >
      <Button :title="'Stop queue'" @click="toggleQueue" />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "./Button.vue";
import router from "../router";
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";
import http from "@/service/http-common";

export default {
  props: ["course", "cardInQueue", "clickCardFunc"],
  components: { Button },
  setup(props, { emit }) {
    const store = useStore();
    let course = Object.assign({}, props.course);

    let active = ref(course.active);
    let archived = ref(course.archived);
    let title = ref(course.title);
    let code = ref(course.code);
    let id = course.id;
    let cardInQueue = props.cardInQueue;
    let clickedButton = false;
    let taskGroupProgress = ref(course.taskGroupProgress);
    let taskProgress = computed(() => {
      let taskProgressArr = [];
      for (const taskGroup in taskGroupProgress.value) {
        for (const task in taskGroupProgress.value[taskGroup].taskProgress) {
          taskProgressArr.push(
            taskGroupProgress.value[taskGroup].taskProgress[task]
          );
        }
      }
      return taskProgressArr;
    });

    let assistant = computed(() => {
      if (store.state.role == "ASSISTANT") return true;
    });
    let edit = computed(() => {
      if (store.state.role == "ADMIN" || store.state.role == "TEACHER")
        return true;
    });
    let student = computed(() => {
      if (store.state.role == "STUDENT") return true;
    });

    const deleteCourse = () => {
      clickedButton = true;
      http.delete("/courses/" + id).then((response) => {
        emit("refresh");
      });
      
    };

    const editCourse = () => {
      clickedButton = true;

      router.push({
        name: "courseEdit",
        params: {
          id: id,
        },
      });
    };

    const clickArchive = () => {
      clickedButton = true;

      http
        .patch("/courses/" + id + "/archived", !archived.value)
        .then((response) => {
          emit("refresh");
        });
    };

    const clickCardFunc = () => {
      if (cardInQueue) {
        // CARD IS IN QUEUE
        if (active.value) {
          router.push({
            name: "courseQueue",
            params: {
              id: id,
            },
          });
        } else {
          router.push({
            name: "viewCourse",
            params: {
              id: id,
            },
          });
        }
      } else {
        // CARD IS IN MANAGEMENT PAGE
        if (!clickedButton) {
          router.push({
            name: "viewCourseFull",
            params: {
              id: id,
            },
          });
        }
      }
    };

    const toggleQueue = () => {
      clickedButton = true;

      http
        .patch("/courses/" + id + "/active", !active.value)
        .then((response) => {
          emit("refresh");
        });
    };

    return {
      edit,
      active,
      archived,
      title,
      assistant,
      student,
      cardInQueue,
      taskProgress,
      editCourse,
      deleteCourse,
      clickArchive,
      clickCardFunc,
      code,
      toggleQueue,
    };
  },
};
</script>

<style>
#card {
  height: 60px;
  background: rgb(7, 3, 29);
  /*background: linear-gradient(
    217deg,
    rgba(7, 3, 29, 1) 0%,
    rgba(0, 0, 0, 1) 100%
  );*/
  border-radius: 10px;
  text-align: left;
  padding: 25px;
  margin: 0px 0px 10px 0px;
  display: grid;
  grid-template-areas: "element-1 element-2" "element-3 element-4";
}

#card:hover {
  cursor: pointer;
  background: rgb(13, 6, 53);
}

#prog-text {
  display: inline-block;
  margin-right: 15px;
}

.circle-1,
.circle-2 {
  background: red;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: inline-block;
  margin: 2px 2px 2px 2px;
  font-size: 14px;
  text-align: center;
  padding: 3px;
}

.circle-1 {
  background: rgb(5, 158, 0);
}

.loadingBar-1,
.loadingBar-2 {
  background: red;
  width: 20px;
  height: 20px;
  display: inline-block;
  margin: 0px;
  font-size: 14px;
  text-align: center;
  padding: 3px;
  border: 1px rgb(37, 37, 37) solid;
}

.loadingBar-2 {
  background: rgb(5, 158, 0);
}

.queue-element-1 {
  grid-area: element-1;
}

.queue-element-2 {
  text-align: right;
  grid-area: element-2;
}

.queue-element-3 {
  justify-self: start;
  grid-area: element-3;
  font-size: 12px;
}

.queue-element-4 {
  grid-area: element-4;
  justify-self: end;
  align-self: end;
  display: flex;
}

.archived {
  opacity: 0.6;
}

@media only screen and (max-width: 600px) {
  #card {
    grid-template-areas: "element-1" "element-2" "element-3" "element-4";
  }
  .queue-element-1,
  .queue-element-2,
  .queue-element-3,
  .queue-element-4 {
    justify-self: start;
    align-self: start;
    display: flex;
  }
}
</style>
