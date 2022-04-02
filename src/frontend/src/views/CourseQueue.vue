<template>
  <div>
    <h1>Course title</h1>

    <div id="topbar">
      <h2 id="queue-header">Students in queue</h2>
      <div id="button-position">
        <Button :title="'Enter queue'" @click="goToQueue" />
      </div>
    </div>

    <div id="list">
      <StudentCard
        :guide="true"
        :firstname="'Student'"
        :studentAssistant="'Student assistant'"
        :type="'Type'"
        :time="'Time'"
      />
      <StudentCard :isStudAss="isStudAss" :studentAssistant="'ererer'" />
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
import { useRoute } from "vue-router";
import router from "../router";
import { computed } from "@vue/runtime-core";

export default {
  components: {
    StudentCard,
    Button,
  },
  setup() {
    const route = useRoute();
    let isStudAss = computed(() => {
      return true;
    });

    const goToQueue = () => {
      router.push({
        name: "enterQueue",
        params: { id: route.params.id },
      });
    };

    return { goToQueue, isStudAss };
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
