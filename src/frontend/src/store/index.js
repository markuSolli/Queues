import { faListSquares } from "@fortawesome/free-solid-svg-icons";
import { createStore } from "vuex";

export default createStore({
  state: {
    loggedIn: true,
    accessToken: null,
    role: 2,
    email: "",
    firstName: "",
    lastName: "",
  },
  getters: {},
  mutations: {
    updateLoggedin(state, val) {
      state.loggedIn = val;
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
