layui.use(['form','element','jquery','layer'],function () {
    var form = layui.form,
        element=layui.element,
        layer=layui.layer,
        $ = layui.jquery;
        form.verify({
            confirmPw:function (value) {
                if(value !== $('#newPassword').val()){
                    return '密码与确认密码不一致！';
                }
            }
        });


    form.on("submit(setNewPassword)",function (data) {
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        $.ajax({
            type: "post",
            async: false,
            url: "/executeChangePassword",
            data: {
                "newPassword": data.field.newPassword,
                "encodeString": data.field.encodeString
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

});