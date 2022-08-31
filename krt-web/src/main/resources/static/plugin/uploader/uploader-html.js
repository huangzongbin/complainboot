$.fn.krtUploader=function(options){
	var defaults={
		type:'simple',
		autoUpload:true
	};
	var htmls={
		'simple':'<div class="file-list" data-drag-placeholder="请拖拽文件到此处"></div><button type="button" class="btn btn-primary uploader-btn-browse"><i class="icon icon-cloud-upload"></i> 选择文件</button>',
		'lg-list':'<div class="uploader-message text-center"><div class="content"></div><button type="button" class="close">×</button></div><div class="uploader-files file-list file-list-lg" data-drag-placeholder="请拖拽文件到此处"></div><div class="uploader-actions"><div class="uploader-status pull-right text-muted"></div><button type="button" class="btn btn-link uploader-btn-browse"><i class="icon icon-plus"></i> 选择文件</button><button type="button" class="btn btn-link uploader-btn-start"><i class="icon icon-cloud-upload"></i> 开始上传</button></div>',
		'grid-list':'<div class="uploader-message text-center"><div class="content"></div><button type="button" class="close">×</button></div><div class="uploader-files file-list file-list-grid"  data-drag-placeholder="请拖拽文件到此处"></div><div class="uploader-actions"><div class="uploader-status pull-right text-muted"></div><button type="button" class="btn btn-link uploader-btn-browse"><i class="icon icon-plus"></i> 选择文件</button><button type="button" class="btn btn-link uploader-btn-start"><i class="icon icon-cloud-upload"></i> 开始上传</button></div>'
	};
	options=$.extend({},defaults,options);
	if(options.type=='simple'){
		options.autoUpload=true;
	};
	$(this).html(htmls[options.type]);
	$(this).uploader(options);
}