const path = require("path");
const { defineConfig } = require("@vue/cli-service");

//function resolve(dir) {
//    return path.join(__dirname, "..", dir);
//}

module.exports = defineConfig({
  // configureWebpack: {
  //    resolve: {
  //        alias: {
  //            "@temp": resolve("src/components/2_templates"),
  //        },
  //    },
  // },
  outputDir: path.resolve(__dirname, "../main/resources/static"),
  devServer: {
    proxy: "http://localhost:8000",
    // proxy: "http://192.168.0.77:8000",
    // inline: true,

    // port: 8000,

    // publicPath: '/',

    
  },
  transpileDependencies: ["vuetify"],
  css: {
    loaderOptions: {
      scss: {
        additionalData: '@import "@/assets/scss/variables.scss";',
      },
    },
  },
});
