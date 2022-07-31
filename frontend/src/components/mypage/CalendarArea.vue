<template>
  <div class="calendar-area" style="height: 150px">
    <Calendar is-expanded :attributes="attributes" class="custom-calendar" />
  </div>
</template>

<script>
import "v-calendar/dist/style.css";

export default {
  name: "CalendarArea",
  data() {
    return {
      // Attributes are supplied as an array
      attributes: [
        // This is a single attribute
        {
          key: {},
          highlight: {
            color: "yellow",
            fillMode: "solid",
          },
          popover: {
            label: "오리선생님 수업있음",
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
          dates: new Date(),
          // You can optionally provide dates to exclude
          excludeDates: null,
          // Think of `order` like `z-index`
          order: 0,
        },
      ],
    };
  },
};
</script>

<style scoped src="@/css/calendar.scss" lang="scss"></style>
