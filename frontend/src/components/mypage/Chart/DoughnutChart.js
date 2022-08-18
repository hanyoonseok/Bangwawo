import { defineComponent, h } from "vue";

import { Doughnut } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale,
} from "chart.js";

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale);

export default defineComponent({
  name: "DoughnutChart",
  components: {
    Doughnut,
  },
  props: {
    emotion: {
      type: Object,
    },
    chartId: {
      type: String,
      default: "doughnut-chart",
    },
    width: {
      type: Number,
      default: 400,
    },
    height: {
      type: Number,
      default: 400,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Array,
      default: () => [],
    },
  },
  setup(props) {
    const chartData = {
      labels: ["화남", "놀람", "피곤", "평범", "기쁨", "슬픔", "두려움"],
      datasets: [
        {
          backgroundColor: [
            "red",
            "orange",
            "green",
            "navy",
            "purple",
            "blue",
            "yellow",
          ],
          data: [
            props.emotion.angry,
            props.emotion.surprised,
            props.emotion.disgusted,
            props.emotion.neutral,
            props.emotion.happy,
            props.emotion.sad,
            props.emotion.fearful,
          ],
        },
      ],
    };

    const chartOptions = {
      responsive: true,
      maintainAspectRatio: false,
    };

    return () =>
      h(Doughnut, {
        chartData,
        chartOptions,
        chartId: props.chartId,
        width: props.width,
        height: props.height,
        cssClasses: props.cssClasses,
        styles: props.styles,
        plugins: props.plugins,
      });
  },
});
