<template>
  <div >
    <div id="management-bar">
      <div id="title"><h2>Users</h2></div>
      <div id="buttons">
        <Button :title="'Add new user'" @click="addUserInterfaceOn" />
      </div>
    </div>
    <UserCard
      :email="'Email'"
      :firstname="'Firstname'"
      :lastname="'Lastname'"
      :role="'Role'"
      :edit="false"
    />
    <UserCard
      v-if="create"
      :create="true"
      :interfaceOff="addUserInterfaceOff"
      :addUser="addUser" 
    />
    <div v-for="user in listOfUsers" :key="user.email">
      <UserCard
        :email="user.email"
        :firstname="user.firstname"
        :lastname="user.lastname"
        :role="user.role"
        :edit="true"
      />
    </div>

  </div>
</template>

<script>
import { ref } from '@vue/reactivity';
import Button from "../components/Button.vue";
import UserCard from "../components/UserCard.vue";
import http from "../service/http-common";

export default {
  components: { Button, UserCard},
  setup(){
    let create = ref(false);
    let listOfUsers = ref([
      {
        email: "asdasdasd",
        firstname: "thor",
        lastname: "dfdfdf",
        role: 0,
      },
      { email: "123123", firstname: "thor1", lastname: "dfdfdf2", role: 1 },
      { email: "ghghgh", firstname: "thor2", lastname: "dfdfdf3", role: 1 },
      { email: "ghghgh2", firstname: "thor2", lastname: "dfdfdf3", role: 2 },
      { email: "ghghgh4", firstname: "thor2", lastname: "dfdfdf3", role: 3 },
    ]);

    const addUserInterfaceOn = () => {
      create.value = true;
    };
    const addUserInterfaceOff = () => {
      create.value = false;
    };
    const addUser = (email, firstname, lastname, role) => {
      // add user to database
      console.log(email + firstname + lastname + role);

      // add fake data to list
      listOfUsers.value.push({
        email: email,
        firstname: firstname,
        lastname: lastname,
        role: role,
      });

      // if succesfull close window
      addUserInterfaceOff();
    };

    return {
      listOfUsers,
      addUserInterfaceOn,
      addUserInterfaceOff,
      addUser,
      create,
    }
  }
}
</script>
#management-bar {
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin: 25px 0px;
  padding: 25px;
}

#title {
  justify-self: start;
  grid-column: 1 / 2;
}

#buttons {
  justify-self: end;
  grid-column: 2 / 3;
  display: flex;
}
<style>

</style>