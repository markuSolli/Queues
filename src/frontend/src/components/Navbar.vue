<template>
  <div id="nav">
    <h2>Queues</h2>
    <router-link class="link" tag="li" to="/">Courses</router-link>
    <router-link class="link" tag="li" to="/about">About</router-link>
    <router-link class="link" tag="li" to="/settings">Settings</router-link>
  </div>
</template>

<script>
export default {};
</script>

<style>
#nav {
  text-align: left;
  font-size: 18px;
  padding: 30px;
}

h2 {
  display: inline;
  margin-right: 40px;
}

.link {
  display: inline;
  margin: 0 0 0 20px;
  color: white;
  text-decoration: none;
}

.link:hover {
  color: grey;
}

li.router-link-active,
li.router-link-exact-active {
  background-color: rgb(223, 9, 176);
  color: green;
  cursor: pointer;
}
</style>
