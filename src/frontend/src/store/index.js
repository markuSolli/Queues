import { createStore } from "vuex";

export default createStore({
  state: {
    loggedIn: false,
    accessToken: null,
    role: 3,
    email: "",
    firstName: "",
    lastName: "",
  },
  getters: {},
  mutations: {
    updateLoggedin(state, val) {
      state.loggedIn = val;
    },
    setUser(state, val) {
      state.email = val.email;
      state.firstName = val.firstName;
      state.lastName = val.lastName;
      state.role = val.role;
    },
    updateEmail(state, val) {
      state.email = val;
    },
    updateFirstname(state, val) {
      state.firstName = val;
    },
    updateLastname(state, val) {
      state.lastName = val;
    },
    updateRole(state, val) {
      state.role = val;
    },
    setAccessToken(state, val) {
      state.accessToken = val;
    },
    logOut(state) {
      state.loggedIn = false;
      state.accessToken = null;
      state.role = 3;
      state.email = "";
      state.firstname = "";
      state.lastname = "";
    },
  },
  actions: {},
  modules: {},
});
