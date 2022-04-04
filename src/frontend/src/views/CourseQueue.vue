<template>
  <div>
    <!--    <h1>{{queue[0].course.title}}</h1>-->

    <div id="topbar">
      <h2 id="queue-header">Queue: {{ code }} {{ title }}</h2>
      <div id="button-position">
        <Button :title="'Enter queue'" @click="goToQueue" />
      </div>
    </div>

    <div id="list">
      <StudentCard
        :guide="true"
        :firstname="'Student'"
        :studentAssistant="'Student assistant'"
        :type="'Type'"
        :time="'Time'"
        :task="'Task'"
        :location="'Location'"
      />
      <div v-for="queueItem in queue" :key="queueItem.id + queueItem.assistant + timeNow">
        <StudentCard
        :timeNow="timeNow"
        :time="queueItem.time"
          :help="queueItem.help"
          :location="queueItem.room.title"
          :isStudAss="isStudAss"
          :guide="false"
          :id="queueItem.id"
          :task="queueItem.task.number"
          :firstname="queueItem.user.firstName"
          :lastname="queueItem.user.lastName"
          :type="queueItem.help ? 'Help' : 'Approval'"
          :studentAssistant="
            queueItem.assistant
              ? queueItem.assistant.firstName + ' ' + queueItem.assistant.lastName
              : null
          "
        />
      </div>
    </div>
  </div>
</template>

<script>
import StudentCard from "../components/StudentCard.vue";
import Button from "../components/Button.vue";
import { useRoute } from "vue-router";
import router from "../router";
import { computed, onMounted, onUnmounted } from "@vue/runtime-core";
import { onBeforeMount } from "vue";
import http from "@/service/http-common";
import { ref } from "@vue/reactivity";
import { useStore } from "vuex";

export default {
  components: {
    StudentCard,
    Button,
  },
  setup() {
    const route = useRoute();
    const store = useStore();

    const queue = ref([]);
    let isStudAss = ref(false);
    const user = ref();
    const code = ref();
    const title = ref();
    const timeNow = ref(Date.now());

    const timeNowFunc = () => {
      timeNow.value = Date.now();

      http
        .get("/queue/" + route.params.id)
        .then((response) => {
          queue.value = response.data;
          code.value = response.data[0].course.code;
          title.value = response.data[0].course.title;
        })
        .catch((err) => console.log(err));
    }

    let intervalID = setInterval(timeNowFunc, 1000);

    onUnmounted(() => {
      clearInterval(intervalID);
      console.log("clearing interval");
    });

    onBeforeMount(() => {
      http
        .get("/queue/" + route.params.id)
        .then((response) => {
          queue.value = response.data;
          code.value = response.data[0].course.code;
          title.value = response.data[0].course.title;
        })
        .catch((err) => console.log(err));

      if (
        store.state.role == "ASSISTANT" ||
        store.state.role == "TEACHER" ||
        store.state.role == "ADMIN"
      ) {
        isStudAss.value = true;
      }
      // check if logged in student is student assistant for this course
      // first get user email
      /* let currentUser = "";
      http
        .get("/me")
        .then((response) => {
          currentUser = response.data;
        })
        .catch((err) => {
          console.log(err);
        });

      // then check if role of current user is assistant in this course
      http
        .get("/courses/" + route.params.id)
        .then((response) => {
          for (const user in response.data.users) {
            if (
              currentUser.email === response.data.users[user].user.email &&
              response.data.users[user].role === "ASSISTANT"
            ) {
              //console.log("passed");
              isStudAss.value = true;
            }
          }
        })
        .catch((err) => console.log(err));
        */
    });

    const goToQueue = () => {
      router.push({
        name: "enterQueue",
        params: { id: route.params.id },
      });
    };

    return { goToQueue, isStudAss, queue, code, title, timeNow };
  },
};
</script>

<style>
#list {
  clear: both;
}

#topbar {
  display: grid;
  grid-template-columns: 1fr 1fr;
  width: 100%;
  margin: 30px 0px;
}

#queue-header {
  justify-self: start;
  margin-left: 20px;
}
#button-position {
  justify-self: end;
  margin-right: 20px;
}
</style>
