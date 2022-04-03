import { createApp, Vue } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

// fontawesome - fix this
/* import the fontawesome core */
import { library } from "@fortawesome/fontawesome-svg-core";

/* import specific icons */
import { faUserSecret } from "@fortawesome/free-solid-svg-icons";

/* add icons to the library */
library.add(faUserSecret);

createApp(App).use(store).use(router).mount("#app");
