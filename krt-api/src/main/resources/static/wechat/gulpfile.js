var gulp         = require('gulp');
var postcss      = require('gulp-postcss');
var px2rem       = require('postcss-px2rem');
var less         = require('gulp-less');
var plumber      = require('gulp-plumber');         // 捕获处理任务中的错误
var autoprefixer = require('gulp-autoprefixer');    // 自动添加浏览器前缀
var minifycss    = require('gulp-minify-css');      // 压缩css文件,并给引用url添加版本号避免缓存
var browserSync  = require('browser-sync').create();// 页面无刷新式，文件刷新能力
var reload       = browserSync.reload;
var livereload   = require('gulp-livereload');
var gulpBalel = require('gulp-babel');
var gulpJsmin = require('gulp-uglify');
var gulpImgsmin = require('gulp-imagemin');
var gulpHtmlMin = require('gulp-htmlmin');
var app = require('./app');
// gulp.series：task按照顺序执行
// gulp.paralle：task可以并行计算
var cssSrc = 'skin/css/*.less';             //作用：监测less变化，即时生成css文件
var cssSrc2 = 'skin/css/screen.less';       //需要编译的less文件匹配字符串
var cssDst = 'skin/css/';                   //处理好的css文件存放目录

var jsSrc = ['skin/js/base.js','skin/js/app.js'];
var imagesSrc = 'skin/images/*';
var htmlSrc = ['rentPayment.html','rentPayment-detail.html','rentPayment-userBind.html'];
var otherStr = ['layer/','layer/*','layer/*/*','layer/*/*/*'];
var otherJsStr = 'skin/js/jquery-2.1.1.min.js';
var cssDist = 'dist/skin/css/';                   //处理好的css文件存放目录
var dist = 'dist/';                   //处理好的html文件存放目录

// css文件处理
gulp.task('css', function () {
    var processors = [px2rem({remUnit: 75})];
    return gulp.src(cssSrc2)            // 找到符合路径匹配的less文件
        .pipe(plumber())                // 捕获处理任务中的错误
        .pipe(less())                   // 编译Less文件，转化成.css
        .pipe(postcss(processors))      // 将px转换成rem
        .pipe(autoprefixer('last 10 version', 'Android >= 2.0'))    // 自动添加浏览器前缀
        .on('error',function(e){        // 监听捕获的错误
            console.log(e);
            this.emit('end');
        })
        .pipe(minifycss())              // 压缩css文件,并给引用url添加版本号避免缓存
        .pipe(gulp.dest(cssDst))        // 将处理好的css文件写入到cssDst中
        .pipe(gulp.dest(cssDist))        // 将处理好的css文件写入到cssDist中
        .pipe(reload({stream: true}));   // 刷新app-style.css文件
});

var clean   = require('gulp-clean'), pump    = require('pump');//清理dist文件夹
gulp.task('clean', function(cb) {
	pump([
		gulp.src('./dist'),
		clean()
	], cb)
});

// dist文件夹生成
gulp.task('dist', gulp.series('clean',gulp.parallel('css', function (done) {
     gulp.src(jsSrc,{base:'.'})       // 路径问题：gulpfile.js为路径的起点。此路径表示js文件下的所有js文件。
	 	.pipe(gulpBalel({presets: ['env']}))
	 	.pipe(gulpJsmin())            //压缩
		.pipe(gulp.dest(dist));    //打包压缩在build目录下。

	 gulp.src(otherJsStr,{base:'.'})       // 路径问题：gulpfile.js为路径的起点。此路径表示js文件下的所有js文件。
		.pipe(gulp.dest(dist));    //打包压缩在build目录下。
		
	gulp.src(imagesSrc,{base:'.'})
		.pipe(gulpImgsmin())
		.pipe(gulp.dest(dist));
		

	var options = {
        removeComments: true,  //清除HTML注释
        collapseWhitespace: true,  //压缩HTML
        collapseBooleanAttributes: true,  //省略布尔属性的值 <input checked="true"/> ==> <input checked />
        removeEmptyAttributes: true,  //删除所有空格作属性值 <input id="" /> ==> <input />
        removeScriptTypeAttributes: true,  //删除<script>的type="text/javascript"
        removeStyleLinkTypeAttributes: true,  //删除<style>和<link>的type="text/css"
        minifyJS: true,  //压缩页面JS
		mangle:true, //修改js变量名
        minifyCSS: true  //压缩页面CSS
    };
	gulp.src(htmlSrc)
		.pipe(gulpHtmlMin(options))
		.pipe(gulp.dest(dist));

	gulp.src(otherStr,{base:'.'})
	.pipe(gulp.dest(dist));

	done();
})));

// 静态服务器配置，以css任务为前置
gulp.task('serve', gulp.series('css', function() {

    // 动态服务器 配置  - - 支持 POST 请求  使用 webStorm 内置服务器代理
    //browserSync.init({
    //     proxy: "http://localhost:63342/webStorm下项目名称/基础模板页.html",    //apache或iis等代理地址
    //     notify: false              // 刷新是否提示
    //});


    // 静态服务器 配置
    browserSync.init({
        server: {
            baseDir: "./",
            index:"/rentPayment-userBind.html"
        },
        port:3000,
        notify: {
            styles: {
                top: 'auto',
                bottom: '0',
                margin: '0px',
                padding: '5px',
                position: 'fixed',
                fontSize: '10px',
                zIndex: '9999',
                borderRadius: '5px 0px 0px',
                color: 'white',
                textAlign: 'center',
                display: 'block',
                backgroundColor: 'rgba(60, 197, 31, 0.498039)'
            }
        }
    });
    // 监测less改变时，重新生成screen.css
    gulp.watch(cssSrc,gulp.series('css'));
    // 监测html，(*.jpg/*.png/*.gif)，js文件的改变，在浏览器中刷新该改变文件
    gulp.watch(['*.html','**/skin/images/*','skin/js/*.js']).on("change", reload);
}));

// 静态服务器配置，以css任务为前置
gulp.task('appNext', gulp.series('css', function() {
    livereload.listen();//{start:true}
    //app.start("http://223.84.197.210:14569/yd_zhny/","3002");
    app.start("http://182.106.189.65:8888/yd_zhny/","3002");
    // 监测less改变时，重新生成app-style.css
    gulp.watch(cssSrc,gulp.series("css"));
    // 监测html，(*.jpg/*.png/*.gif)，js文件的改变，在浏览器中刷新该改变文件   
    gulp.watch(['*.html',cssSrc,'skin/images/*','skin/js/*.js']).on("change",function(file){
        livereload.changed(file.path);
        //reload(file.path);
    });
}));

// 默认执行任务，以serve任务为前置要求
gulp.task('default',  gulp.series('serve'));
gulp.task('app',  gulp.series('appNext'));