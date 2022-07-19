const path = require("path");
const { defineConfig } = require("@vue/cli-service");

//function resolve(dir) {
//    return path.join(__dirname, "..", dir);
//}

module.exports = defineConfig({
    //configureWebpack: {
    //    resolve: {
    //        alias: {
    //            "@temp": resolve("src/components/2_templates"),
    //        },
    //    },
    //},
    outputDir: path.resolve(__dirname, "../main/resources/static"),
    devServer: {
        proxy: "http://localhost:8000",
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
