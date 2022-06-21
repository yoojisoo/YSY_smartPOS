const path = require("path");
const { defineConfig } = require("@vue/cli-service");

module.exports = defineConfig({
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
