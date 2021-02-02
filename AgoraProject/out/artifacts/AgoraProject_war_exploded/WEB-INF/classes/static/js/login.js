layui.use(['form','jquery','layer'],function () {
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
    form.on("submit(login)",function (data) {
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
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
                alert(content);
                if(flag==="0"){
                    parent.layer.close(index);
                    parent.window.location.reload();
                }
            }
        });
        return false;
    });

    $(".forgetpw").on("click",function () {
        //关闭当前窗口
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        parent.layer.close(index);
        parent.window.location.href = '/resetPassword';
        /*window.location.reload();
        window.location.href='resetPassword.html';*/
    });

    $("#register").on("click",function () {
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