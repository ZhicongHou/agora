$(document).ready(function() {
	//手机自适应
	(function(doc, win){
	    var docE1 = doc.documentElement,
	        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
	        recalc = function(){
	            var clientWidth = docE1.clientWidth;
	            if(!clientWidth) return;
	            docE1.style.fontSize = 20 * (clientWidth / 320) + 'px';            
	        };

	    if (!doc.addEventListener) return;
	    win.addEventListener(resizeEvt,recalc,false);
	    doc.addEventListener('DOMContentLoaded',recalc,false);
	})(document,window);

	//表单被提交时触发
	$("#login-form").submit(function checkLogin(){
        var newurl = "login?code=" + $("#vcode").val() + "&isStudent=" + false;
		$(this).attr("action" , newurl);
		var outputJSON = {
							username: $("#login-username").val(),
							password: $("#login-password").val(),
							code: $("#vcode").val()
                         };
		$.post(
				'login',
				{
            		data:JSON.stringify(outputJSON)
        		}, 
	        	(data) => {
		                    if(data.flag == "true"){
		                    window.open("success.html" , "_blank");
		                    }else{
		                    	alert(data.content);
		                    }
	                      }, 
	           	'json'
           	);
	});

	$("#en-form").submit(function checkRegister(){
		var outputJSON = {
							email: $("#en-email").val(),
							username: $("#en-username").val(),
							password: $("#en-password").val()
	                     };
		$.post(
			'save',
			{
	        	data: JSON.stringify(outputJSON)
	        }, 
		    (data) => {
			            if(data.flag == "true"){
			            	alert("注册成功");
			           	}else{
			            	alert(data.content);
			           	}
		              }, 
		    'json'
	    );
	});

});