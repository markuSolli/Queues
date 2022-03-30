<template>
  <div id="user-card-grid">
    <div v-if="currentlyEditing">
      <input id="input-field" v-model="email" type="text" placeholder="" />
    </div>
    <div v-else id="email">{{ email }}</div>

    <div v-if="currentlyEditing">
      <input id="input-field" v-model="firstname" type="text" placeholder="" />
    </div>
    <div v-else id="firstname">{{ firstname }}</div>

    <div v-if="currentlyEditing">
      <input id="input-field" v-model="lastname" type="text" placeholder="" />
    </div>
    <div v-else id="lastname">{{ lastname }}</div>

    <div v-if="currentlyEditing">
      <input id="input-field" v-model="role" type="text" placeholder="" />
    </div>
    <div v-else id="role">{{ role }}</div>

    <div id="edit" v-if="edit && !currentlyEditing">
      <Button :title="'Edit'" @click="editMode" />
    </div>
    <div id="currently-editing" v-if="currentlyEditing && !create">
      <Button :title="'Done'" @click="doneEdit" />
      <Button :title="'Cancel'" @click="cancelEdit" />
    </div>
    <div id="currently-editing" v-if="currentlyEditing && create">
      <Button
        :title="'Done'"
        @click="addUser(email, firstname, lastname, role)"
      />
      <Button :title="'Cancel'" @click="interfaceOff" />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";

export default {
  props: [
    "email",
    "firstname",
    "lastname",
    "role",
    "edit",
    "create",
    "interfaceOff",
    "addUser",
  ],
  components: { Button },
  setup(props) {
    let email = ref(props.email);
    let firstname = ref(props.firstname);
    let lastname = ref(props.lastname);
    let role = ref(props.role);

    let edit = ref(props.edit);
    let create = ref(props.create);
    let currentlyEditing = ref(props.create);

    let startEmail = email.value;
    let startFirstname = firstname.value;
    let startLastname = lastname.value;
    let startRole = role.value;

    // createUser functions
    const interfaceOff = props.interfaceOff;
    const addUser = props.addUser;

    const editMode = () => {
      currentlyEditing.value = true;
    };
    const cancelEdit = () => {
      email.value = startEmail;
      firstname.value = startFirstname;
      lastname.value = startLastname;
      role.value = startRole;
      currentlyEditing.value = false;
    };
    const doneEdit = () => {
      // update user
      let oldUser = {
        email: startEmail,
        firstname: startFirstname,
        lastname: startLastname,
        role: startRole,
      };
      let newUser = {
        email: email.value,
        firstname: firstname.value,
        lastname: lastname.value,
        role: role.value,
      };

      startEmail = email.value;
      startFirstname = firstname.value;
      startLastname = lastname.value;
      startRole = role.value;
      currentlyEditing.value = false;
    };

    return {
      email,
      firstname,
      lastname,
      role,
      edit,
      currentlyEditing,
      editMode,
      cancelEdit,
      doneEdit,
      create,
      addUser,
      interfaceOff,
    };
  },
};
</script>

<style>
#user-card-grid {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;

  border-radius: 10px;

  padding: 10px;
  margin-top: 3px;
  background: rgb(7, 3, 29);
}

#email {
  grid-column: 1/2;
  justify-self: start;
  align-self: center;
}
#firstname {
  grid-column: 2/3;
  justify-self: center;
  align-self: center;
}
#lastname {
  grid-column: 3/4;
  justify-self: center;
  align-self: center;
}
#role {
  grid-column: 4/5;
  justify-self: center;
  align-self: center;
}

#edit {
  grid-column: 5/6;
  justify-self: end;
  align-self: center;
}

#input-field {
  padding: 5px;
}

#currently-editing {
  display: flex;
  grid-column: 5/6;
  justify-self: end;
  align-self: center;
}
</style>
