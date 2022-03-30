<template>
  <div>
    <div class="add-row">
      <div class="add-row-left">
        <h2>{{ title }}</h2>
      </div>
      <div class="add-row-right">
        <Button :title="'Add'" @click="addPerson(list)" />
        <Button :title="'Add from CSV'" />
      </div>
    </div>
    <PersonCard
      :email="'Email'"
      :firstname="'Firstname'"
      :lastname="'Lastname'"
    />
    <div v-for="person in list" :key="person.email" class="person-list">
      <div v-if="person.create == null">
        <PersonCard
          :ListToPostTo="list"
          :email="person.email"
          :firstname="person.firstname"
          :lastname="person.lastname"
          :edit="true"
        />
      </div>
      <div v-else>
        <PersonCard
          :create="true"
          :ListToPostTo="list"
          :email="person.email"
          :firstname="person.firstname"
          :lastname="person.lastname"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import PersonCard from "../components/PersonCard.vue";
import Button from "../components/Button.vue";

export default {
  components: { PersonCard, Button },
  props: ["title", "list"],
  setup(props) {
    const title = ref(props.title);
    const list = ref(props.list);

    const addPerson = (list) => {
      // if a person is already being created, cancel add menu
      for (const person in list) {
        if (list[person].create != null) return;
      }
      list.unshift({
        email: "",
        firstname: "",
        lastname: "",
        create: true,
      });
    };

    return {
      title,
      list,
      addPerson,
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
