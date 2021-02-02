layui.use(['form','element','jquery','layer'],function () {
    var form = layui.form,
        element=layui.element,
        layer=layui.layer,
        $ = layui.jquery;

    $(".layui-nav-bar").hide();//将导航栏下面的滑块隐藏
    //注册表单验证
    form.verify({
        userName:function(value){
            if(value === null){
                return '用户名不能为空！';
            }else if (value.length>30) {
                return '用户名长度不能超过30字符！';
            }else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
                return '用户名只能由中文、数字和字母组成！';
            }
        }
        ,password:function(value){
            if(value === null){
                return '密码不能为空！'
            }else if(value.length>20){
                return '密码长度不能超过20字符！';
            }else if(!/^[a-zA-Z0-9]+$/.test(value)){
                return '密码只能由数字和字母组成！';
            }
        }
        ,confirmPw:function (value) {
            if(value !== $('#password').val()){
                return '密码与确认密码不一致！';
            }
        },email:function (value) {
            var emailVal = $.trim(value);
            var regEmail = /.+@.+\.[a-zA-Z]{2,4}$/;
            if(emailVal === "" || (emailVal !== "" && !regEmail.test(emailVal))){
                return " 请输入正确的邮箱地址！";
            }
        }
    });

    form.on("submit(register)",function (data) {

        //改变按钮样式
        $(".registerBtn").addClass("layui-btn-disabled").attr("disabled",true)
            .html("注册中 <i class=\"layui-icon layui-icon-loading-1 layui-anim layui-anim-rotate layui-anim-loop\" style=\"display: inline-block\"></i>");
        setTimeout(function () {//延时发送，改变按钮样式才能正确执行
            $.ajax({
                type: "post",
                async: false,
                url: "/register",
                data: {
                    "userName": data.field.userName,
                    "password": data.field.password,
                    "emailAccount": data.field.emailAccount,
                    "realName": data.field.realName,
                    "sex": data.field.sex,
                    "code": data.field.code,
                },
                datatype: "json",
                success: function (data) {
                    var flag = data.flag;
                    var content = data.content;
                    if(flag==="0"){
                        layer.alert(content,{
                            skin: 'layui-layer-lan'
                            ,closeBtn: 0
                        }, function(){
                            window.location.href="/index"
                        });
                    }else {
                        layer.alert(content,{
                            skin: 'layui-layer-lan'
                            ,closeBtn: 0
                        });
                        //将注册按钮的样式变回来
                        $(".registerBtn").removeClass("layui-btn-disabled").attr("disabled",false).html("注册");
                    }
                }
            });
        },500);
        return false;
    });

    $(".codeImg").mouseover(function () {
        $(".flush").show();
    });

    $(".codeImg").mouseout(function () {
        $(".flush").hide();
    });

    $(".flush").on("click",function () {
        var test = rand(0,1000);
        // alert(test)
        $(".codeImg2").attr("src","/changeImage?test="+test);
    });


    function rand(min,max) {
        return Math.floor(Math.random()*(max-min))+min;
    }

    var test2 = rand(0,1000);
    $(".codeImg2").attr("src","/changeImage?test="+test2);
});