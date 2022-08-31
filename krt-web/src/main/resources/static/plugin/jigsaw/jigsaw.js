(function(window) {
	var l = 42,
		r = 10,
		w = 310,
		h = 150,
		PI = Math.PI;
	var L = l + r * 2;
	// 生成制定范围内的随机数
	function getRandomNumberByRange(start, end) {
		return Math.round(Math.random() * (end - start) + start)
	}
	// 生成元素
	function createElement(tagName) {
		return document.createElement(tagName)
	}
	// 添加类名
	function addClass(tag, className) {
		tag.className += ' ' + className;
	}
	// 移除类名
	function removeClass(tag, className) {
		tag.className = tag.className.replace(className, '')
	}
	// 获取随机图片
	function getRandomImg(url) {
		return url + '?image=' + getRandomNumberByRange(0, 100)
	}
	// 求和
	function sum(x, y) {
		return x + y
	}
	// 求平方
	function square(x) {
		return x * x
	}
	// 构造函数
	function Jigsaw(el, callBack, imgRequestCallBack) {
		this.el = el;
		this.callBack = callBack;
		this.imgRequestCallBack = imgRequestCallBack;
	}
	// 原型
	Jigsaw.prototype = {
		constructor: Jigsaw,
		init: function() {
			this.initDOM();
			this.bindEvents();
			this.imgReady = false;
			this.canDrag = true;
			this.imgRequestCallBack();
			return this;
		},
		initDOM: function() {
			var canvas = createElement('img');
			var block = createElement('img');
			var sliderContainer = createElement('div');
			var refreshIcon = createElement('div');
			var sliderMask = createElement('div');
			var slider = createElement('div');
			var sliderIcon = createElement('span');
			var text = createElement('span');
			canvas.style.width = w + 'px';
			canvas.style.height = h + 'px';
			block.style.width = L + 'px';
			block.style.height = 0;
			// block.style.height = h + 'px';
			block.className = 'block';
			sliderContainer.className = 'sliderContainer';
			refreshIcon.className = 'refreshIcon';
			sliderMask.className = 'sliderMask';
			slider.className = 'slider';
			sliderIcon.className = 'sliderIcon';
			text.innerHTML = '向右滑动滑块填充拼图';
			text.className = 'sliderText';
			var el = this.el;
			el.appendChild(canvas);
			el.appendChild(refreshIcon);
			el.appendChild(block);
			slider.appendChild(sliderIcon);
			sliderMask.appendChild(slider);
			sliderContainer.appendChild(sliderMask);
			sliderContainer.appendChild(text);
			el.appendChild(sliderContainer);
			this.canvas = canvas;
			this.block = block;
			this.sliderContainer = sliderContainer;
			this.refreshIcon = refreshIcon;
			this.slider = slider;
			this.sliderMask = sliderMask;
			this.sliderIcon = sliderIcon;
			this.text = text;
			// 设定初始位置
			this.intX = 0;
			this.intY = 0;
		},
		initImg: function(dom, url, imgloadCallback) {
			dom.crossOrigin = "Anonymous";
			dom.onload = imgloadCallback;
			dom.src = url;
		},
		handleImg: function(bigImg, sonImg) {
			this.bigImg = bigImg;
			this.sonImg = sonImg;
			var _this = this;
			var imgloadCallback = function() {
				_this.imgReady = true;
			}
			this.initImg(this.canvas, this.bigImg, imgloadCallback);
			this.initImg(this.block, this.sonImg, imgloadCallback);
			this.block.style.height = h + 'px';
		},
		bindEvents: function() {
			var _this = this;
			this.el.onselectstart = function() {
				return false;
			}
			this.refreshIcon.onclick = function() {
				_this.reset();
			}
			var originX, isMouseDown = false;
			this.slider.addEventListener('mousedown', function(e) {
				if (!_this.canDrag || isMouseDown || !_this.imgReady || _this.isChecking) return false;
				isMouseDown = true;
				originX = e.x;
				_this.intX = _this.slider.style.left ? _this.slider.style.left.split('px')[0] * 1 : 0;
				_this.sliderContainer.className = 'sliderContainer sliderContainer_active';
			})
			document.addEventListener('mousemove', function(e) {
				if (!_this.canDrag || !isMouseDown) {
					return
				}
				var moveX = e.x - originX;
				var left = moveX + _this.intX;
				left = left < 0 ? 0 : left;
				left = left > (w - l) ? (w - l) : left;
				_this.slider.style.left = left + 'px';
				_this.sliderMask.style.width = left + 'px';
				var blockLeft = (w - L) * left / (w - l);
				_this.block.style.left = blockLeft + 'px';
			})
			document.addEventListener('mouseup', function(e) {
				// 重置起始值
				if (!_this.canDrag || !isMouseDown) {
					return
				}
				isMouseDown = false;
				_this.sliderContainer.className = 'sliderContainer';
				_this.isChecking = true;
				var left = parseInt(_this.block.style.left);
				_this.canDrag = false;
				_this.callBack && _this.callBack(left);
			})
		},
		success: function() {
			this.isChecking = false;
			this.sliderContainer.className = 'sliderContainer sliderContainer_success';
			this.text.innerHTML = '';
		},
		fail: function() {
			this.isChecking = false;
			this.sliderContainer.className = 'sliderContainer sliderContainer_fail';
			this.text.innerHTML = '再试一次';
			this.reset();
		},
		reset: function() {
			if (this.isChecking) {
				return;
			}
			if(this.sliderContainer.className.indexOf('sliderContainer_success')>-1){
				this.text.innerHTML = '向右滑动滑块填充拼图';
			}else{
				this.text.innerHTML = '再试一次';
			}
// 			removeClass(this.sliderContainer, 'sliderContainer_fail');
			removeClass(this.sliderContainer, 'sliderContainer_success');
			this.canvas.src = '';
			this.block.src = '';
			this.intX = 0;
			this.slider.style.left = 0;
			this.block.style.left = 0;
			this.block.style.height = 0;
			this.sliderMask.style.width = 0;
			this.isChecking = false;
			this.imgReady = false;
			this.canDrag = true;
			this.imgRequestCallBack();
		}
	}
	window.jigsaw = {
		init: function(element, callBack, imgRequestCallBack) {
			return new Jigsaw(element, callBack, imgRequestCallBack).init();
		}
	}
}(window))
