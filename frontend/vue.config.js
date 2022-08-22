const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
});

module.exports = {
  css: {
    loaderOptions: {
      sass: {
        additionalData: `
          @import "@/css/variabled.scss";
        `,
      },
    },
  },
};
