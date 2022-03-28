<template>
  <div id="studentcard-container">
    <div class="element-1"><h4>Name of student</h4></div>
    <div class="element-2"><h4>Time in queue</h4></div>
    <div class="element-3">
      <font-awesome-icon icon="fa-solid fa-address-book" />
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
#studentcard-container {
  background: rgb(7, 3, 29);
  /*background: linear-gradient(
    217deg,
    rgba(7, 3, 29, 1) 0%,
    rgba(0, 0, 0, 1) 100%
  );*/
  border-radius: 10px;
  text-align: left;
  padding: 25px;
  margin: 0px 0px 10px 0px;
  display: grid;
  grid-template-areas: "element-1 element-2 element-3";
}

#studentcard-container:hover {
  cursor: pointer;
}

#prog-text {
  display: inline-block;
  margin-right: 15px;
}

.element-1 {
  grid-area: element-1;
}

.element-2 {
  grid-area: element-2;
}

.element-3 {
  text-align: right;
  grid-area: element-3;
  font-size: 12px;
}

.element-4 {
  grid-area: element-4;
  justify-self: end;
  align-self: end;
}
</style>
