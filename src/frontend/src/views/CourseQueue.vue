<template>
  <div>
    <h1>Course title</h1>
    <div id="container">
      <h2 id="queue-header">Students in queue:</h2>
      <div id="list">
        <StudentCard />
        <StudentCard />
        <StudentCard />
        <StudentCard />
        <StudentCard />
      </div>
    </div>
  </div>
</template>

<script setup>
import StudentCard from "../components/StudentCard.vue";
</script>

<style>
#container {
}

#list {
  clear: both;
}

#queue-header {
  float: left;
  margin: 40px;
}
</style>
