<template>
  <div>
    <h1>Course title</h1>

    <div id="topbar">
      <h2 id="queue-header">Students in queue</h2>
      <div id="button-position">
        <Button :title="'Go to queue'" :route="'enterQueue'" />
      </div>
    </div>

    <div id="list">
      <StudentCard
        :guide="true"
        :firstname="'Name'"
        :studentAssistant="'Student assistant'"
        :type="'Type'"
        :time="'Time'"
      />
      <StudentCard />
      <StudentCard />
      <StudentCard />
      <StudentCard />
      <StudentCard />
    </div>
  </div>
</template>

<script>
import StudentCard from "../components/StudentCard.vue";
import Button from "../components/Button.vue";

export default {
  components: {
    StudentCard,
    Button,
  },
  props: ["course"],
  setup() {
    return {};
  },
};
</script>

<style>
#list {
  clear: both;
}

#topbar {
  display: grid;
  grid-template-columns: 1fr 1fr;
  width: 100%;
  margin: 30px 0px;
}

#queue-header {
  justify-self: start;
  margin-left: 20px;
}
#button-position {
  justify-self: end;
  margin-right: 20px;
}
</style>
