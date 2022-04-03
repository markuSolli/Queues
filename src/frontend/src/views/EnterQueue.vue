<template>
  <div>
    <h1>Entering queue...</h1>
    <div id="spacer">
      <h2>Select task:</h2>
      <div id="list-of-tasks">
        <div
          id="taskgroup-grid"
          v-for="taskGroup in taskGroups"
          :key="taskGroup.id"
        >
          <div id="group-header">Group {{ taskGroup.number }}</div>
          <div id="taskgroup">
            <div v-for="task in taskGroup.tasks" :key="task.id">
              <div @click="taskClick(task.number, task.id)" class="task-1">
                {{ task.number }}
              </div>
            </div>
          </div>
          <div id="group-status">Group status: / {{ taskGroup.required }}</div>
        </div>
      </div>
      <div v-if="selectedTask != 0">
        <h3>Selected task: {{ selectedTask }}</h3>
      </div>
    </div>
    <div id="spacer">
      <h2>Select room:</h2>
      <div id="map" ref="mapRef"></div>
      <div>
        Selected room: {{ selectedRoom.buildingName }}
        {{ selectedRoom.roomName }}
      </div>
    </div>
    <div id="spacer">
      <h2>Select type:</h2>
      <div id="help-approval">
        <Button
          v-bind:class="{ buttonSelected: help }"
          :title="'Help'"
          @click="clickHelp"
        />
        <Button
          v-bind:class="{ buttonSelected: approval }"
          :title="'Approval'"
          @click="clickApproval"
        />
      </div>
    </div>
    <div id="button-pos">
      <Button :title="'Enter queue'" @click="enterQueue" />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";
import { useRoute } from "vue-router";
import { onMounted } from "@vue/runtime-core";
import http from "@/service/http-common";
import router from "../router";

export default {
  components: { Button },
  setup() {
    const route = useRoute();
    let selectedTask = ref(0);
    let selectedTaskId = ref();
    let help = ref(false);
    let approval = ref(true);
    let taskGroups = ref();
    let mapRef = ref();
    let selectedRoom = ref({});

    onMounted(() => {
      //--------- MAZEMAP ----------//
      var map = new Mazemap.Map({
        // container id specified in the HTML
        container: mapRef.value,
        campuses: "default",
        center: { lng: 10.404674604795176, lat: 63.41558686615829 },
        zoom: 19,
        zLevel: 4,
        scrollZoom: true,
        doubleClickZoom: false,
        touchZoomRotate: false,
      });

      map.on("load", function () {
        // Initialize a Highlighter for POIs
        // Storing the object on the map just makes it easy to access for other things
        map.highlighter = new Mazemap.Highlighter(map, {
          showOutline: true,
          showFill: true,
          outlineColor: Mazemap.Util.Colors.MazeColors.MazeBlue,
          fillColor: Mazemap.Util.Colors.MazeColors.MazeBlue,
        });
        map.on("click", onMapClick);
      });

      // define a global
      var mazeMarker;

      function onMapClick(e) {
        clearPoiMarker(selectedRoom.value);
        var lngLat = e.lngLat;
        var zLevel = map.zLevel;

        // Fetching via Data API
        // NB: Adding optional campusId parameter, makes lookup much faster, but can be omitted
        Mazemap.Data.getPoiAt(lngLat, zLevel)
          .then((poi) => {
            // Place a marker on the map, or highlight the room
            placePoiMarker(poi);
            console.log(poi.properties.buildingName);
            selectedRoom.value = {
              buildingName: poi.properties.buildingName,
              roomName: poi.properties.title,
            };
          })
          .catch(function () {
            return false;
          });
      }

      function clearPoiMarker(poi) {
        if (mazeMarker) {
          mazeMarker.remove();
        }
        map.highlighter.clear();
      }

      function placePoiMarker(poi) {
        // Get a center point for the POI, because the data can return a polygon instead of just a point sometimes
        var lngLat = Mazemap.Util.getPoiLngLat(poi);

        mazeMarker = new Mazemap.MazeMarker({
          color: "#ff00cc",
          innerCircle: true,
          innerCircleColor: "#FFF",
          size: 34,
          innerCircleScale: 0.5,
          zLevel: poi.properties.zLevel,
        })
          .setLngLat(lngLat)
          .addTo(map);

        // If we have a polygon, use the default 'highlight' function to draw a marked outline around the POI.
        if (poi.geometry.type === "Polygon") {
          map.highlighter.highlight(poi);
        }
        map.flyTo({ center: lngLat, zoom: 19, speed: 0.5 });
      }
      //---------------------------------//

      http.get("/courses/" + route.params.id).then((response) => {
        const course = response.data;
        console.log(course);

        // sort groups
        course.taskGroups.sort(function (a, b) {
          return a.number - b.number;
        });
        // sort tasks
        for (let groupNumber in course.taskGroups) {
          course.taskGroups[groupNumber].tasks.sort(function (a, b) {
            return a.number - b.number;
          });
        }

        taskGroups.value = course.taskGroups;
      });
    });

    const taskClick = (taskNumber, taskId) => {
      selectedTask.value = taskNumber;
      selectedTaskId.value = taskId;
      console.log(selectedTaskId.value);
    };

    const clickHelp = () => {
      help.value = true;
      approval.value = false;
    };
    const clickApproval = () => {
      help.value = false;
      approval.value = true;
    };
    const enterQueue = () => {
      http
        .post("/queue/" + route.params.id + "/" + selectedTaskId.value, {
          help: help.value,
        })
        .then((response) => {
          console.log(response.data);

          // redirect to queue in successfull
          router.push({
            name: "courseQueue",
            params: {
              id: route.params.id,
            },
          });
        });
    };

    return {
      taskClick,
      selectedTask,
      clickHelp,
      clickApproval,
      enterQueue,
      help,
      approval,
      taskGroups,
      mapRef,
      selectedRoom,
    };
  },
};
</script>

<style>
#group-header {
  grid-row: 1/2;
  font-size: 25px;
}

#group-status {
  grid-row: 3 /4;
  justify-self: center;
  align-self: center;
  font-size: 20px;
}

#taskgroup-grid {
  display: grid;
  font-weight: 200;
  color: white;
  padding: 0px 10px;
  border-radius: 20px;
  grid-template-rows: 1fr 1fr 1fr;
}

#taskgroup {
  grid-row: 2 /3;
  display: flex;
}

#help-approval {
  display: flex;
  margin: 20px 0px;
  justify-content: center;
}

.buttonSelected {
  background: rgb(212, 145, 0);
  color: black;
}

#select-room {
  margin: 25px 0px;
}

#button-pos {
  display: flex;
  justify-content: center;
}

#spacer {
  margin: 75px 0px;
}

#list-of-tasks {
  color: white;
  flex-flow: row wrap;
  justify-content: space-around;
  display: flex;
  margin: 25px 0px;
  padding: 20px;
  border-radius: 20px;
}

.task-1,
.task-2 {
  background: green;
  border-radius: 10px;

  font-size: 30px;
  padding: 5px 40px;
  margin: 0px 5px;
}

.task-2 {
  background: red;
}

.task-1:hover,
.task-2:hover {
  background: rgb(212, 145, 0);
  cursor: pointer;
}

#map {
  height: 600px;
}
</style>
