import { createRouter, createWebHistory } from "vue-router";
import Courses from "../views/Courses.vue";
import About from "@/views/About.vue";
import CourseQueue from "@/views/CourseQueue.vue";

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
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
