import { faListSquares } from "@fortawesome/free-solid-svg-icons";
import { createStore } from "vuex";

export default createStore({
  state: {
    loggedIn: true,
    accessToken: null,
    role: 1,
    email: "Smith@ntnu.no",
    firstname: "smith",
    lastname: "hansen",
  },
  getters: {},
  mutations: {
    updateLoggedin(state, val) {
      state.loggedIn = val;
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
