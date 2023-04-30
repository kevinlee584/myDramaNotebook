var path = require('path')
var webpack = require('webpack')
var HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = {
  mode: 'development',
  entry: [
    './src/index.js'
  ],
  output: {
    path: path.join(__dirname, 'dist'),
    filename: 'bundle.js'
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new HtmlWebpackPlugin({
      template: './src/static/index.html'
    })
  ],
  module: {
    rules:[
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        use: "babel-loader"
      },
      {
        test: /\.html$/,
        use: "html-loader"
      },
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader"],
      },
      {
        test: /\.(png|jpe?g|gif)$/i,
        use: [
          {
            loader: 'file-loader', 
            options: {
              name: '[name].[ext]',
              outputPath: './icons'
            }
          }
        ]
      }, 
      {
        test: /\.svg$/i,
        use: "@svgr/webpack",
      },
    ], 
  },
  devServer: {
    static: { 
      directory: path.join(__dirname, 'dist'),
    },
    compress: true,
    hot: true,
    open: true,
    port: 3000
  }
}