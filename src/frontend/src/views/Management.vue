<template>
  <div>
    <h1>Management</h1>
    <div id="management-bar">
      <div id="title"><h2>Your courses</h2></div>
      <div id="buttons">
        <Button :title="'Add new course'" :route="'course'" />
      </div>
    </div>
    <div id="course-list">
      <CourseCard :edit="true" />
      <CourseCard :edit="true" />
      <CourseCard :edit="true" />
      <CourseCard :edit="true" />
    </div>
    <div id="management-bar">
      <div id="title"><h2>Users</h2></div>
      <div id="buttons">
        <Button :title="'Add new user'" :route="'course'" />
        <Button :title="'Add new user'" :route="'course'" />
      </div>
    </div>
  </div>
</template>

<script>
import Button from "../components/Button.vue";
import CourseCard from "../components/CourseCard.vue";
export default {
  components: { Button, CourseCard },
};
</script>

<style>
#management-bar {
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin: 25px 0px;
  padding: 25px;
}

#title {
  justify-self: start;
  grid-column: 1 / 2;
}

#buttons {
  justify-self: end;
  grid-column: 2 / 3;
}
</style>
