<template>
  <div ref="trigger">-</div>
</template>
<script>
import { defineComponent, onMounted, onUnmounted, ref } from "vue";

export default defineComponent({
  setup(props, { emit }) {
    const trigger = ref();
    const options = {
      root: null,
      threshold: 1,
    };
    let observer = null;
    const handleIntersect = (entry) => {
      if (entry.isIntersecting) emit("triggerIntersected", trigger.value);
    };

    onMounted(() => {
      try {
        observer = new IntersectionObserver((entries) => {
          handleIntersect(entries[0]);
        }, options);
        observer.observe(trigger.value);
      } catch (err) {
        console.error(err);
      }
    });

    onUnmounted(() => {
      observer.value = null;
    });

    return {
      trigger,
    };
  },
});
</script>

<style scoped>
div {
  width: 100%;
}
</style>
