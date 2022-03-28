<template>
  <div id="main">
    <div id="queue-container">
      <div id="queue-container-header"><h1>Active</h1></div>
      <Card />
      <Card />
      <Card />
      <Card />
      <Card />
    </div>

    <div id="queue-container">
      <div id="queue-container-header"><h1>Inactive</h1></div>
      <div>There is currently no courses inactive courses</div>
    </div>
  </div>
</template>

<script setup>
import Card from "../components/CourseCard";
</script>

<style>
#main {
  width: 1200px;
  margin: 0 auto;
}

#queue-container {
  margin: 20px 0px 0px 0px;

  border-color: linear-gradient(
    17deg,
    rgba(16, 16, 17, 1) 38%,
    rgba(0, 0, 0, 1) 100%
  );

  padding: 10px;
  align-content: center;
}

#queue-container-header {
  padding: 20px;
}
</style>
