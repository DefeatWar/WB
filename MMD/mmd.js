/*
                                    mmd.js使用方法
                        根据“./utils/mmd.js”把mmd.js导入你的文件夹
    |--------app.js------------------------  |--------index.wxml-------------
    |                                        |
    |  var mmd = require('./utils/mmd.js');  |   var app = getApp();
    |  App({                                 |   var $ = app.$;
    |    $: mmd.$,                           |   Page({
    |    //你的代码                           |    onLoad: function () {
    |  )}                                    |       app.$(this).init();
    |                                        |     }
    |                                        |   )}
*/


var t_this = null;

var model = false; //模式：生产模式 true，debug模式 false，默认 false

var imgResources = {};//图片资源路径存储

var img;

var $ = function (id) {   //mmd公开的函数
  return {
//初始化
    //init=>MMDinit,格式：$(this).init(null)
    init: () => {
      t_this = id;
    },
//debug模式设置
    //setMod=>set model,格式：$(boolean).setMod(null)
    setMod:() => {
      model=id;
    },
    //getMod=>get model,格式：$(boolean).getMod(null)
    getMod: () => {
      return model;
    },
//网络
    //get
    wget:() =>{

    },
    //post
    wpost: () => {

    },
//判断空
    //noll=>no null,格式：$(value).init(null)
    noll: () => {
      
    },
//关于data
    //setd=>setdata,格式：$(key).setd(value)
    setd: (value) =>  {
      var change = {};
      change[id] = value;
      t_this.setData(change);
    },
    //getd=>getdata,格式：$(key).setd(value)
    getd: (value) => {
       
    },
//小程序相关
    //getPageWidth=>get system info to PageWidth,格式：$().getPageWidth()
    getPageWidth:() => {
      return _$().getsysinfo().windowWidth;
    },
    //getPageHeight=>get system info to PageHeight ,格式：$().getsysinfo()
    getPageHeight:() =>{
      return _$().getsysinfo().windowHeight;
    },
//图片资源路径管理
    //setRs=>onloadImage ,格式：$(src).setRs(图片全部名称)，警告：此函数只支持三位的文件类型名称
    setrs:(name) => {
      var _name = name.substring(0, name.length - 4);
      if(imgResources[_name] == undefined){
        imgResources[_name] = id+"/"+name;
      }
      //log(imgResources[_name]);
      return $(id);
    },
    //getRs=>getsrc ,格式：$(name).getRs()
    getrs:() =>{
      return imgResources[id];
    }
  }
}

var log = function(a){
  console.log(a);
}

module.exports = {
  $: $
};


var _$ = function (id) {   //mmd内部函数
  return {
    //getsysinfo=>get system info ,格式：_$().setd(value)
    getsysinfo: () => {
      var _res = null;
      wx.getSystemInfo({
        success: function (res) {
          _res = res;
        },
      });
      return _res;
    },
    //errLog=>error console log ,格式：_$(e).errLog()
    errLog:(value) =>{
      if (model){
        console.log(value);
        return;
      }
      console.log(id);
    }
  }
}
