layui.use(['form','element','jquery','layer'],function () {
    var form = layui.form,
        element=layui.element,
        layer=layui.layer,
        $ = layui.jquery;
    form.verify({
        confirmPw:function (value) {
            if(value !== $('#password').val()){
                return '密码与确认密码不一致！';
            }
        }
    });
    form.on("submit(register)",function (data) {
        //改变注册按钮样式
        // $(".registerBtn").addClass("layui-btn-disabled");
        // $(".registerBtn").html("注册中 <i class=\"layui-icon layui-icon-loading-1 layui-anim layui-anim-rotate layui-anim-loop\" style=\"display: inline-block\"></i>");
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
                alert(content);
                if(flag==="0"){
                    window.location.href="/index"
                }
            }
        });
        // $(".registerBtn").addClass("layui-btn-disabled").html("注册中 <i class=\"layui-icon layui-icon-loading-1 layui-anim layui-anim-rotate layui-anim-loop\" style=\"display: inline-block\"></i>");
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