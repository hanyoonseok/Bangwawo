<template>
  <section class="background">
    <HostView
      v-if="state.isHost"
      :dataLen="dataLen"
      :currentStudents="currentStudents"
      :initCurrentStudents="initCurrentStudents"
      :prevClick="prevClick"
      :nextClick="nextClick"
      @changeDataLen="changeDataLen"
    />
    <UserView
      v-else
      :dataLen="dataLen"
      :currentStudents="currentStudents"
      :initCurrentStudents="initCurrentStudents"
      :prevClick="prevClick"
      :nextClick="nextClick"
      @changeDataLen="changeDataLen"
    />
  </section>
</template>

<script>
import { reactive, ref } from "vue";
import HostView from "@/components/class/HostView.vue";
import UserView from "@/components/class/UserView.vue";
export default {
  name: "InClassView",
  components: {
    HostView,
    UserView,
  },
  setup() {
    const state = reactive({
      isHost: false,
    });

    const dataLen = ref(state.isHost ? 12 : 4);
    const dataIdx = ref(0);
    const students = ref([
      {
        id: 1,
        name: "김수빈수빈1",
      },
      {
        id: 2,
        name: "김수빈수빈2",
      },
      {
        id: 3,
        name: "김수빈수빈3",
      },
      {
        id: 4,
        name: "김수빈수빈4",
      },
      {
        id: 5,
        name: "김수빈수빈5",
      },
      {
        id: 6,
        name: "김수빈수빈6",
      },
      {
        id: 7,
        name: "김수빈수빈7",
      },
      {
        id: 8,
        name: "김수빈수빈8",
      },
      {
        id: 9,
        name: "김수빈수빈9",
      },
      {
        id: 10,
        name: "김수빈수빈10",
      },
      {
        id: 11,
        name: "김수빈수빈11",
      },
      {
        id: 12,
        name: "김수빈수빈12",
      },
      {
        id: 13,
        name: "김수빈수빈13",
      },
      {
        id: 14,
        name: "김수빈수빈14",
      },
      {
        id: 15,
        name: "김수빈수빈15",
      },
    ]);

    const currentStudents = ref([]);

    const initCurrentStudents = () => {
      const tempArr = [];
      for (
        let i = dataIdx.value;
        i < Math.min(dataIdx.value + dataLen.value, students.value.length);
        i++
      ) {
        tempArr.push(students.value[i]);
      }
      currentStudents.value = tempArr;
      console.log(currentStudents.value);
    };

    const nextClick = () => {
      if (dataIdx.value + dataLen.value >= students.value.length) return;
      dataIdx.value += dataLen.value;
      const currentTempArr = [];
      for (
        let i = dataIdx.value;
        i < Math.min(dataIdx.value + dataLen.value, students.value.length);
        i++
      ) {
        currentTempArr.push(students.value[i]);
      }
      currentStudents.value = currentTempArr;
    };

    const prevClick = () => {
      if (dataIdx.value - dataLen.value < 0) return;
      dataIdx.value -= dataLen.value;
      const currentTempArr = [];
      for (
        let i = Math.max(0, dataIdx.value);
        i < dataIdx.value + dataLen.value;
        i++
      ) {
        currentTempArr.push(students.value[i]);
      }
      currentStudents.value = currentTempArr;
    };

    const changeDataLen = (param) => {
      dataLen.value = param;
    };

    return {
      state,
      dataLen,
      currentStudents,
      initCurrentStudents,
      prevClick,
      nextClick,
      changeDataLen,
    };
  },
};
</script>

<style scoped src="@/css/class.scss" lang="scss"></style>
