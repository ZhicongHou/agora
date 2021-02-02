layui.use(['form','jquery','layer'],function () {
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;

    $(".layui-nav-bar").hide();//将导航栏下面的滑块隐藏

    form.on("submit(login)",function (data) {
        $(".loginBtn").addClass("layui-btn-disabled").attr("disabled",true)
            .html("登录中 <i class=\"layui-icon layui-icon-loading-1 layui-anim layui-anim-rotate layui-anim-loop\" style=\"display: inline-block\"></i>");
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        setTimeout(function () {
            $.ajax({
                type: "post",
                async: false,
                url: "/login",
                data: {
                    "userName": data.field.userName,
                    "password": data.field.password,
                    "code": data.field.code
                },
                datatype: "json",
                success: function (data) {
                    var flag = data.flag;
                    var content = data.content;
                    if(flag==="0"){

                        parent.layer.close(index);
                        parent.window.location.reload();
                    }else{
                        layer.alert(content,{
                            skin: 'layui-layer-lan' //样式类名
                            ,closeBtn: 0
                        });
                        //将登录按钮的样式变回来
                        $(".loginBtn").removeClass("layui-btn-disabled").attr("disabled",false).html("登录");
                    }
                }
            });
        },500);
        return false;
    });

    $(".forgetpw a").on("click",function () {
        //关闭当前窗口
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        parent.layer.close(index);
        parent.window.location.href = '/resetPassword';
    });

    $(".regBtn").on("click",function () {
        //关闭当前窗口
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        parent.layer.close(index);
        parent.window.location.href = '/register';
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