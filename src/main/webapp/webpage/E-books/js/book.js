$(function () {
	/*返回顶部*/
    var backButton=$('#indextop');
    function backToTop() {
        $('html,body').animate({
            scrollTop: 0
        }, 800);
    }
    backButton.on('click', backToTop);
  	/*书库*/  	
  	$(".lib-style").on('click','li',function(){
  		$(this).attr("class","on");
  		$(this).siblings("li").attr("class","");
  	});
  	/*充值*/
  	$('.top-money ul').find('li').on('click',function(){
  		$(this).attr('class','on');
  		$(this).siblings('li').attr('class','');
  	})
  	/*我的消费*/
  	$('.consu-ul ul').find('li').toggle(function(){
  		$(this).attr("class","on");
  		$(this).find('img').attr('src','images/con-xia.png');
  	},function(){
  		$(this).attr("class","");
  		$(this).find('img').attr('src','images/con-zuo.png');
  	})
  	/*个人中心--充值*/
  	//点击“充值”--显示
  	$('.per-sign').on('click',function(){
  		$('.sign-in').fadeIn();
  	})
  	//点击或者触控弹出层外的半透明遮罩层，关闭弹出层 
	$('.sign-in').on('click', function(event) { 
		if (event.target == this){ 
			$('.sign-in').fadeOut();
		} 
	}); 
	/*书籍详情*/
	//点击“设置”--字号&背景出来
	$('.book-aset').on('click',function(){
		$('.book-set').fadeIn();
		$(this).find('img').attr('src','images/book-b2.png');
	})
	$('.book-a').find('.book-sma').on('click',function(){
		$('.deta-cp').find('p').css('font-size','12px');
	})
	$('.book-a').find('.book-midd').on('click',function(){
		$('.deta-cp').find('p').css('font-size','14px');
	})
	$('.book-a').find('.book-big').on('click',function(){
		$('.deta-cp').find('p').css('font-size','16px');
	})
	//“白天”-“夜间”
	var bookDay=document.getElementById("bookday");
	$('.book-aday').toggle(function(){		
		$(this).find('img').attr('src','images/book-c2.png');
		bookDay.innerHTML="白天";
		$('.index-body').css('background','#000');
	},function(){		
		$(this).find('img').attr('src','images/book-c3.png');
		bookDay.innerHTML="夜间";
		$('.index-body').css('background','#fff');
	})
	//背景颜色
	$('.book-b ul>li').find('p').on('click',function(){
		var bookP = $(this).css('background');
		$('.index-body').css('background', bookP );		
	})
	/*点击“设置”*/
	$('.deta-set').on('click',function(){
  		$('.book-more-div').fadeIn();
  	})
  	//点击或者触控弹出层外的半透明遮罩层，关闭弹出层 
  	$(window).scroll(function(){		
		$('.book-more-div').hide();
	});
	$('.deta-ctxt').on('click', function( ) { 		
		$('.book-more-div').hide();
	});
	/*阅读历史*/
	$('.colle-btn').on('click',function(){
		$('.colle-pop-div').fadeOut();
	})
})