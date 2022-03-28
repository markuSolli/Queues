<template>
  <div id="container"><Navbar /> <router-view /></div>
</template>

<script setup>
import Navbar from "./components/Navbar";
</script>

<style>
* {
  margin: 0px;
  padding: 0px;
}

#container {
  width: 1200px;
  margin: 0 auto;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #ffffff;
  min-height: 100vh;
  background: rgb(11, 4, 36);
  background: linear-gradient(
    17deg,
    rgba(11, 4, 36, 1) 11%,
    rgba(0, 0, 0, 1) 100%
  );
}
</style>
