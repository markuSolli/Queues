<template>
  <div>
    <div class="add-row">
      <div class="add-row-left">
        <h2>{{ title }}</h2>
      </div>
      <div class="add-row-right">
        <Button :title="'Add'" @click="addPerson" />
        <input
          v-bind:id="idName"
          type="file"
          hidden
          accept=".csv"
          @change="filePicked"
        />
        <Button :title="'Add from CSV'" @click="addFromCSV" />
      </div>
    </div>
    <PersonCard
      :email="'Email'"
      :firstName="'Firstname'"
      :lastName="'Lastname'"
    />
    <PersonCard
      v-if="create"
      :cancelFunc="cancelAddPerson"
      :ListToPostTo="list"
      :email="''"
      :firstName="''"
      :lastName="''"
      :create="true"
    />
    <div v-for="person in list" :key="person.email" class="person-list">
      <div v-if="person.create == null">
        <PersonCard
          :ListToPostTo="list"
          :email="person.email"
          :firstName="person.firstName"
          :lastName="person.lastName"
          :edit="true"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import PersonCard from "../components/PersonCard.vue";
import Button from "../components/Button.vue";
import { onBeforeMount, onMounted } from "@vue/runtime-core";

export default {
  components: { PersonCard, Button },
  props: ["title", "list", "idName"],
  setup(props) {
    let title = ref(props.title);
    let list = ref(props.list);
    let idName = ref(props.idName);
    let create = ref(false);
    let csvFile;

    const addPerson = () => {
      create.value = true;
    };
    const cancelAddPerson = () => {
      create.value = false;
    };

    const addFromCSV = () => {
      document.getElementById(idName.value).click();
      console.log("add " + idName.value);
    };

    const filePicked = () => {
      console.log("picked " + idName.value);
      csvFile = document.getElementById(idName.value);
      const input = csvFile.files[0];
      const reader = new FileReader();

      reader.onload = function (e) {
        const text = e.target.result;
        const data = csvToArray(text);

        for (const person in data) {
          // push to list only of all fields have been put in
          if (
            data[person].firstName &&
            data[person].lastName &&
            data[person].email
          )
            list.value.push(data[person]);
        }
      };

      reader.readAsText(input);
    };

    function csvToArray(str, delimiter = ",") {
      const headers = ["lastName", "firstName", "email"];
      const rows = str.slice(str.indexOf("\n") + 1).split("\n");

      const arr = rows.map(function (row) {
        const values = row.split(delimiter);
        const el = headers.reduce(function (object, header, index) {
          object[header] = values[index];
          return object;
        }, {});
        return el;
      });

      return arr;
    }

    return {
      title,
      list,
      idName,
      addPerson,
      addFromCSV,
      filePicked,
      create,
      cancelAddPerson,
    };
  },
};
</script>

<style>
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
