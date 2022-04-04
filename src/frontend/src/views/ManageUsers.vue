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
        :firstname="user.firstName"
        :lastname="user.lastName"
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
    let listOfUsers = ref([]);

    http.get("/users")
    .then((response) => {
      listOfUsers.value = response.data;
    })
    .catch((err) => {
      console.log(err);
    });

    const addUserInterfaceOn = () => {
      create.value = true;
    };
    const addUserInterfaceOff = () => {
      create.value = false;
    };
    const addUser = (email, firstname, lastname, role) => {
      const newUser = {
        email: email,
        firstName: firstname,
        lastName: lastname,
        role: role
      }

      // add user to database
      http.post("/register", newUser)
      .then((response) => {
        console.log(response.data)
      })
      .catch((err) => {
        console.log(err);
      })

      // push to list
      listOfUsers.value.push({
        email: email,
        firstname: firstname,
        lastname: lastname,
        role: role,
      });

      // close window
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