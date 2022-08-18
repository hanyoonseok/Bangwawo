import { defineComponent, h } from "vue";

import { Bar } from "vue-chartjs";

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
);

export default defineComponent({
  name: "BarChart",
  components: {
    Bar,
  },
  props: {
    emotion: {
      type: Object,
    },
    chartId: {
      type: String,
      default: "bar-chart",
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
          label: "수업 중 감정 평균치",
          backgroundColor: "#f87979",
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
      h(Bar, {
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
