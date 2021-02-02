layui.use(['form','jquery','layer'],function () {
    var form = layui.form,
        $ = layui.jquery;

    form.on("submit(resetPassword)",function (data) {
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name

        $.ajax({
            type: "post",
            async: false,
            url: "/generateForgetMail",
            data: {
                "emailAccount": data.field.emailAccount,
                "code": data.field.code
            },
            datatype: "json",
            success: function (data) {
                var flag = data.flag;
                var content = data.content;
                alert(content);
                if(flag==="0"){
                    window.location.href="/index";
                }
            }
        });

        return false;  //return true表明验证成功，然后访问post链接
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