const path = require("path");
const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  outputDir: path.resolve(__dirname, "../main/resources/static"),
  
  transpileDependencies: [
    'vuetify'
  ]
})
