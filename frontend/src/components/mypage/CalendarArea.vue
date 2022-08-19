<template>
  <div class="calendar-area" style="height: 150px">
    <Calendar is-expanded :attributes="attributes" class="custom-calendar" />
  </div>
</template>

<script>
import "v-calendar/dist/style.css";
import { ref, watch } from "vue";

export default {
  name: "CalendarArea",
  props: ["comingClass", "userInfo"],
  setup(props) {
    // Attributes are supplied as an array
    const attributes = ref([]);

    watch(
      () => props.comingClass,
      () => {
        setAttributes();
      },
      { deep: true },
    );

    const setAttributes = () => {
      attributes.value = [];
      props.comingClass.forEach((e) => {
        console.log(e);
        attributes.value.push({
          key: {},
          highlight: {
            color: "yellow",
            fillMode: "solid",
          },
          popover: {
            label:
              props.userInfo.userType === "volunteer"
                ? `${e.title}수업 예정`
                : `${e.volunteer.nickname} 강사님의 수업`,
            isInteractive: true,
          },
          dot: {
            color: "pink",
            fillMode: "solid",
          }, // Boolean, String, Object
          bar: false, // Boolean, String, Object
          content: "yellow", // Boolean, String, Object
          // Your custom data object for later access, if needed
          // We also need some dates to know where to display the attribute
          // We use a single date here, but it could also be an array of dates,
          //  a date range or a complex date pattern.
          dates: `${e.stime[0]}-${e.stime[1]}-${e.stime[2]} ${e.stime[3]}:${e.stime[4]}`,
          // You can optionally provide dates to exclude
          excludeDates: null,
          // Think of `order` like `z-index`
          order: 0,
        });
      });
      console.log(attributes.value);
    };
    setAttributes();
    // This is a single attribute

    return {
      attributes,
    };
  },
};
</script>

<style scoped src="@/css/calendar.scss" lang="scss"></style>
