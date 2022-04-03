<template>
  <div id="person-card">
    <div v-if="currentlyEditing || create" id="element-1">
      Email <input id="input-field" v-model="email" type="text" />
    </div>
    <div v-else id="element-1">
      {{ email }}
    </div>
    <div v-if="currentlyEditing || create" id="element-2">
      Firstname<input id="input-field" v-model="firstName" type="text" />
    </div>
    <div v-else id="element-2">
      {{ firstName }}
    </div>
    <div v-if="currentlyEditing || create" id="element-3">
      Lastname<input id="input-field" v-model="lastName" type="text" />
    </div>
    <div v-else id="element-3">
      {{ lastName }}
    </div>
    <div v-if="create" id="element-4">
      <Button :title="'Done'" @click="doneCreatePerson" />
      <Button :title="'Cancel'" @click="cancelFunc" />
    </div>
    <div v-if="currentlyEditing" id="element-4">
      <Button :title="'Edit'" @click="doneEditPerson" />
      <Button :title="'Cancel'" @click="cancelEditPerson" />
    </div>
    <div v-if="edit && !currentlyEditing" id="element-4">
      <Button :title="'Edit'" @click="editPerson" />
      <Button :title="'Delete'" @click="deletePerson" />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import Button from "../components/Button.vue";

export default {
  components: { Button },
  props: [
    "ListToPostTo",
    "edit",
    "create",
    "email",
    "firstName",
    "lastName",
    "cancelFunc",
  ],
  setup(props) {
    let listToPostTo = ref(props.ListToPostTo);
    let email = ref(props.email);
    let firstName = ref(props.firstName);
    let lastName = ref(props.lastName);
    let edit = ref(props.edit);
    let currentlyEditing = ref(false);
    let create = ref(props.create);
    let cancelFunc = ref(props.cancelFunc);
    let editEmail = ref(props.email);
    let editFirstName = ref(props.firstName);
    let editLastName = ref(props.lastName);

    // validate
    const validate = () => {
      for (const person in listToPostTo.value) {
        if (
          listToPostTo.value[person].email == email.value ||
          email.value == "" ||
          firstName.value == "" ||
          lastName.value == ""
        )
          return false;
      }
      return true;
    };

    // add
    const doneCreatePerson = () => {
      console.log(email.value + firstName.value + lastName.value);

      // do some validaiton check here
      if (validate()) {
        listToPostTo.value.push({
          email: email.value,
          firstName: firstName.value,
          lastName: lastName.value,
        });
      }
    };

    // edit
    const editPerson = () => {
      currentlyEditing.value = true;
    };
    const cancelEditPerson = () => {
      email.value = editEmail.value;
      firstName.value = editFirstName.value;
      lastName.value = editLastName.value;
      currentlyEditing.value = false;
    };
    const doneEditPerson = () => {
      // validation, check if unique email and not blank fields
      if (validate()) {
        // delete previous user with that email if new one was created successfully
        for (const person in listToPostTo.value) {
          if (listToPostTo.value[person].email == editEmail.value) {
            listToPostTo.value.splice(person, 1);
          }
        }
        // create new user based on new input
        doneCreatePerson();
        currentlyEditing.value = false;
      }
    };

    // delete
    const deletePerson = () => {
      for (const person in listToPostTo.value) {
        if (listToPostTo.value[person].email == email.value) {
          listToPostTo.value.splice(person, 1);
          return;
        }
      }
    };

    return {
      doneCreatePerson,
      editPerson,
      cancelEditPerson,
      doneEditPerson,
      deletePerson,
      email,
      firstName,
      lastName,
      edit,
      currentlyEditing,
      create,
      cancelFunc,
    };
  },
};
</script>

<style>
#person-card {
  border-radius: 10px;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  padding: 5px 20px;
  margin: 3px 0px 0px 0px;
  background: rgb(7, 3, 29);
}

#element-1 {
  justify-self: start;
  align-self: center;
}
#element-2 {
  justify-self: center;
  align-self: center;
}
#element-3 {
  justify-self: center;
  align-self: center;
}
#element-4 {
  display: flex;
  justify-self: end;
  align-self: center;
}

#input-field {
  height: 20px;
  border-radius: 10px;
  padding: 5px;
}
</style>
