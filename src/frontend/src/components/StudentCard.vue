<template>
  <div id="full">
    <div id="studentcard-container" v-bind:class="{ guide: guide }">
      <div class="element-1">{{ firstname }} {{ lastname }}</div>
      <div class="element-2">{{ type }}</div>
      <div  v-if="timeTitle == 'Time'" class="element-3">{{ timeTitle }}</div>
      <div  v-else class="element-3">{{ time }}</div>
      <div class="element-4">{{ location }}</div>
      <div class="element-5">
        {{ studass }}
      </div>
      <div class="element-6">{{ task }}</div>

      <div v-if="guide" class="element-7"></div>
      <div v-else>
        <div class="element-7" v-if="!guide && isStudAss">
          <div v-if="help">
            <Button v-if="beingApproved" :title="'Done'" @click="doneWithoutTaskApproved" />
            <Button
                v-if="!beingApproved"
                :title="'Assist'"
                @click="superviseStudent"
              />
          </div>
          <div v-else>
            <div v-if="!beingApproved">
              <Button
                v-if="!beingApproved"
                :title="'Assist'"
                @click="superviseStudent"
              />
            </div>
            <div v-else>
              <Button :title="'Approve'" @click="approveStudent" />
              <Button :title="'Cancel'" @click="doneWithoutTaskApproved" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="!guide"
      class="being-approved"
      v-bind:class="{ beingapprovedcolor: beingApproved }"
    ></div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import { computed } from "@vue/runtime-core";
import Button from "../components/Button.vue";
import http from "@/service/http-common";

export default {
  components: { Button },
  props: [
    "id",
    "email",
    "firstname",
    "lastname",
    "time",
    "type",
    "studentAssistant",
    "guide",
    "isStudAss",
    "task",
    "location",
    "help",
    "timeNow",
    "timeTitle"
  ],
  setup(props) {
    const id = ref(props.id);
    const email = ref(props.email);
    const firstname = ref(props.firstname);
    const lastname = ref(props.lastname);
    const time = ref(msToTime(props.timeNow - props.time));
    const timeTitle = ref(props.time);
    const type = ref(props.type);
    const task = ref(props.task);
    const location = ref(props.location);
    const studass = ref(props.studentAssistant);
    const guide = ref(props.guide);
    const help = ref(props.help);
    const isStudAss = ref(props.isStudAss);
    const beingApproved = computed(() => {
      if (studass.value) {
        return true;
      } else {
        return false;
      }
    });

    function msToTime(duration) {
  var milliseconds = Math.floor((duration % 1000) / 100),
    seconds = Math.floor((duration / 1000) % 60),
    minutes = Math.floor((duration / (1000 * 60)) % 60),
    hours = Math.floor((duration / (1000 * 60 * 60)) % 24);

  hours = (hours < 10) ? "0" + hours : hours;
  minutes = (minutes < 10) ? "0" + minutes : minutes;
  seconds = (seconds < 10) ? "0" + seconds : seconds;

  return hours + ":" + minutes + ":" + seconds;
}

    const superviseStudent = () => {
      http.put("/queue/" + id.value + "/assist").then((response) => {
        console.log(response.data);
      });
      // put name of studass into this queue in database
    };

    const approveStudent = () => {
      http.post("/queue/" + id.value + "/approve").then((response) => {
        console.log(response.data);
      });
    };

    const doneWithoutTaskApproved = () => {
      http.delete("/queue/" + id.value).then((response) => {
        console.log(response.data);
      });
    };



    return {
      email,
      firstname,
      lastname,
      time,
      type,
      studass,
      guide,
      beingApproved,
      isStudAss,
      superviseStudent,
      approveStudent,
      task,
      location,
      help,
      doneWithoutTaskApproved,
      timeTitle
    };
  },
};
</script>

<style>
#full {
  display: flex;
}

#studentcard-container {
  background: rgb(7, 3, 29);
  /*background: linear-gradient(
    217deg,
    rgba(7, 3, 29, 1) 0%,
    rgba(0, 0, 0, 1) 100%
  );*/
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  text-align: left;
  padding: 10px;
  margin: 0px 0px 10px 0px;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  flex: 1 1 auto;
  font-size: 16px;
}

.guide {
  background: transparent;
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
}

.being-approved {
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  background-color: red;
  max-width: 10px;
  min-width: 10px;
  margin: 0px 0px 10px 0px;
}

.beingapprovedcolor {
  background-color: green;
}

#studentcard-container:hover {
  cursor: pointer;
}

#prog-text {
  display: inline-block;
  margin-right: 15px;
}

.element-1 {
  grid-column: 1 / 2;
  justify-self: center;
  align-self: center;
}

.element-2 {
  grid-column: 2 / 3;
  justify-self: center;
  align-self: center;
}

.element-3 {
  grid-column: 3 / 4;
  justify-self: center;
  font-size: 16px;
  align-self: center;
}

.element-4 {
  grid-column: 4 / 5;
  justify-self: center;
  align-self: center;
}

.element-5 {
  grid-column: 5 / 6;
  justify-self: center;
  align-self: center;
}
.element-6 {
  grid-column: 6 / 7;
  justify-self: center;
  align-self: center;
}
.element-7 {
  grid-column: 7 / 8;
  justify-self: center;
  align-self: center;
}

@media only screen and (max-width: 600px) {
  #studentcard-container {
    font-size: 10px;
  }
}
</style>
