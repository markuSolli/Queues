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
import ActivateUser from "@/views/ActivateUser";
import ManageUsers from "@/views/ManageUsers";
import ViewCourse from "@/views/ViewCourse";
import ViewCourseFull from "@/views/ViewCourseFull";

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
    path: "/courseQueue/:id",
    name: "courseQueue",
    component: CourseQueue,
    props: true,
  },
  {
    path: "/viewCourseFull/:id",
    name: "viewCourseFull",
    component: ViewCourseFull,
    props: true,
  },
  {
    path: "/viewCourse/:id",
    name: "viewCourse",
    component: ViewCourse,
    props: true,
  },
  {
    path: "/activateUser/:id",
    name: "activateUser",
    component: ActivateUser,
    props: true,
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
    path: "/course/:id",
    name: "courseEdit",
    component: Course,
    props: true,
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
    path: "/manageUsers",
    name: "manageUsers",
    component: ManageUsers,
  },
  {
    path: "/enterQueue/:id",
    name: "enterQueue",
    component: EnterQueue,
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
