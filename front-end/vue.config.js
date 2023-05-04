const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: '0.0.0.0',
    client: {
      webSocketURL: 'ws://0.0.0.0/ws',
    },
    allowedHosts: [
      '.nek0peko.com'
    ]
  }
})
