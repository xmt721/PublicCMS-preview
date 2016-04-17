var __ = {
	init : function() {
		$(function() {
			__.a.markOuterLink();
			__.screenBox.init();
		});
	},
	create : function() {
		return new Object;
	},
	screenBox : {
		init : function() {
			function fullScreen() {
				$('.screenBox').parent().height(
						$(window).height()
								- parseInt($('body').css('padding-bottom'))
								- parseInt($('body').css('padding-bottom')));
			}
			if ($('.screenBox')) {
				fullScreen();
				$(window).resize(function() {
					fullScreen();
				});
			}
		}
	},
	width : function() {
		$('body').css('overflow', 'hidden');
		var $width = $(window).width();
		$('body').css('overflow', 'auto');
		return $width;
	},
	a : {
		markOuterLink : function() {
			$('a[href^=http],a[href^=\\/\\/]').addClass('outer-link-a');
		},
	},
};
__.init();
__.CreateCards = function() {
	var cards = __.create();
	cards.getTotal = function() {
		var $width = __.width();
		if ($width >= 1850) {
			return 4;
		} else if ($width >= 1530) {
			return 3;
		} else if ($width >= 960) {
			return 2;
		} else if ($width >= 640) {
			return 1;
		} else {
			return 0;
		}
	};
	cards.total = cards.getTotal();
	cards.draw = function($data, func) {
		var flag = false;
		$data.find('section').each(function() {
			var $current = $('.cards>li:first');
			$('.cards>li:gt(0):lt(' + cards.total + ')').each(function() {
				if ($current.height() > $(this).height()) {
					$current = $(this);
				}
			});
			$(this).css('opacity', '0').appendTo($current).animate({
				opacity : '1'
			});
			if ('function' == typeof func) {
				func($(this));
			}
			if (!flag)
				flag = true;
		});
		return flag;
	};
	cards.resize = function() {
		var oldtotal = this.total;
		this.total = this.getTotal();
		if (oldtotal > this.total) {
			$('.cards>li:eq(' + oldtotal + ')').find('section').each(
					function() {
						$(this).appendTo($('#data'));
					});
			this.draw($('#data'));
		} else if (this.total > oldtotal) {
			var reDealNum = Math.floor($('.cards>li>section').length
					/ $('.cards>li:lt(' + (this.total + 1) + ')').length)-1;
			$('.cards>li:lt(' + this.total + ')').each(function() {
				$(this).find('section:gt(' + reDealNum + ')').each(function() {
					$(this).appendTo($('#data'));
				});
			});
			this.draw($('#data'));
		}
	};
	cards.init = function() {
		$(function() {
			for ( var i = $('.cards>li').length; i < 5; i++) {
				$('<li></li>').appendTo($('.cards'));
			}
			$('<div id="data"></div>').hide().appendTo($('body'));
			if (1 < $('.cards>li:first').children().length && 1 <= cards.total) {
				$('.cards>li:first').find('section').each(function(index) {
					$(this).appendTo($('#data'));
				});
				cards.draw($('#data'));
			}
			$(window).resize(function() {
				cards.resize();
			});
		});
		return this;
	};
	return cards;
};
__.CreateFooter = function(interval) {
	var footer = __.create();
	if ('number' != typeof interval)
		interval = 5;
	footer.run = false;
	footer.interval = interval * 1000;
	footer.timer = setInterval('__.footer.scroller()', footer.interval);
	footer.current = 0;
	footer.scroller = function(index) {
		var totalNum = $('.footer-scroller>p').length;
		if (totalNum <= 1) {
			$('.footer-scroller .controller').hide();
		}
		if ('number' == typeof index) {
			this.current = index;
		} else {
			if (this.run)
				this.current = this.current + 1;
		}
		if (this.current < 0)
			this.current = totalNum - 1;
		if (this.current >= totalNum)
			this.current = 0;
		$('.footer-scroller').stop().animate({
			'margin-top' : '-' + (this.current * 46) + 'px'
		});
	};
	footer.changeText = function($message, text) {
		$message.find('span').text(text);
	};
	footer.message = function(text, style) {
		if ('undefined' == typeof style)
			style = '';
		if ('undefined' == typeof text || '' == text)
			text = 'empty message';
		this.scroller(0);
		return $(
				'<p class="text-center ' + style + '"><span>' + text
						+ '</span></p>').hide().prepend(
				$('<a href="javascript:void(0)" class="close">close</a>')
						.click(function() {
							$(this).parent().remove();
						}).hide().delay(1000).fadeIn()).prependTo(
				$('.footer-scroller')).slideDown('fast');
	};
	footer.loading = function() {
		this.run = false;
		return this.message('loading...');
	};
	footer.complete = function($message, func) {
		this.resize();
		$message.slideUp('slow', function() {
			$(this).remove();
			if ('function' == typeof func)
				func();
		});
	};
	footer.resize = function() {
		var $width = __.width();
		if ($width >= 1210) {
			this.run = false;
			this.scroller(0);
		} else {
			this.run = true;
		}
	};
	footer.init = function() {
		$(function() {
			footer.resize();
			$('.footer-scroller,.footer-scroller-controller').bind('click',
					function() {
						footer.run = false;
						setTimeout('__.footer.resize()', 5000);
					});
			$('.footer-scroller-controller .next').click(function() {
				footer.scroller(footer.current + 1);
			});
			$('.footer-scroller-controller .previous').click(function() {
				footer.scroller(footer.current - 1);
			});
			$(window).resize(function() {
				footer.resize();
			});
		});
		return this;
	}
	return footer;
}
__.CreateDataLoader = function(url, startpage, pages, func) {
	var dataLoader = __.create();
	dataLoader.url = url;
	dataLoader.startpage = startpage;
	dataLoader.pages = pages;
	dataLoader.message;
	dataLoader.loading = false;
	dataLoader.getData = function() {
		this.message = __.footer.loading();
		this.loading = true;
		this.startpage += this.pages;
		$
				.ajax({
					url : this.url + this.startpage,
					success : function(data, status) {
						if ('success' == status) {
							if (__.cards.draw($(data, func))) {
								__.footer.complete(dataLoader.message,
										function() {
											dataLoader.loading = false;
											if ($(document).height()
													- $(window).height() <= $(
													window).scrollTop()) {
												dataLoader.load();
											}
										});
							} else {
								dataLoader.loading = true;
								dataLoader.message.addClass('red');
								__.footer.changeText(dataLoader.message,
										'load data complete!');
							}
						} else {
							dataLoader.message.addClass('red');
							__.footer.changeText(dataLoader.message,
									'load data error');
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						dataLoader.message.addClass('red');
						__.footer.changeText(dataLoader.message,
								'load data error');
					}
				});
	};
	dataLoader.load = function() {
		if (!this.loading
				&& ($(document).height() - $(window).scrollTop()) <= $(window)
						.height() + 5) {
			this.getData();
		}
	};
	dataLoader.init = function() {
		$(function() {
			dataLoader.load();
			$(window).scroll(function() {
				dataLoader.load();
			});
			$(window).resize(function() {
				dataLoader.load();
			});
		});
		return this;
	};
	return dataLoader;
};
__.cards = __.CreateCards().init();
__.footer = __.CreateFooter(5).init();

var _hmt = _hmt || [];
(function() {
	var hm = document.createElement("script");
	hm.src = "//hm.baidu.com/hm.js?b3eecd444f274cd3b3bf74dc1e02e8c1";
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(hm, s);
})();