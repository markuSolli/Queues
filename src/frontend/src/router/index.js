import { createRouter, createWebHistory } from "vue-router";
import Courses from "../views/Courses.vue";
import About from "@/views/About.vue";
import CourseQueue from "@/views/CourseQueue.vue";
import Login from "@/views/Login.vue";
import Archieve from "@/views/Archieve";
import Profile from "@/views/Profile";
import Management from "@/views/Management";
import Course from "@/views/Course";
import EnterQueue from "@/views/EnterQueue";
import CourseTest from "@/views/CourseTest";

const routes = [
  {
    path: "/",
    name: "Courses",
    component: Courses,
  },
  {
    path: "/about",
    name: "about",
    component: About,
  },
  {
    path: "/courseQueue",
    name: "courseQueue",
    component: CourseQueue,
  },
  {
    path: "/login",
    name: "login",
    component: Login,
  },
  {
    path: "/archieve",
    name: "Archieve",
    component: Archieve,
  },
  {
    path: "/course",
    name: "course",
    component: Course,
  },

  {
    path: "/course:id",
    name: "courseEdit",
    component: Course,
    props: true,
  },
  {
    path: "/coursetest",
    name: "coursetest",
    component: CourseTest,
  },
  {
    path: "/profile",
    name: "profile",
    component: Profile,
  },
  {
    path: "/management",
    name: "management",
    component: Management,
  },
  {
    path: "/enterQueue",
    name: "enterQueue",
    component: EnterQueue,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
