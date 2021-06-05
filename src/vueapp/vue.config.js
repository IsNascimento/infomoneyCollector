module.exports = {
    chainWebpack: config => {
      config.performance
        .maxEntrypointSize(400000000)
        .maxAssetSize(400000000)
    }
  }