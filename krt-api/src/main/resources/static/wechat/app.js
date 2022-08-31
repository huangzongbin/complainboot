//var basePath = 'http://223.84.197.218:8099/complain';
// var basePath = 'http://172.0.52.10:8080/complain';
// var basePath = 'http://172.30.1.38:8087/complain';
// var basePath = 'http://223.84.197.218:8099/complain';

// var basePath = "/" + location.href.split("/")[3];
// var basePath = 'http://zfbz.gzbzxzf.cn:8099/complain_test';

// var basePath = 'http://172.30.1.22:8089/complain_test';
var basePath = ""

var url = window.location.pathname;
if (url.indexOf("test") > -1) {
    basePath = "/complain_test"  //测试
} else {

    basePath = '/complain'   //正式
}
