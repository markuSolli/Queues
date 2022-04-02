<template>
  <div>
    <h1>Manage rooms</h1>
    <div class="add-row">
      <div class="add-row-left">
        <h2>Campuses{{ Campus }}</h2>
      </div>
      <div class="add-row-right">
        <div id="location-edit">
          <input
            id="location-edit-input"
            type="text"
            placeholder="Campus name"
            v-model="campusName"
          />
          <Button
            :title="'Add'"
            @click="
              addLocation({ name: campusName, buildings: [] }, locations.campus)
            "
          />
        </div>
      </div>
    </div>

    <div v-for="(campus, index) in locations.campus" :key="campus.name">
      <div id="campus">
        <div id="campus-header" v-if="campus.edit">
          <input
            type="text"
            id="location-edit-input"
            :placeholder="[[campus.name]]"
            v-model="name"
          />
          <Button :title="'Edit name'" @click="doneEditName(name, campus)" />
        </div>
        <div v-else id="campus-header">
          <div id="spacer-1"></div>
          <h2>{{ campus.name }}</h2>
          <Button :title="'Edit'" @click="editName(campus)" />
        </div>
        <div id="campus-buttons">
          <input
            type="text"
            id="location-edit-input"
            v-model="buildingName"
            placeholder="Building name"
          />
          <Button
            :title="'Add building'"
            @click="
              addLocation({ name: buildingName, rooms: [] }, campus.buildings)
            "
          />
          <Button
            :title="'Remove campus'"
            @click="removeLocation(locations.campus, index)"
          />
        </div>
      </div>
      <div
        id="building"
        v-for="building in campus.buildings"
        :key="building.name"
      >
        <div v-if="building.edit" id="building-header">
          <input
            type="text"
            id="location-edit-input"
            :placeholder="[[building.name]]"
            v-model="name"
          />
          <Button :title="'Edit name'" @click="doneEditName(name, building)" />
        </div>
        <div v-else id="building-header">
          <div id="spacer-2"></div>
          <h3>{{ building.name }}</h3>

          <Button :title="'Edit'" @click="editName(building)" />
        </div>

        <div id="building-buttons">
          <input
            type="text"
            id="location-edit-input"
            v-model="roomName"
            placeholder="Room name"
          />
          <Button
            :title="'Add room'"
            @click="addLocation({ name: roomName }, building.rooms)"
          />
          <Button
            :title="'Remove building'"
            @click="removeLocation(campus.buildings, index)"
          />
        </div>
        <div id="rooms">
          <div
            id="room"
            v-for="(room, index) in building.rooms"
            :key="room.name"
          >
            <div v-if="room.edit" id="room-left">
              <input
                type="text"
                id="location-edit-input"
                :placeholder="[[room.name]]"
                v-model="name"
              />
              <Button :title="'Edit name'" @click="doneEditName(name, room)" />
            </div>
            <div v-else id="room-left">
              <div id="spacer-3"></div>
              <h4>{{ room.name }}</h4>
              <Button :title="'Edit'" @click="editName(room)" />
            </div>

            <div id="room-right">
              <Button
                :title="'Remove room'"
                @click="removeLocation(building.rooms, index)"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";

export default {
  components: { Button },
  setup() {
    let locations = ref({
      campus: [
        {
          name: "campus 1",
          buildings: [
            {
              name: "building 1",
              rooms: [
                {
                  name: "room 1",
                },
              ],
            },
          ],
        },
      ],
    });

    const addLocation = (location, locations) => {
      locations.push(location);
    };

    const removeLocation = (location, index) => {
      location.splice(index, 1);
    };

    const editName = (location) => {
      location.edit = true;
    };
    const doneEditName = (name, location, input) => {
      location.name = name;
      location.edit = false;
      input = "";
    };

    return {
      locations,
      addLocation,
      removeLocation,
      editName,
      doneEditName,
    };
  },
};
</script>

<style>
#spacer-1,
#spacer-2,
#spacer-3 {
  width: 32px;
  height: 5px;
  align-self: center;
  background: red;
  border-radius: 20px;
  margin-right: 10px;
}

#spacer-2 {
  width: 75px;
  background: green;
}

#spacer-3 {
  width: 150px;
  background: yellow;
}

#room {
  padding-top: 5px;
  display: grid;
  grid-template-columns: 1fr 1fr;
}

#rooms {
  grid-row: 2/ 3;
  grid-column: 1 / 3;
}

#room-left {
  justify-self: start;
  grid-column: 1/2;
  display: flex;
}
#room-right {
  justify-self: end;
  grid-column: 2/3;
}

#location-edit {
  display: flex;
}

#location-edit-input {
  padding: 10px;
  border-radius: 20px;
}

#building {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 75px 1fr;
  padding: 30px 20px;
  margin-top: 2px;
  background: rgb(7, 3, 29);
}

#campus-header {
  grid-column: 1/2;
  grid-row: 1/2;
  justify-self: start;
  align-self: center;
  display: flex;
}

#building-header {
  grid-column: 1/2;
  grid-row: 1/2;
  justify-self: start;
  align-self: center;
  display: flex;
}

#building-buttons {
  display: flex;
  grid-column: 2/3;
  grid-row: 1/2;
  justify-self: end;
  align-self: center;
}

#campus-buttons {
  display: flex;
  grid-column: 2/3;
  grid-row: 1/2;
  justify-self: end;
  align-self: center;
}

#campus {
  display: grid;
  grid-template-columns: 1fr 1fr;

  margin: 20px 0px 0px 0px;
  padding: 10px 20px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  background: rgb(7, 3, 29);
}

.add-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin: 50px 0px;
  border-bottom: 1px white solid;
  padding-bottom: 10px;
}

.add-row-left {
  justify-self: start;
  grid-column: 1 /2;
}
.add-row-right {
  display: flex;
  justify-self: end;
  grid-column: 2 / 3;
}
</style>
