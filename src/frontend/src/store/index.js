import { createStore } from "vuex";

export default createStore({
  state: {
    loggedIn: true,
    rank: 1,
    email: "",
    firstname: "",
    surname: "",
  },
  getters: {},
  mutations: {
    updateLoggedin(state, val) {
      state.loggedIn = val;
    },
  },
  actions: {},
  modules: {},
});
