<template>
  <div id="person-card">
    <div v-if="edit || create" id="element-1">
      Email <input id="input-field" v-model="email" type="text" />
    </div>
    <div v-else id="element-1">
      {{ email }}
    </div>
    <div v-if="edit || create" id="element-2">
      Firstname<input id="input-field" v-model="firstname" type="text" />
    </div>
    <div v-else id="element-2">
      {{ firstname }}
    </div>
    <div v-if="edit || create" id="element-3">
      Lastname<input id="input-field" v-model="lastname" type="text" />
    </div>
    <div v-else id="element-3">
      {{ lastname }}
    </div>
    <div v-if="create" id="element-4">
      <Button :title="'Done'" @click="doneCreatePerson" />
      <Button :title="'Cancel'" @click="cancelAddPerson" />
    </div>
    <div v-if="edit" id="element-4">
      <Button :title="'Edit'" @click="doneEditPerson" />
      <Button :title="'Cancel'" @click="cancelEditPerson" />
    </div>
    <div v-if="!create && !edit" id="element-4">
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
  props: ["ListToPostTo", "edit", "create", "email", "firstname", "lastname"],
  setup(props) {
    let listToPostTo = ref(props.ListToPostTo);
    let email = ref(props.email);
    let firstname = ref(props.firstname);
    let lastname = ref(props.lastname);
    let edit = ref(props.edit);
    let create = ref(props.create);
    let editEmail = ref(props.email);
    let editFirstname = ref(props.firstname);
    let editLastname = ref(props.lastname);

    // validate
    const validate = () => {
      for (const person in listToPostTo.value) {
        if (
          listToPostTo.value[person].email == email.value ||
          email.value == "" ||
          firstname.value == "" ||
          lastname.value == ""
        )
          return false;
      }
      return true;
    };

    // add
    const doneCreatePerson = () => {
      console.log(email.value + firstname.value + lastname.value);

      // do some validaiton check here
      if (validate()) {
        listToPostTo.value.push({
          email: email.value,
          firstname: firstname.value,
          lastname: lastname.value,
        });
      }
    };
    const cancelAddPerson = () => {
      listToPostTo.value.shift();
    };

    // edit
    const editPerson = () => {
      edit.value = true;
    };
    const cancelEditPerson = () => {
      email.value = editEmail.value;
      firstname.value = editFirstname.value;
      lastname.value = editLastname.value;
      edit.value = false;
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
        edit.value = false;
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
      cancelAddPerson,
      email,
      firstname,
      lastname,
      edit,
      create,
    };
  },
};
</script>

<style>
#person-card {
  border: 1px solid black;
  border-radius: 20px;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  padding: 5px 20px;
  margin: 5px 0px 0px 0px;
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
}

#input-field {
  height: 20px;
  border-radius: 10px;
  padding: 5px;
}
</style>
